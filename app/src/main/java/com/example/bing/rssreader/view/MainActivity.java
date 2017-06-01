package com.example.bing.rssreader.view;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bing.rssreader.R;
import com.example.bing.rssreader.data.EntryDbHelper;
import com.example.bing.rssreader.data.TestUtil;
import com.example.bing.rssreader.view.item.ItemFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.toolbar) Toolbar toolbar;
	private boolean like;
	private FragmentRefreshListener fragmentRefreshListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		like = false;
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		setTitle("Reddit");

		if(savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.fragment_container, ItemFragment.getInstance())
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.toolbar_like) {
			if(!like) {
				item.setIcon(R.drawable.ic_favorite_black_18dp);
				setTitle("Liked");
				like = true;
				if(getFragmentRefreshListener()!=null){
					getFragmentRefreshListener().onRefresh(ItemFragment.LIST_TYPE_LIKED);
				}
			} else {
				item.setIcon(R.drawable.ic_favorite_border_black_18dp);
				setTitle("Reddit");
				Fragment fragment = ItemFragment.getInstance();
				if(fragment != null) {
					getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.fragment_container, ItemFragment.getInstance())
							.commit();
				}
				like = false;
			}
		}

		return super.onOptionsItemSelected(item);
	}


	public FragmentRefreshListener getFragmentRefreshListener() {
		return fragmentRefreshListener;
	}

	public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
		this.fragmentRefreshListener = fragmentRefreshListener;
	}

	public interface FragmentRefreshListener{
		void onRefresh(int ListType);
	}

}
