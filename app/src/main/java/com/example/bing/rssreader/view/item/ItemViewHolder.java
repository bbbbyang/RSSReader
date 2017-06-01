package com.example.bing.rssreader.view.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bing.rssreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bing on 2017/5/30.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.shot_image) ImageView image;
	@BindView(R.id.item_updated) TextView updated;
	@BindView(R.id.category) TextView category;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.shot_clickable_cover) View cover;
	@BindView(R.id.like) ImageButton like;

	public ItemViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}
