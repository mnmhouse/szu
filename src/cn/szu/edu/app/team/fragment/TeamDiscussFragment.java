package cn.szu.edu.app.team.fragment;

import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.api.remote.OSChinaTeamApi;
import cn.szu.edu.app.base.BaseActivity;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.ListEntity;
import cn.szu.edu.app.team.adapter.TeamDiscussAdapter;
import cn.szu.edu.app.team.bean.Team;
import cn.szu.edu.app.team.bean.TeamDiscuss;
import cn.szu.edu.app.team.bean.TeamDiscussList;
import cn.szu.edu.app.team.bean.TeamIssue;
import cn.szu.edu.app.team.ui.TeamMainActivity;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * team讨论区列表界面
 * 
 * @author fireant(http://my.oschina.net/u/253900)
 * 
 */
public class TeamDiscussFragment extends BaseListFragment<TeamDiscuss> {

    protected static final String TAG = TeamDiscussFragment.class
            .getSimpleName();
    private static final String CACHE_KEY_PREFIX = "team_discuss_list_";

    private Team mTeam;

    private int mTeamId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Team team = (Team) bundle
                    .getSerializable(TeamMainActivity.BUNDLE_KEY_TEAM);
            if (team != null) {
                mTeam = team;
                mTeamId = StringUtils.toInt(mTeam.getId());
            }
        }
    }

    @Override
    protected TeamDiscussAdapter getListAdapter() {
        return new TeamDiscussAdapter();
    }

    /**
     * 获取当前展示页面的缓存数据
     */
    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mTeamId + "_" + mCurrentPage;
    }

    @Override
    protected TeamDiscussList parseList(InputStream is)
            throws Exception {
        TeamDiscussList list = XmlUtils.toBean(
                TeamDiscussList.class, is);
        return list;
    }

    @Override
    protected ListEntity<TeamDiscuss> readList(Serializable seri) {
        return ((TeamDiscussList) seri);
    }

    @Override
    protected void sendRequestData() {
        OSChinaTeamApi.getTeamDiscussList("new", mTeamId, 0, mCurrentPage,
                mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
	TeamDiscuss item = (TeamDiscuss) mAdapter.getItem(position);
	if (item != null) {
	    UIHelper.showTeamDiscussDetail(getActivity(), mTeam, item);
	}

    }

}