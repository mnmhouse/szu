package cn.szu.edu.app.viewpagerfragment;

import cn.szu.edu.app.adapter.ViewPageFragmentAdapter;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.BaseViewPagerFragment;
import cn.szu.edu.app.bean.Favorite;
import cn.szu.edu.app.fragment.UserFavoriteFragment;
import cn.szu.edu.app.R;
import android.os.Bundle;
import android.view.View;

public class UserFavoriteViewPagerFragment extends BaseViewPagerFragment {
	
	public static UserFavoriteViewPagerFragment newInstance(){
		return new UserFavoriteViewPagerFragment();
	}
	
	@Override
	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

		String[] title = getResources().getStringArray(R.array.userfavorite);
		adapter.addTab(title[0], "favorite_software", UserFavoriteFragment.class, getBundle(Favorite.CATALOG_SOFTWARE));
		adapter.addTab(title[1], "favorite_topic", UserFavoriteFragment.class, getBundle(Favorite.CATALOG_TOPIC));
		adapter.addTab(title[2], "favorite_code", UserFavoriteFragment.class, getBundle(Favorite.CATALOG_CODE));
		adapter.addTab(title[3], "favorite_blogs", UserFavoriteFragment.class, getBundle(Favorite.CATALOG_BLOGS));
		adapter.addTab(title[4], "favorite_news", UserFavoriteFragment.class, getBundle(Favorite.CATALOG_NEWS));
		
	}
	
	private Bundle getBundle(int favoriteType) {
		Bundle bundle = new Bundle();
		bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, favoriteType);
		return bundle;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initView(View view) {

	}

	@Override
	public void initData() {

	}

}
