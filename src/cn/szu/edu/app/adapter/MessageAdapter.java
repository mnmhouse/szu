package cn.szu.edu.app.adapter;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.R;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.bean.Messages;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.widget.AvatarView;
import cn.szu.edu.app.widget.MyLinkMovementMethod;
import cn.szu.edu.app.widget.MyURLSpan;
import cn.szu.edu.app.widget.TweetTextView;

public class MessageAdapter extends ListBaseAdapter<Messages> {

    @Override
    protected boolean loadMoreHasBg() {
        return false;
    }

    @Override
    protected View getRealView(int position, View convertView,
            final ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.list_cell_message, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Messages item = (Messages) mDatas.get(position);

        if (AppContext.getInstance().getLoginUid() == item.getSenderId()) {
            vh.sender.setVisibility(View.VISIBLE);
        } else {
            vh.sender.setVisibility(View.GONE);
        }

        vh.name.setText(item.getFriendName());

        vh.content.setMovementMethod(MyLinkMovementMethod.a());
        vh.content.setFocusable(false);
        vh.content.setDispatchToParent(true);
        vh.content.setLongClickable(false);
        Spanned span = Html.fromHtml(item.getContent());
        vh.content.setText(span);
        MyURLSpan.parseLinkText(vh.content, span);

        vh.time.setText(StringUtils.friendly_time(item.getPubDate()));
        vh.count.setText(parent.getResources().getString(
                R.string.message_count, item.getMessageCount()));

        vh.avatar.setAvatarUrl(item.getPortrait());
        vh.avatar.setUserInfo(item.getSenderId(), item.getSender());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_avatar)
        AvatarView avatar;
        @InjectView(R.id.tv_name)
        TextView name;
        @InjectView(R.id.tv_sender)
        TextView sender;
        @InjectView(R.id.tv_time)
        TextView time;
        @InjectView(R.id.tv_count)
        TextView count;
        @InjectView(R.id.tv_content)
        TweetTextView content;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
