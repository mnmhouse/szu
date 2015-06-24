package cn.szu.edu.app.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.szu.edu.app.Constant;
import cn.szu.edu.app.DeviceInfoUtil;
import cn.szu.edu.app.R;
import cn.szu.edu.app.bean.PicsBean;
import cn.szu.edu.app.ui.WebActivity;

import com.dodowaterfall.Helper;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.networkbench.com.google.gson.Gson;
import com.networkbench.com.google.gson.reflect.TypeToken;

public class PictureListFragment extends Fragment implements Constant {
    private ImageFetcher mImageFetcher;
    private XListView mAdapterView = null;
    private StaggeredAdapter mAdapter = null;
    private int currentPage = 1;
    ContentTask task = new ContentTask(getActivity(), 2);

    private class ContentTask extends AsyncTask<String, Integer, List<PicsBean>> {

        private Context mContext;
        private int mType = 1;

        public ContentTask(Context context, int type) {
            super();
            mContext = context;
            mType = type;
        }

        @Override
        protected List<PicsBean> doInBackground(String... params) {
            try {
                return parseNewsJSON(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PicsBean> result) {
            if (mType == 1) {

                mAdapter.addItemTop(result);
                mAdapter.notifyDataSetChanged();
                mAdapterView.stopRefresh();

            } else if (mType == 2) {
                mAdapterView.stopLoadMore();
                mAdapter.addItemLast(result);
                mAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        public List<PicsBean> parseNewsJSON(String url) throws IOException {
            List<PicsBean> picsList = new ArrayList<PicsBean>();
            String json = "";
            if (Helper.checkConnection(mContext)) {
                try {
                    json = Helper.getStringFromUrl(url);

                } catch (IOException e) {
                    Log.e("IOException is : ", e.toString());
                    e.printStackTrace();
                    return picsList;
                }
            }
            Log.i("MainActiivty", "json:" + json);

            try {
                if (null != json) {
//                    JSONObject newsObject = new JSONObject(json);
//                    JSONObject jsonObject = newsObject.getJSONObject("data");
//                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");
//                    JSONArray blogsJson = new JSONArray(json);
                    picsList = new Gson().fromJson(json,new TypeToken<List<PicsBean>>() {}.getType());
//                    for (int i = 0; i < blogsJson.length(); i++) {
//                        JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
//                        Log.i("MainActiivty", newsInfoLeftObject.toString());
//                        PicsBean newsInfo1 = new PicsBean();
//                        newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "" : newsInfoLeftObject.getString("albid"));
//                        newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc"));
//                        newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg"));
//                        newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
//                        newsInfo1.setAlbid(newsInfoLeftObject.isNull("id") ? "" : newsInfoLeftObject.getString("id"));
//                        newsInfo1.setIsrc(newsInfoLeftObject.isNull("thumbnail_url") ? "" : newsInfoLeftObject.getString("thumbnail_url"));
//                        newsInfo1.setMsg(newsInfoLeftObject.isNull("post_title") ? "" : newsInfoLeftObject.getString("post_title"));
//                        newsInfo1.setHeight(200);
//                        duitangs.add(newsInfo1);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return picsList;
        }
    }

    /**
     * 添加内容
     * 
     * @param pageindex
     * @param type
     *            1为下拉刷新 2为加载更多
     */
    private void AddItemToContainer(int pageindex, int type) {
        if (task.getStatus() != Status.RUNNING) {
//            String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
        	String url = Constant.HTTP_SERVER + Constant.HTTP_PICS;
			String sign = DeviceInfoUtil.getAndroidId(getActivity());
			JSONObject param = new JSONObject();
			try {
				param.put("pageIndex", pageindex);
				param.put("sign", sign);
			} catch (JSONException e) {
				Log.e("PictureListActivity", e.getMessage());
			}
			url += "key=" + Uri.encode(param.toString());
            Log.d("MainActivity", "current url:" + url);
            ContentTask task = new ContentTask(getActivity(), type);
            task.execute(url);

        }
    }

    public class StaggeredAdapter extends BaseAdapter {
        private Context mContext;
        private LinkedList<PicsBean> mInfos;
        private XListView mListView;

        public StaggeredAdapter(Context context, XListView xListView) {
            mContext = context;
            mInfos = new LinkedList<PicsBean>();
            mListView = xListView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            final PicsBean pic = mInfos.get(position);

            if (convertView == null) {
                LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
                convertView = layoutInflator.inflate(R.layout.infos_list, null);
                holder = new ViewHolder();
                holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);
                holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.imageView.setImageWidth(200);
            holder.imageView.setImageHeight(200);
            holder.contentView.setText(pic.getPost_title());
            mImageFetcher.loadImage(pic.getThumbnail_url(), holder.imageView);
            holder.imageView.setOnClickListener( new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), WebActivity.class);
					intent.putExtra("notification", "");
					intent.putExtra("postId", pic.getId());
					intent.putExtra("title", pic.getPost_title());
					intent.putExtra("url", pic.getPost_url() + "&app=android");
					startActivity(intent);
				}
			});
            
            return convertView;
        }

        class ViewHolder {
            ScaleImageView imageView;
            TextView contentView;
            TextView timeView;
        }

        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mInfos.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        public void addItemLast(List<PicsBean> datas) {
            mInfos.addAll(datas);
        }

        public void addItemTop(List<PicsBean> datas) {
            for (PicsBean info : datas) {
                mInfos.addFirst(info);
            }
        }
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pull_to_refresh_pics, container, false);
        mAdapterView = (XListView)view. findViewById(R.id.picture_list);
        mAdapterView.setPullLoadEnable(true);
        mAdapterView.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh() {
                AddItemToContainer(++currentPage, 1);

            }

            @Override
            public void onLoadMore() {
                AddItemToContainer(++currentPage, 2);

            }
        });

        mAdapter = new StaggeredAdapter(getActivity(), mAdapterView);

        mImageFetcher = new ImageFetcher(getActivity(), 240);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        
        
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
	public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        mAdapterView.setAdapter(mAdapter);
        AddItemToContainer(currentPage, 2);
    }

    @Override
	public void onDestroy() {
        super.onDestroy();

    }

}
