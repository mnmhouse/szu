package cn.szu.edu.app.team.fragment;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import cn.szu.edu.app.api.remote.OSChinaTeamApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.team.adapter.TeamProjectMemberAdapter;
import cn.szu.edu.app.team.bean.Team;
import cn.szu.edu.app.team.bean.TeamMember;
import cn.szu.edu.app.team.bean.TeamMemberList;
import cn.szu.edu.app.team.bean.TeamProject;
import cn.szu.edu.app.team.ui.TeamMainActivity;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import cn.szu.edu.app.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * TeamProjectFragment.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 * 
 * @data 2015-2-28 下午4:08:58
 */
public class TeamProjectMemberFragment extends
	BaseListFragment<TeamMember> {

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
	    
	    mTeamProject = (TeamProject) bundle.getSerializable(TeamMainActivity.BUNDLE_KEY_PROJECT);

	    mTeamId = mTeam.getId();
	}
    }

    @Override
    protected TeamProjectMemberAdapter getListAdapter() {
	// TODO Auto-generated method stub
	return new TeamProjectMemberAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
	return "team_project_member_list_" + mTeamId + "_" + mTeamProject.getGit().getId();
    }

    @Override
    protected TeamMemberList parseList(InputStream is) throws Exception {
	TeamMemberList list = XmlUtils.toBean(
		TeamMemberList.class, is);
	return list;
    }

    @Override
    protected TeamMemberList readList(Serializable seri) {
	return ((TeamMemberList) seri);
    }

    @Override
    protected void sendRequestData() {
	// TODO Auto-generated method stub
	OSChinaTeamApi.getTeamProjectMemberList(mTeamId, mTeamProject,
		mHandler);
    }

    @Override
    protected void executeOnLoadDataSuccess(List<TeamMember> data) {
	// TODO Auto-generated method stub
	super.executeOnLoadDataSuccess(data);
	if (mAdapter.getData().isEmpty()) {
	    setNoProjectMember();
	}
	mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
    }

    private void setNoProjectMember() {
	mErrorLayout.setErrorType(EmptyLayout.NODATA);
	mErrorLayout.setErrorImag(R.drawable.page_icon_empty);
	String str = getResources().getString(
		R.string.team_empty_project_member);
	mErrorLayout.setErrorMessage(str);
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
	TeamMember teamMember = mAdapter.getItem(position);
	if (teamMember != null) {
	    UIHelper.showTeamMemberInfo(getActivity(), mTeamId, teamMember);
	}
    }
}
