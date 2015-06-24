package cn.szu.edu.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.R;
import cn.szu.edu.app.base.ListBaseAdapter;
import cn.szu.edu.app.bean.SoftwareDec;
import cn.szu.edu.app.bean.SoftwareList;

public class SoftwareAdapter extends ListBaseAdapter<SoftwareDec> {
	
	static class ViewHold{
		@InjectView(R.id.tv_title)TextView name;
		@InjectView(R.id.tv_software_des)TextView des;
		public ViewHold(View view) {
			ButterKnife.inject(this,view);
		}
	}

	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		
		ViewHold vh = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.list_cell_software, null);
			vh = new ViewHold(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHold)convertView.getTag();
		}
		
		SoftwareDec softwareDes = (SoftwareDec) mDatas.get(position);
		vh.name.setText(softwareDes.getName());
		
        if (AppContext.isOnReadedPostList(SoftwareList.PREF_READED_SOFTWARE_LIST, softwareDes.getName())) {
        	vh.name.setTextColor(parent.getContext().getResources().getColor(R.color.main_gray));
        } else {
        	vh.name.setTextColor(parent.getContext().getResources().getColor(R.color.main_black));
        }
		
		vh.des.setText(softwareDes.getDescription());
		
		return convertView;
	}
}
