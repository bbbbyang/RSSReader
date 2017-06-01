package com.example.bing.rssreader.view.item;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.bing.rssreader.R;
import com.example.bing.rssreader.data.EntryContract;
import com.example.bing.rssreader.data.EntryDbHelper;
import com.example.bing.rssreader.model.Entry;
import com.example.bing.rssreader.utils.FeedParser;
import com.example.bing.rssreader.view.MainActivity;
import com.example.bing.rssreader.view.base.SpaceItemDecoration;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Bing on 2017/5/29.
 */

public class ItemFragment extends Fragment {

	@BindView(R.id.recycler_view) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;
	@BindView(R.id.swipe_fresh_layout) SwipeRefreshLayout swipeRefreshLayout;
	private ItemAdapter adapter;
	private int listType;

	public static final String KEY_LIST_TYPE = "listType";
	public static final int LIST_TYPE_HOT = 1;
	public static final int LIST_TYPE_LIKED = 2;

	private SQLiteDatabase mDb;

	public static ItemFragment getInstance() {
		ItemFragment itemFragment = new ItemFragment();
		return itemFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		listType = LIST_TYPE_HOT;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

		EntryDbHelper dbHelper = new EntryDbHelper(getActivity());
		mDb = dbHelper.getWritableDatabase();

		adapter = new ItemAdapter(new ArrayList<Entry>(), mDb);

		URL redditUrl = null;
		try {
			redditUrl = new URL("https://www.reddit.com/hot/.rss");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		new XmlParsing().execute(redditUrl);
		recyclerView.setAdapter(adapter);

		((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
			@Override
			public void onRefresh(int listType) {
				adapter.showLikedEntry();
				changeListType(listType);
			}
		});

		final URL finalRedditUrl = redditUrl;

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				int listType = getListType();
				if(listType == ItemFragment.LIST_TYPE_LIKED) {
					adapter.showLikedEntry();
					swipeRefreshLayout.setRefreshing(false);
				} else if(listType == ItemFragment.LIST_TYPE_HOT) {
					adapter.resetData();
					new XmlParsing().execute(finalRedditUrl);
					recyclerView.setAdapter(adapter);
					swipeRefreshLayout.setRefreshing(false);
				}
			}
		});

	}

	void changeListType(int listType) {
		this.listType = listType;
	}
	int getListType() {
		return this.listType;
	}

	public class XmlParsing extends AsyncTask<URL, String, List<Entry>> {

		@Override
		protected List<Entry> doInBackground(URL... urls) {
			URL rssUrl = urls[0];
			List<Entry> entryList = null;
			try {
				entryList = new FeedParser().parse(rssUrl.openStream());
			} catch (XmlPullParserException | IOException | ParseException e) {
				e.printStackTrace();
			}
			return entryList;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			recyclerView.setVisibility(View.GONE);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(List<Entry> entryList) {
			recyclerView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			if(entryList != null) {
				adapter.append(entryList);
			}
		}
	}

}
