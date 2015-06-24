package cn.szu.edu.app.team.fragment;

import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.team.adapter.TeamIssueAdapter;
import cn.szu.edu.app.team.bean.Team;
import cn.szu.edu.app.team.bean.TeamIssue;
import cn.szu.edu.app.team.bean.TeamIssueList;
import cn.szu.edu.app.team.ui.TeamMainActivity;
import cn.szu.edu.app.team.viewpagefragment.MyIssuePagerfragment;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * 我的任务列表界面
 * 
 * @author kymjs (https://github.com/kymjs)
 * 
 */
public class MyIssueFragment extends BaseListFragment<TeamIssue> {

    protected static final String TAG = TeamIssueFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "my_issue_";

    private Team mTeam;
    private String type = "all";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTeam = (Team) bundle
                    .getSerializable(TeamMainActivity.BUNDLE_KEY_TEAM);
            type = bundle.getString(MyIssuePagerfragment.MY_ISSUEDETAIL_KEY,
                    "all");
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mListView.setDivider(new ColorDrawable(0x00000000));
        mListView.setSelector(new ColorDrawable(0x00000000));
    }

    @Override
    protected TeamIssueAdapter getListAdapter() {
        return new TeamIssueAdapter();
    }

    /**
     * 获取当前展示页面的缓存数据
     */
    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + AppContext.getInstance().getLoginUid() + "_"
                + mTeam.getId() + mCurrentPage + type;
    }

    @Override
    protected TeamIssueList parseList(InputStream is) throws Exception {
        TeamIssueList list = XmlUtils.toBean(TeamIssueList.class, is);
        return list;
    }

    @Override
    protected TeamIssueList readList(Serializable seri) {
        return ((TeamIssueList) seri);
    }

    @Override
    protected void sendRequestData() {
        OSChinaApi.getMyIssue(mTeam.getId() + "", AppContext.getInstance()
                .getLoginUid() + "", mCurrentPage, type, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        TeamIssue issue = mAdapter.getItem(position);
        if (issue != null) {
            UIHelper.showTeamIssueDetail(getActivity(), mTeam, issue, null);
        }
    }
}
