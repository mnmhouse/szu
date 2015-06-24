package cn.szu.edu.app.fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.http.Header;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.R;
import cn.szu.edu.app.adapter.TopNewsAdapter;
import cn.szu.edu.app.api.OperationResponseHandler;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.Constants;
import cn.szu.edu.app.bean.Result;
import cn.szu.edu.app.bean.ResultBean;
import cn.szu.edu.app.bean.Tweet;
import cn.szu.edu.app.bean.TweetsList;
import cn.szu.edu.app.interf.OnTabReselectListener;
import cn.szu.edu.app.ui.dialog.CommonDialog;
import cn.szu.edu.app.ui.dialog.DialogHelper;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.HTMLUtil;
import cn.szu.edu.app.util.TDevice;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

/**
 * @author kymjs (http://www.kymjs.com)
 */
public class TopNewsFragment extends BaseListFragment<Tweet> implements
        OnItemLongClickListener, OnTabReselectListener {

    protected static final String TAG = TopNewsFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "tweetslist_";
    private TextView mNotice;
    
    @Override
    protected int getLayoutId() {
    	return R.layout.fragment_pull_refresh_listview_with_header;
    }
    
    class DeleteTweetResponseHandler extends OperationResponseHandler {

        DeleteTweetResponseHandler(Object... args) {
            super(args);
        }

        @Override
        public void onSuccess(int code, ByteArrayInputStream is, Object[] args)
                throws Exception {
            try {
                Result res = XmlUtils.toBean(ResultBean.class, is).getResult();
                if (res != null && res.OK()) {
                    AppContext.showToastShort(R.string.delete_success);
                    Tweet tweet = (Tweet) args[0];
                    mAdapter.removeItem(tweet);
                    mAdapter.notifyDataSetChanged();
                } else {
                    onFailure(code, res.getErrorMessage(), args);
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(code, e.getMessage(), args);
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {
            AppContext.showToastShort(R.string.delete_faile);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCatalog > 0) {
            IntentFilter filter = new IntentFilter(
                    Constants.INTENT_ACTION_USER_CHANGE);
            filter.addAction(Constants.INTENT_ACTION_LOGOUT);
            getActivity().registerReceiver(mReceiver, filter);
        }
    }
    
    @Override
	protected void executeOnLoadFinish() {
    	super.executeOnLoadFinish();
    	mNotice.setSelected(true);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	onRefresh();
    }
    
    @Override
    public void onDestroy() {
        if (mCatalog > 0) {
            getActivity().unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }

    @Override
    protected TopNewsAdapter getListAdapter() {
        return new TopNewsAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String str = bundle.getString("topic");
            if (str != null) {
                return str;
            }
        }
        return CACHE_KEY_PREFIX + mCatalog;
    }

    public String getTopic() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String str = bundle.getString("topic");
            if (str != null) {
                return str;
            }
        }
        return "";
    }

    @Override
    protected TweetsList parseList(InputStream is) throws Exception {
        TweetsList list = XmlUtils.toBean(TweetsList.class, is);
        return list;
    }

    @Override
    protected TweetsList readList(Serializable seri) {
        return ((TweetsList) seri);
    }

    @Override
    protected void sendRequestData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String str = bundle.getString("topic");
            if (str != null) {
                OSChinaApi.getTweetTopicList(mCurrentPage, str, mHandler);
                return;
            }
        }
        OSChinaApi.getTweetList(mCatalog, mCurrentPage, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Tweet tweet = mAdapter.getItem(position);
        if (tweet != null) {
            UIHelper.showTweetDetail(view.getContext(), null, tweet.getId());
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setupContent();
        }
    };

    private void setupContent() {
        if (AppContext.getInstance().isLogin()) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            requestData(true);
        } else {
            mCatalog = TweetsList.CATALOG_ME;
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
        }
    }

    @Override
    protected void requestData(boolean refresh) {
        if (mCatalog > 0) {
            if (AppContext.getInstance().isLogin()) {
                mCatalog = AppContext.getInstance().getLoginUid();
                super.requestData(refresh);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                mErrorLayout.setErrorMessage(getString(R.string.unlogin_tip));
            }
        } else {
            super.requestData(refresh);
        }
    }

    @Override
    public void initView(View view) {
    	
        super.initView(view);

        mNotice = (TextView)view.findViewById(R.id.notice); 
        mNotice.setText( "2.2.1.  紧急通知类消息紧急通知类型的消息主要指恶劣天 气警报或政府部门授权下发的紧急事件通知。 ");
        
        mListView.setOnItemLongClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCatalog > 0) {
                    if (AppContext.getInstance().isLogin()) {
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                        requestData(true);
                    } else {
                        UIHelper.showLoginActivity(getActivity());
                    }
                } else {
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    requestData(true);
                }
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
            int position, long id) {
        Tweet tweet = mAdapter.getItem(position);
        if (tweet != null) {
            handleLongClick(tweet);
            return true;
        }
        return false;
    }

    private void handleLongClick(final Tweet tweet) {
        String[] items = null;
        if (AppContext.getInstance().getLoginUid() == tweet.getAuthorid()) {
            items = new String[] { getResources().getString(R.string.copy),
                    getResources().getString(R.string.delete) };
        } else {
            items = new String[] { getResources().getString(R.string.copy) };
        }
        final CommonDialog dialog = DialogHelper
                .getPinterestDialogCancelable(getActivity());
        dialog.setNegativeButton(R.string.cancle, null);
        dialog.setItemsWithoutChk(items, new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                dialog.dismiss();
                if (position == 0) {
                    TDevice.copyTextToBoard(HTMLUtil.delHTMLTag(tweet.getBody()));
                } else if (position == 1) {
                    handleDeleteTweet(tweet);
                }
            }
        });
        dialog.show();
    }

    private void handleDeleteTweet(final Tweet tweet) {
        CommonDialog dialog = DialogHelper
                .getPinterestDialogCancelable(getActivity());
        dialog.setMessage(R.string.message_delete_tweet);
        dialog.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OSChinaApi.deleteTweet(tweet.getAuthorid(), tweet
                                .getId(), new DeleteTweetResponseHandler(tweet));
                    }
                });
        dialog.setNegativeButton(R.string.cancle, null);
        dialog.show();
    }

    @Override
    public void onTabReselect() {
        onRefresh();
    }

    @Override
    protected long getAutoRefreshTime() {
        // 最新动弹3分钟刷新一次
        if (mCatalog == TweetsList.CATALOG_LATEST) {
            return 3 * 60;
        }
        return super.getAutoRefreshTime();
    }
}