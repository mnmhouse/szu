package cn.szu.edu.app.team.bean;

import cn.szu.edu.app.bean.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/***
 * 帖子、任务、讨论的创建者， 
 * 		注：节点
 * 
 * @author fireant
 *
 */
@SuppressWarnings("serial")
@XStreamAlias("author")
public class Author extends Entity {
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("portrait")
	private String portrait;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

}
