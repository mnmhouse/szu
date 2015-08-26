package cn.szu.edu.app.ui;

import cn.szu.edu.app.R;
import cn.szu.edu.app.fragment.MyInformationFragment;
import cn.szu.edu.app.fragment.PictureListFragment;
import cn.szu.edu.app.fragment.TopNewsFragment;
import cn.szu.edu.app.fragment.TopicListFragment;
import cn.szu.edu.app.viewpagerfragment.CollegeNoticeViewPagerFragment;

public enum MainTab {

	TWEET(0, R.string.main_tab_name_forum, R.drawable.tab_icon_new,
			TopNewsFragment.class),
			
	GROUP(1, R.string.main_tab_name_weiqun, R.drawable.tab_icon_tweet,
			PictureListFragment.class),			
			
	TOPICLIST(2, R.string.main_tab_name_question, R.drawable.tab_icon_tweet,
			TopicListFragment.class),

	NEWS(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
			CollegeNoticeViewPagerFragment.class),

	ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
			MyInformationFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
