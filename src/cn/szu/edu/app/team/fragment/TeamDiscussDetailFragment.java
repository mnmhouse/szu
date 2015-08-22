package cn.szu.edu.app.team.fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.R;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.api.remote.OSChinaTeamApi;
import cn.szu.edu.app.base.BeseHaveHeaderListFragment;
import cn.szu.edu.app.bean.Result;
import cn.szu.edu.app.bean.ResultBean;
import cn.szu.edu.app.emoji.OnSendClickListener;
import cn.szu.edu.app.team.adapter.TeamReplyAdapter;
import cn.szu.edu.app.team.bean.TeamDiscuss;
import cn.szu.edu.app.team.bean.TeamDiscussDetail;
import cn.szu.edu.app.team.bean.TeamRepliesList;
import cn.szu.edu.app.team.bean.TeamReply;
import cn.szu.edu.app.ui.DetailActivity;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * TeamDiscussDetailFragment.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 * 
 * @data 2015-2-2 下午6:14:15
 */
public class TeamDiscussDetailFragment extends
        BeseHaveHeaderListFragment<TeamReply, TeamDiscuss> implements
        OnSendClickListener {

    private int mTeamId;

    private int mDiscussId;

    private TextView mTvTitle;

    private TextView mTvAuthor;

    private TextView mTvTime;

    private TextView mTvAnswerVote;

    private WebView mWebView;

    private DetailActivity outAty;

    @Override
    protected void sendRequestData() {
        OSChinaTeamApi.getTeamReplyList(mTeamId, mDiscussId,
                TeamReply.REPLY_TYPE_DISCUSS, mCurrentPage, mHandler);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        outAty = (DetailActivity) getActivity();
        super.onViewCreated(view, savedInstanceState);
    }

    private final AsyncHttpResponseHandler mReplyHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            // TODO Auto-generated method stub
            Result res = XmlUtils.toBean(ResultBean.class, arg2).getResult();
            if (res.OK()) {
                AppContext.showToast("评论成功");
                onRefresh();
            } else {
                AppContext.showToast(res.getErrorMessage());
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {
            // TODO Auto-generated method stub
            AppContext.showToast(new String(arg2));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            super.onFinish();
            hideWaitDialog();
        }

        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            super.onStart();
            showWaitDialog();
        }
    };

    @Override
    protected void requestDetailData(boolean isRefresh) {
        OSChinaTeamApi
                .getTeamDiscussDetail(mTeamId, mDiscussId, mDetailHandler);
    }

    @Override
    protected View initHeaderView() {
        Intent args = getActivity().getIntent();
        if (args != null) {
            mTeamId = args.getIntExtra("teamid", 0);
            mDiscussId = args.getIntExtra("discussid", 0);
        }
        View headerView = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_team_discuss_detail, null);
        mTvTitle = findHeaderView(headerView, R.id.tv_title);
        mTvAuthor = findHeaderView(headerView, R.id.tv_author);
        mTvTime = findHeaderView(headerView, R.id.tv_time);
        mTvAnswerVote = findHeaderView(headerView, R.id.tv_answer_vote);
        mWebView = findHeaderView(headerView, R.id.webview);
        return headerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        outAty.emojiFragment.hideFlagButton();
    }

    @Override
    protected String getDetailCacheKey() {
        // TODO Auto-generated method stub
        return "team_discuss_detail_" + mTeamId + mDiscussId;
    }

    @Override
    protected void executeOnLoadDetailSuccess(TeamDiscuss detailBean) {
        mTvTitle.setText(detailBean.getTitle());
        mTvAuthor.setText(detailBean.getAuthor().getName());
        mTvTime.setText(StringUtils.friendly_time(detailBean.getCreateTime()));
        mTvAnswerVote.setText(detailBean.getVoteUp() + "赞/"
                + detailBean.getAnswerCount() + "回");
        UIHelper.initWebView(mWebView);
        UIHelper.addWebImageShow(getActivity(), mWebView);
        mWebView.loadDataWithBaseURL(null,
                UIHelper.WEB_STYLE + detailBean.getBody(), "text/html",
                "utf-8", null);
        mAdapter.setNoDataText(R.string.comment_empty);
    }

    @Override
    protected TeamDiscuss getDetailBean(ByteArrayInputStream is) {
        // TODO Auto-generated method stub
        return XmlUtils.toBean(TeamDiscussDetail.class, is).getDiscuss();
    }

    @Override
    protected TeamReplyAdapter getListAdapter() {
        // TODO Auto-generated method stub
        return new TeamReplyAdapter();
    }

    @Override
    protected TeamRepliesList parseList(InputStream is) throws Exception {
        // TODO Auto-generated method stub
        return XmlUtils.toBean(TeamRepliesList.class, is);
    }

    @Override
    protected TeamRepliesList readList(Serializable seri) {
        // TODO Auto-generated method stub
        return (TeamRepliesList) seri;
    }

    @Override
    protected String getCacheKeyPrefix() {
        // TODO Auto-generated method stub
        return "team_discuss_reply" + mTeamId + "_" + mDiscussId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        TeamReply reply = mAdapter.getItem(position - 1);
        if (reply == null)
            return;
    }

    @Override
    public void onClickSendButton(Editable str) {
        if (TextUtils.isEmpty(str)) {
            AppContext.showToast("请先输入评论内容...");
            return;
        }
        if (!AppContext.getInstance().isLogin()) {
            UIHelper.showLoginActivity(getActivity());
            return;
        }
        int uid = AppContext.getInstance().getLoginUid();
        OSChinaTeamApi.pubTeamDiscussReply(uid, mTeamId, mDiscussId,
                str.toString(), mReplyHandler);
    }

    @Override
    public void onClickFlagButton() {

    }
}
