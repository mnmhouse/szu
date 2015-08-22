package cn.szu.edu.app.team.viewpagefragment;

import cn.szu.edu.app.adapter.ViewPageFragmentAdapter;
import cn.szu.edu.app.base.BaseViewPagerFragment;
import cn.szu.edu.app.team.bean.TeamIssue;
import cn.szu.edu.app.team.fragment.MyIssueFragment;
import cn.szu.edu.app.team.fragment.TeamBoardFragment;
import cn.szu.edu.app.team.ui.TeamMainActivity;
import cn.szu.edu.app.ui.SimpleBackActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 我的任务状态列表
 * 
 * @author kymjs (https://github.com/kymjs)
 * 
 */
public class MyIssuePagerfragment extends BaseViewPagerFragment {
    public static final String MY_ISSUEDETAIL_KEY = "MyIssuePagerfragment_key";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void initView(View view) {}

    @Override
    public void initData() {}

    @Override
    public void onResume() {
        super.onResume();

        int currentPage = 0;
        try {
            currentPage = getArguments().getInt(TeamBoardFragment.WHICH_PAGER_KEY, 0);
        } catch (NullPointerException e) {
        }
        mViewPager.setCurrentItem(currentPage);
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        adapter.addTab("待办中", TeamIssue.TEAM_ISSUE_STATE_OPENED, MyIssueFragment.class, getBundle(TeamIssue.TEAM_ISSUE_STATE_OPENED));

        adapter.addTab("进行中", TeamIssue.TEAM_ISSUE_STATE_UNDERWAY, MyIssueFragment.class, getBundle(TeamIssue.TEAM_ISSUE_STATE_UNDERWAY));

        adapter.addTab("已完成", TeamIssue.TEAM_ISSUE_STATE_CLOSED, MyIssueFragment.class, getBundle(TeamIssue.TEAM_ISSUE_STATE_CLOSED));

        adapter.addTab("已验收", TeamIssue.TEAM_ISSUE_STATE_ACCEPTED, MyIssueFragment.class, getBundle(TeamIssue.TEAM_ISSUE_STATE_ACCEPTED));
    }
    
    private Bundle getBundle(String state) {
	Bundle bundle = new Bundle();
	bundle.putSerializable(TeamMainActivity.BUNDLE_KEY_TEAM, getArguments().getSerializable(TeamMainActivity.BUNDLE_KEY_TEAM));
	bundle.putString(MyIssuePagerfragment.MY_ISSUEDETAIL_KEY, state);
	return bundle;
    }
}
