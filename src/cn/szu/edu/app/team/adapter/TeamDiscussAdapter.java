package cn.szu.edu.app.team.adapter;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.team.bean.TeamDiscuss;
import cn.szu.edu.app.util.HTMLUtil;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.util.TypefaceUtils;
import cn.szu.edu.app.widget.AvatarView;
import cn.szu.edu.app.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * team 讨论区帖子
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年10月9日 下午6:22:54
 * 
 */
public class TeamDiscussAdapter extends ListBaseAdapter<TeamDiscuss> {

    static class ViewHolder {

	@InjectView(R.id.tv_title)
	TextView title;
	@InjectView(R.id.tv_description)
	TextView description;
	@InjectView(R.id.tv_author)
	TextView author;
	@InjectView(R.id.tv_date)
	TextView time;
	@InjectView(R.id.tv_count)
	TextView comment_count;
	@InjectView(R.id.tv_vote_up)
	TextView vote_up;

	@InjectView(R.id.iv_face)
	public AvatarView face;

	public ViewHolder(View view) {
	    ButterKnife.inject(this, view);
	}
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
	ViewHolder vh = null;
	if (convertView == null || convertView.getTag() == null) {
	    convertView = getLayoutInflater(parent.getContext()).inflate(
		    R.layout.list_cell_team_discuss, null);
	    vh = new ViewHolder(convertView);
	    convertView.setTag(vh);
	} else {
	    vh = (ViewHolder) convertView.getTag();
	}

	TeamDiscuss item = mDatas.get(position);

	vh.face.setUserInfo(item.getAuthor().getId(), item.getAuthor()
		.getName());
	vh.face.setAvatarUrl(item.getAuthor().getPortrait());
	vh.title.setText(item.getTitle());
	String body = item.getBody().trim();
	vh.description.setVisibility(View.GONE);
	if (null != body || !StringUtils.isEmpty(body)) {
	    vh.description.setVisibility(View.VISIBLE);
	    vh.description.setText(HTMLUtil.replaceTag(item.getBody()).trim());
	}
	TypefaceUtils.setTypeface(vh.author, item.getAuthor().getName());
	String faTime = AppContext.getInstance().getResources().getString(R.string.fa_clock_o);
	TypefaceUtils.setTypeface(vh.time, faTime + " " + StringUtils.friendly_time(item.getCreateTime()));
	String faVoteUp = AppContext.getInstance().getResources().getString(R.string.fa_thumbs_o_up);
	TypefaceUtils.setTypeface(vh.vote_up, faVoteUp + " " + item.getVoteUp());
	String commentCount = AppContext.getInstance().getResources().getString(R.string.fa_comment);
	TypefaceUtils.setTypeface(vh.comment_count, commentCount + " " + item.getAnswerCount());
	return convertView;
    }
}
