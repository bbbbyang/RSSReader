package com.example.bing.rssreader.view.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bing.rssreader.R;
import com.example.bing.rssreader.model.Entry;
import com.example.bing.rssreader.utils.DateUtils;
import com.example.bing.rssreader.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bing on 2017/5/30.
 */

public class ItemActivity extends AppCompatActivity {

	public static final String KEY_ENTRY = "entry";
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.imageView) ImageView imageView;
	@BindView(R.id.author_id) TextView authorId;
	@BindView(R.id.item_updated) TextView updated;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.link) TextView link;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		init();

	}

	public void init() {
		Entry entry = ModelUtils.toObject(getIntent().getExtras().getString(KEY_ENTRY), new TypeToken<Entry>(){});
		setTitle(entry.category);
		if(!"".equals(entry.image)) {
			Picasso.with(this)
					.load(entry.image)
					.placeholder(R.drawable.shot_placeholder)
					.into(imageView);
		} else {
			imageView.setImageResource(R.drawable.shot_placeholder);
		}
		authorId.setText(entry.id);
		updated.setText(DateUtils.longToString(entry.updated));
		title.setText(entry.title);
		link.setText(entry.link);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
