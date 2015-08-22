package cn.szu.edu.app.team.fragment;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import cn.szu.edu.app.api.remote.OSChinaTeamApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.team.adapter.DynamicAdapter;
import cn.szu.edu.app.team.bean.Team;
import cn.szu.edu.app.team.bean.TeamActive;
import cn.szu.edu.app.team.bean.TeamActives;
import cn.szu.edu.app.team.bean.TeamProject;
import cn.szu.edu.app.team.ui.TeamMainActivity;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import cn.szu.edu.app.R;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * 团队动态列表 TeamProjectFragment.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 * 
 * @data 2015-2-28 下午4:08:58
 */
public class TeamProjectActiveFragment extends BaseListFragment<TeamActive> {

    private Team mTeam;

    private int mTeamId;

    private TeamProject mTeamProject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTeam = (Team) bundle
                    .getSerializable(TeamMainActivity.BUNDLE_KEY_TEAM);

            mTeamProject = (TeamProject) bundle
                    .getSerializable(TeamMainActivity.BUNDLE_KEY_PROJECT);

            mTeamId = mTeam.getId();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListView.setSelector(new ColorDrawable(android.R.color.transparent));
        mListView.setDivider(new ColorDrawable(android.R.color.transparent));
    }

    @Override
    protected DynamicAdapter getListAdapter() {
        // TODO Auto-generated method stub
        return new DynamicAdapter(getActivity());
    }

    @Override
    protected String getCacheKeyPrefix() {
        return "team_project_active_list_" + mTeamId + "_"
                + mTeamProject.getGit().getId();
    }

    @Override
    protected TeamActives parseList(InputStream is) throws Exception {
        TeamActives list = XmlUtils.toBean(TeamActives.class, is);
        return list;
    }

    @Override
    protected TeamActives readList(Serializable seri) {
        return ((TeamActives) seri);
    }

    @Override
    protected void sendRequestData() {
        // TODO Auto-generated method stub
        OSChinaTeamApi.getTeamProjectActiveList(mTeamId, mTeamProject, "all",
                mCurrentPage, mHandler);
    }

    @Override
    protected void executeOnLoadDataSuccess(List<TeamActive> data) {
        // TODO Auto-generated method stub
        super.executeOnLoadDataSuccess(data);
        if (mAdapter.getData().isEmpty()) {
            setNoProjectActive();
        }
        mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
    }

    private void setNoProjectActive() {
        mErrorLayout.setErrorType(EmptyLayout.NODATA);
        mErrorLayout.setErrorImag(R.drawable.page_icon_empty);
        String str = getResources().getString(
                R.string.team_empty_project_active);
        mErrorLayout.setErrorMessage(str);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        TeamActive active = mAdapter.getItem(position);
        if (active != null) {
            UIHelper.showTeamActiveDetail(getActivity(), mTeam.getId(), active);
        }
    }
}
