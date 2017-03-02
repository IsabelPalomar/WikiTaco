package com.wikitaco.demo.tacolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.wikitaco.demo.App;
import com.wikitaco.demo.R;
import com.wikitaco.demo.models.Taco;

/**
 * Created by ykro.
 */

public class TacoRecyclerAdapter extends FirebaseRecyclerAdapter<Taco,TacoRecyclerAdapter.TacoViewHolder> {
  private Context context;
  private OnItemClickListener clickListener;
  private final static int layoutId = R.layout.item_taco;

  public TacoRecyclerAdapter(Context context, DatabaseReference databaseReference, OnItemClickListener clickListener) {
    super(Taco.class, layoutId, TacoViewHolder.class, databaseReference);
    this.context = context;
    this.clickListener = clickListener;
  }

  @Override
  protected void populateViewHolder(TacoViewHolder viewHolder, Taco model, int position) {
    viewHolder.setClickListener(model, this.clickListener);
    viewHolder.tvTacoName.setText(model.getName());

    StorageReference storageReference =
        ((App)context.getApplicationContext())
          .getTacoStorageReference(
              getRef(position)
                  .getKey());

    Glide.with(context)
        .using(new FirebaseImageLoader())
        .load(storageReference)
        .centerCrop()
        .crossFade()
        .into(viewHolder.ivTacoImg);
  }

  public static class TacoViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTacoName;
    private ImageView ivTacoImg;
    private View view;

    public TacoViewHolder(View v) {
      super(v);
      tvTacoName = (TextView) v.findViewById(R.id.tvTacoName);
      ivTacoImg = (ImageView) v.findViewById(R.id.ivTacoImg);
      this.view = v;
    }

    public void setClickListener(final Taco taco, final OnItemClickListener clickListener) {
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          clickListener.onItemClick(taco);
        }
      });
    }
  }
}
