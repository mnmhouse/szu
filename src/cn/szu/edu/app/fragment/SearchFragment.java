package cn.szu.edu.app.fragment;

import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.adapter.SearchAdapter;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.SearchList;
import cn.szu.edu.app.bean.SearchResult;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

public class SearchFragment extends BaseListFragment<SearchResult> {
	protected static final String TAG = SearchFragment.class.getSimpleName();
	private static final String CACHE_KEY_PREFIX = "search_list_";
	private String mCatalog;
	private String mSearch;
	private boolean mRquestDataIfCreated = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			mCatalog = args.getString(BUNDLE_KEY_CATALOG);
		}
		int mode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
				| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
		getActivity().getWindow().setSoftInputMode(mode);
	}

	public void search(String search) {
		mSearch = search;
		if (mErrorLayout != null) {
			mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
			mState = STATE_REFRESH;
			requestData(true);
		} else {
			mRquestDataIfCreated = true;
		}
	}

	@Override
	protected boolean requestDataIfViewCreated() {
		return mRquestDataIfCreated;
	}

	@Override
	protected SearchAdapter getListAdapter() {
		return new SearchAdapter();
	}

	@Override
	protected String getCacheKeyPrefix() {
		return CACHE_KEY_PREFIX + mCatalog + mSearch;
	}

	@Override
	protected SearchList parseList(InputStream is) throws Exception {
		SearchList list = XmlUtils.toBean(SearchList.class, is);
		return list;
	}

	@Override
	protected SearchList readList(Serializable seri) {
		return ((SearchList) seri);
	}
	
	@Override
	protected void sendRequestData() {
		OSChinaApi.getSearchList(mCatalog, mSearch, mCurrentPage, mHandler);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SearchResult res = (SearchResult) mAdapter.getItem(position);
		if (res != null)
			UIHelper.showUrlRedirect(view.getContext(), res.getUrl());
	}
}
