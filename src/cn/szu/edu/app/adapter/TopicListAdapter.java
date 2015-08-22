package cn.szu.edu.app.adapter;

import org.apache.http.Header;
import org.kymjs.kjframe.utils.DensityUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.R;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.bean.Tweet;
import cn.szu.edu.app.emoji.InputHelper;
import cn.szu.edu.app.ui.dialog.CommonDialog;
import cn.szu.edu.app.ui.dialog.DialogHelper;
import cn.szu.edu.app.util.ImageUtils;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.widget.AvatarView;
import cn.szu.edu.app.widget.MyLinkMovementMethod;
import cn.szu.edu.app.widget.MyURLSpan;
import cn.szu.edu.app.widget.TweetTextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author HuangWenwei
 * @author kymjs
 * @date 2014年10月10日
 */
public class TopicListAdapter extends ListBaseAdapter<Tweet> {

    static class ViewHolder {
        @InjectView(R.id.tv_tweet_name)
        TextView author;
        @InjectView(R.id.tv_tweet_time)
        TextView time;
        @InjectView(R.id.tweet_item)
        TweetTextView content;
        @InjectView(R.id.tv_tweet_platform)
        TextView platform;
        @InjectView(R.id.iv_tweet_face)
        AvatarView face;
        @InjectView(R.id.tv_del)
        TextView del;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private Bitmap recordBitmap;
    private Context context;

    private void initRecordImg(Context cxt) {
        recordBitmap = BitmapFactory.decodeResource(cxt.getResources(),
                R.drawable.audio3);
        recordBitmap = ImageUtils.zoomBitmap(recordBitmap,
                DensityUtils.dip2px(cxt, 20f), DensityUtils.dip2px(cxt, 20f));
    }

    @Override
    protected View getRealView(final int position, View convertView,
            final ViewGroup parent) {
        context = parent.getContext();
        final ViewHolder vh;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.list_cell_topic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final Tweet tweet = mDatas.get(position);

        if (tweet.getAuthorid() == AppContext.getInstance().getLoginUid()) {
            vh.del.setVisibility(View.VISIBLE);
            vh.del.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    optionDel(parent.getContext(), tweet, position);
                }
            });
        } else {
            vh.del.setVisibility(View.GONE);
        }

        vh.face.setUserInfo(tweet.getAuthorid(), tweet.getAuthor());
        vh.face.setAvatarUrl(tweet.getPortrait());
        vh.author.setText(tweet.getAuthor());
        vh.time.setText(StringUtils.friendly_time(tweet.getPubDate()));
        vh.content.setMovementMethod(MyLinkMovementMethod.a());
        vh.content.setFocusable(false);
        vh.content.setDispatchToParent(true);
        vh.content.setLongClickable(false);

        Spanned span = Html.fromHtml(tweet.getBody().trim());

        if (!StringUtils.isEmpty(tweet.getAttach())) {
            if (recordBitmap == null) {
                initRecordImg(parent.getContext());
            }
            ImageSpan recordImg = new ImageSpan(parent.getContext(),
                    recordBitmap);
            SpannableString str = new SpannableString("c");
            str.setSpan(recordImg, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            vh.content.setText(str);
            span = InputHelper.displayEmoji(context.getResources(), span);
            vh.content.append(span);
        } else {
            span = InputHelper.displayEmoji(context.getResources(), span);
            vh.content.setText(span);
        }
        MyURLSpan.parseLinkText(vh.content, span);

        vh.platform.setVisibility(View.VISIBLE);
        switch (tweet.getAppclient()) {
        case Tweet.CLIENT_MOBILE:
            vh.platform.setText(R.string.from_mobile);
            break;
        case Tweet.CLIENT_ANDROID:
            vh.platform.setText(R.string.from_android);
            break;
        case Tweet.CLIENT_IPHONE:
            vh.platform.setText(R.string.from_iphone);
            break;
        case Tweet.CLIENT_WINDOWS_PHONE:
            vh.platform.setText(R.string.from_windows_phone);
            break;
        case Tweet.CLIENT_WECHAT:
            vh.platform.setText(R.string.from_wechat);
            break;
        default:
            vh.platform.setText("");
            vh.platform.setVisibility(View.GONE);
            break;
        }
        return convertView;
    }

    private void optionDel(Context context, final Tweet tweet,
            final int position) {

        CommonDialog dialog = DialogHelper
                .getPinterestDialogCancelable(context);
        dialog.setTitle("提示");
        dialog.setMessage("确定删除吗？");
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OSChinaApi.deleteTweet(tweet.getAuthorid(), tweet.getId(),
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, Header[] arg1,
                                    byte[] arg2) {
                                mDatas.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1,
                                    byte[] arg2, Throwable arg3) {}
                        });
            }
        });
        dialog.setPositiveButton("取消", null);

        dialog.show();
    }

}
