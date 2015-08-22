package cn.szu.edu.app.team.bean;

import cn.szu.edu.app.bean.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 团队任务实体类
 * 
 * TeamIssueDetail.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 * 
 * @data 2015-1-27 下午7:44:38
 */
@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class TeamIssueDetail extends Entity {

    @XStreamAlias("issue")
    private TeamIssue teamIssue;

    public TeamIssue getTeamIssue() {
        return teamIssue;
    }

    public void setTeamIssue(TeamIssue teamIssue) {
        this.teamIssue = teamIssue;
    }

}
