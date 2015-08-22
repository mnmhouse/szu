package cn.szu.edu.app.team.fragment;

import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.api.remote.OSChinaTeamApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.ListEntity;
import cn.szu.edu.app.team.adapter.TeamIssueAdapter;
import cn.szu.edu.app.team.bean.TeamIssue;
import cn.szu.edu.app.team.bean.TeamIssueList;
import cn.szu.edu.app.util.XmlUtils;


/**
 * 任务列表界面
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2015年1月14日 下午5:15:46
 * 
 */

public class IssueListFragment extends BaseListFragment<TeamIssue> {

    private final String CACHE_KEY_PREFIX = "issue_list_";

    private final int mTeamId = 12481;

    private final int mProjectId = 435;

    @Override
    protected TeamIssueAdapter getListAdapter() {
        return new TeamIssueAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + "_" + mCurrentPage;
    }

    @Override
    protected TeamIssueList parseList(InputStream is) throws Exception {
        TeamIssueList list = XmlUtils.toBean(TeamIssueList.class, is);
        return list;
    }

    @Override
    protected void sendRequestData() {
        OSChinaTeamApi.getTeamIssueList(mTeamId, mProjectId, 0, "", 253900,
                "state", "scope", mCurrentPage, AppContext.PAGE_SIZE, mHandler);
    }

    @Override
    protected ListEntity<TeamIssue> readList(Serializable seri) {
        return null;
    }
}
