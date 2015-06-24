package cn.szu.edu.app.team.bean;

import java.util.List;

import cn.szu.edu.app.bean.Entity;
import cn.szu.edu.app.bean.ListEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Replies.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 *
 * @data 2015-1-30 下午4:01:42
 */
@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class TeamRepliesList extends Entity implements ListEntity<TeamReply> {

    @XStreamAlias("pagesize")
    private int pagesize;
    
    @XStreamAlias("totalCount")
    private int totalCount;
    
    @XStreamAlias("replies")
    private List<TeamReply> list;
    
    @Override
    public List<TeamReply> getList() {
	return list;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<TeamReply> list) {
        this.list = list;
    }
    

}

