package cn.szu.edu.app.fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.szu.edu.app.R;

import org.apache.http.Header;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.adapter.MessageDetailAdapter;
import cn.szu.edu.app.api.OperationResponseHandler;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseActivity;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.bean.Comment;
import cn.szu.edu.app.bean.CommentList;
import cn.szu.edu.app.bean.Constants;
import cn.szu.edu.app.bean.Result;
import cn.szu.edu.app.bean.ResultBean;
import cn.szu.edu.app.emoji.KJEmojiFragment;
import cn.szu.edu.app.emoji.OnSendClickListener;
import cn.szu.edu.app.ui.dialog.CommonDialog;
import cn.szu.edu.app.ui.dialog.DialogHelper;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.HTMLUtil;
import cn.szu.edu.app.util.TDevice;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 与某人的聊天记录界面（留言详情）
 * 
 * @author kymjs (http://www.kymjs.com/)
 * 
 */
public class MessageDetailFragment extends BaseListFragment<Comment> implements
        OnItemLongClickListener, OnSendClickListener {
    protected static final String TAG = ActiveFragment.class.getSimpleName();
    public static final String BUNDLE_KEY_FID = "BUNDLE_KEY_FID";
    public static final String BUNDLE_KEY_FNAME = "BUNDLE_KEY_FNAME";
    private static final String CACHE_KEY_PREFIX = "message_detail_list";

    private int mFid;
    private String mFName;
    public KJEmojiFragment emojiFragment = new KJEmojiFragment();

    private final AsyncHttpResponseHandler mPublicHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            hideWaitDialog();
            try {
                ResultBean resb = XmlUtils.toBean(ResultBean.class,
                        new ByteArrayInputStream(arg2));
                Result res = resb.getResult();
                if (res.OK()) {
                    AppContext
                            .showToastShort(R.string.tip_message_public_success);
                    mAdapter.addItem(0, resb.getComment());
                } else {
                    AppContext.showToastShort(res.getErrorMessage());
                }
                emojiFragment.clean();
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {
            hideWaitDialog();
            AppContext.showToastShort(R.string.tip_message_public_faile);
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mErrorLayout != null) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mFid = args.getInt(BUNDLE_KEY_FID);
            mFName = args.getString(BUNDLE_KEY_FNAME);
            mCatalog = CommentList.CATALOG_MESSAGE;
        }
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);
        getActivity().registerReceiver(mReceiver, filter);

        ((BaseActivity) getActivity()).setActionBarTitle(mFName);

        int mode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        getActivity().getWindow().setSoftInputMode(mode);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.emoji_container, emojiFragment).commit();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        if (emojiFragment.isShowEmojiKeyBoard()) {
            emojiFragment.hideAllKeyBoard();
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    protected MessageDetailAdapter getListAdapter() {
        return new MessageDetailAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mFid;
    }

    @Override
    protected CommentList parseList(InputStream is) throws Exception {
        CommentList list = XmlUtils.toBean(CommentList.class, is);
        return list;
    }

    @Override
    protected CommentList readList(Serializable seri) {
        CommentList list = ((CommentList) seri);
        return list;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mListView.setDivider(null);
        mListView.setDividerHeight(0);
        mListView.setStackFromBottom(true);
        mListView.setOnItemLongClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getInstance().isLogin()) {
                    requestData(false);
                } else {
                    UIHelper.showLoginActivity(getActivity());
                }
            }
        });
    }

    @Override
    protected void requestData(boolean refresh) {
        mErrorLayout.setErrorMessage("");
        if (AppContext.getInstance().isLogin()) {
            super.requestData(refresh);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
        }
    }

    @Override
    protected void sendRequestData() {
        OSChinaApi.getCommentList(mFid, mCatalog, mCurrentPage, mHandler);
    }

    @Override
    protected boolean isReadCacheData(boolean refresh) {
        if (!TDevice.hasInternet()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void executeOnLoadDataSuccess(List<Comment> data) {
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (data == null) {
            data = new ArrayList<Comment>(1);
        }
        if (mAdapter != null) {
            if (mCurrentPage == 0)
                mAdapter.clear();
            mAdapter.addData(data);
            if (data.size() == 0 && mState == STATE_REFRESH) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else if (data.size() < getPageSize()) {
                mAdapter.setState(ListBaseAdapter.STATE_OTHER);
            } else {
                mAdapter.setState(ListBaseAdapter.STATE_LOAD_MORE);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {}

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
            int position, long id) {
        final Comment message = mAdapter.getItem(position);
        final CommonDialog dialog = DialogHelper
                .getPinterestDialogCancelable(getActivity());
        dialog.setItemsWithoutChk(
                getResources().getStringArray(R.array.message_list_options),
                new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
                        dialog.dismiss();
                        switch (position) {
                        case 0:
                            TDevice.copyTextToBoard(HTMLUtil.delHTMLTag(message
                                    .getContent()));
                            break;
                        case 1:
                            handleDeleteMessage(message);
                            break;
                        default:
                            break;
                        }
                    }
                });
        dialog.setNegativeButton(R.string.cancle, null);
        dialog.show();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        emojiFragment.hideFlagButton();
    }

    private void handleDeleteMessage(final Comment message) {
        CommonDialog dialog = DialogHelper
                .getPinterestDialogCancelable(getActivity());
        dialog.setMessage(getString(R.string.confirm_delete_one_message,
                message.getAuthor()));
        dialog.setNegativeButton(R.string.cancle, null);
        dialog.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showWaitDialog(R.string.progress_submit);
                        OSChinaApi.deleteComment(mFid,
                                CommentList.CATALOG_MESSAGE, message.getId(),
                                message.getAuthorId(),
                                new DeleteMessageOperationHandler(message));
                    }
                });
        dialog.show();
    }

    class DeleteMessageOperationHandler extends OperationResponseHandler {

        public DeleteMessageOperationHandler(Object... args) {
            super(args);
        }

        @Override
        public void onSuccess(int code, ByteArrayInputStream is, Object[] args)
                throws Exception {
            Result res = XmlUtils.toBean(ResultBean.class, is).getResult();
            if (res.OK()) {
                Comment msg = (Comment) args[0];
                mAdapter.removeItem(msg);
                mAdapter.notifyDataSetChanged();
                hideWaitDialog();
                AppContext.showToastShort(R.string.tip_delete_success);
            } else {
                AppContext.showToastShort(res.getErrorMessage());
                hideWaitDialog();
            }
        }

        @Override
        public void onFailure(int code, String errorMessage, Object[] args) {
            AppContext.showToastShort(R.string.tip_delete_faile);
            hideWaitDialog();
        }
    }

    @Override
    public void onClickSendButton(Editable str) {
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }
        if (StringUtils.isEmpty(str)) {
            AppContext.showToastShort(R.string.tip_content_empty);
            return;
        }
        showWaitDialog("提交中...");
        OSChinaApi.publicMessage(AppContext.getInstance().getLoginUid(), mFid,
                str.toString(), mPublicHandler);
    }

    @Override
    public void onClickFlagButton() {}
}
