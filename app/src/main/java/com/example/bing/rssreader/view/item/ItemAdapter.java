package com.example.bing.rssreader.view.item;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bing.rssreader.R;
import com.example.bing.rssreader.data.EntryContract;
import com.example.bing.rssreader.data.TestUtil;
import com.example.bing.rssreader.model.Entry;
import com.example.bing.rssreader.utils.DateUtils;
import com.example.bing.rssreader.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bing on 2017/5/30.
 */

public class ItemAdapter extends RecyclerView.Adapter {

	private List<Entry> data;
	SQLiteDatabase mDb;

	public ItemAdapter(List<Entry> data, SQLiteDatabase mDb) {
		this.data = data;
		this.mDb = mDb;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
		final Entry entry = data.get(position);

		itemViewHolder.title.setText(entry.title);
		itemViewHolder.updated.setText(DateUtils.longToString(entry.updated));
		itemViewHolder.category.setText(entry.category);

		long _id = TestUtil.checkDataBaseRecord(mDb, entry);
		if(_id != -1) {
			entry.like = true;
			entry._id = _id;
		}

		if (entry.like) {
			itemViewHolder.like.setImageResource(R.drawable.ic_favorite_black_18dp);
		} else {
			itemViewHolder.like.setImageResource(R.drawable.ic_favorite_border_black_18dp);
		}




		if(!"".equals(entry.image)) {
			Picasso.with(itemViewHolder.itemView.getContext())
					.load(entry.image)
					.placeholder(R.drawable.shot_placeholder)
					.into(itemViewHolder.image);
		} else {
			itemViewHolder.image.setImageResource(R.drawable.shot_placeholder);
		}

		itemViewHolder.cover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = holder.itemView.getContext();
				Intent intent = new Intent(context, ItemActivity.class);
				intent.putExtra(ItemActivity.KEY_ENTRY, ModelUtils.toString(entry, new TypeToken<Entry>(){}));
				context.startActivity(intent);
			}
		});

		itemViewHolder.like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!entry.like) {
					entry.like = true;
					itemViewHolder.like.setImageResource(R.drawable.ic_favorite_black_18dp);
					entry._id = TestUtil.insertData(mDb, entry);
				} else {
					entry.like = false;
					itemViewHolder.like.setImageResource(R.drawable.ic_favorite_border_black_18dp);
					if(TestUtil.deleteData(mDb, entry._id)) {
						entry._id = -1;
					}
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public void append(@NonNull List<Entry> moreShots) {
		Collections.sort(moreShots, new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				return o1.updated > o2.updated ? -1 : 1;
			}
		});
		data.addAll(moreShots);
		notifyDataSetChanged();
	}

	public void showLikedEntry() {
		Iterator<Entry> iter = data.iterator();
		while(iter.hasNext()) {
			Entry entry = iter.next();
			if(!entry.like) {
				iter.remove();
			}
		}
		notifyDataSetChanged();
	}

	public void resetData() {
		data.clear();
		notifyDataSetChanged();
	}
}
