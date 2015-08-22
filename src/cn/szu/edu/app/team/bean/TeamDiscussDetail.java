package cn.szu.edu.app.team.bean;

import cn.szu.edu.app.bean.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * TeamDisscussDetail.java
 * 
 * @author 火蚁(http://my.oschina.net/u/253900)
 *
 * @data 2015-2-2 下午5:33:07
 */
@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class TeamDiscussDetail extends Entity {
    
    @XStreamAlias("discuss")
    private TeamDiscuss discuss;

    public TeamDiscuss getDiscuss() {
        return discuss;
    }

    public void setDiscuss(TeamDiscuss discuss) {
        this.discuss = discuss;
    }
}

