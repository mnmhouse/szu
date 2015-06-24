package cn.szu.edu.app.fragment;

import java.io.ByteArrayInputStream;
import java.util.List;

import cn.szu.edu.app.R;

import org.apache.http.Header;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import cn.szu.edu.app.adapter.FindUserAdapter;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseFragment;
import cn.szu.edu.app.bean.FindUserList;
import cn.szu.edu.app.bean.ListEntity;
import cn.szu.edu.app.bean.User;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 查找用户界面
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年12月8日 下午3:50:20
 * 
 */

public class FindUserFragment extends BaseFragment implements
	OnItemClickListener {

    private SearchView mSearchView;

    @InjectView(R.id.lv_list)
    ListView mListView;

    @InjectView(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private FindUserAdapter mAdapter;

    private AsyncHttpResponseHandler mHandle = new AsyncHttpResponseHandler() {

	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
	    mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
	    ListEntity<User> list = XmlUtils.toBean(FindUserList.class,
		    new ByteArrayInputStream(arg2));
	    executeOnLoadDataSuccess(list.getList());
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		Throwable arg3) {
	    mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
	}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_find_user, null);
	ButterKnife.inject(this, view);
	setHasOptionsMenu(true);
	initView(view);
	return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	inflater.inflate(R.menu.search_menu, menu);
	MenuItem search = menu.findItem(R.id.search_content);
	mSearchView = (SearchView) search.getActionView();
	mSearchView.setIconifiedByDefault(false);
	setSearch();
	super.onCreateOptionsMenu(menu, inflater);
    }

    private void setSearch() {
	mSearchView.setQueryHint("输入用户昵称");
	TextView textView = (TextView) mSearchView
		.findViewById(R.id.search_src_text);
	textView.setTextColor(Color.WHITE);

	mSearchView
		.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

		    @Override
		    public boolean onQueryTextSubmit(String arg0) {
			return false;
		    }

		    @Override
		    public boolean onQueryTextChange(String arg0) {
			search(arg0);
			return false;
		    }
		});
    }

    private void search(String nickName) {
	if (nickName == null || StringUtils.isEmpty(nickName)) {
	    return;
	}
	mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
	mListView.setVisibility(View.GONE);
	OSChinaApi.findUser(nickName, mHandle);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initView(View view) {
	mAdapter = new FindUserAdapter();
	mListView.setAdapter(mAdapter);
	mListView.setOnItemClickListener(this);

	mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		search(mSearchView.getQuery().toString());
	    }
	});
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	User user = (User) mAdapter.getItem(position);
	if (user != null)
	    UIHelper.showUserCenter(getActivity(), user.getId(),
		    user.getName());
    }

    private void executeOnLoadDataSuccess(List<User> data) {
	mAdapter.clear();
	mAdapter.addData(data);
	mListView.setVisibility(View.VISIBLE);
    }
}
