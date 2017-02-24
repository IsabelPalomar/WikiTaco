package com.wikitaco.demo.tacolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.wikitaco.demo.App;
import com.wikitaco.demo.R;
import com.wikitaco.demo.models.Taco;

/**
 * Created by ykro.
 */

public class TacoRecyclerAdapter extends FirebaseRecyclerAdapter<Taco,TacoRecyclerAdapter.TacoViewHolder> {
  private Context context;

  private static final int TYPE_FULL = 0;
  private static final int TYPE_HALF = 1;
  private static final int TYPE_QUARTER = 2;
  private final static int layoutId = R.layout.item_taco;

  public TacoRecyclerAdapter(Context context, DatabaseReference databaseReference) {
    super(Taco.class, layoutId, TacoViewHolder.class, databaseReference);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(TacoViewHolder viewHolder, Taco model, int position) {
    viewHolder.tvTacoName.setText(model.getName());

    StorageReference storageReference =
        ((App)context.getApplicationContext())
          .getTacoStorageReference(
              getRef(position)
                  .getKey());
    Log.e("ASDF",storageReference.toString());
    Glide.with(context)
        .using(new FirebaseImageLoader())
        .load(storageReference)
        .into(viewHolder.ivTacoImg);

    Picasso.with(context).load(model.getImageUrl()).into(viewHolder.ivTacoImg);

  }

  /*
  //http://stackoverflow.com/questions/36514887/layoutmanager-for-recyclerview-grid-with-different-cell-width
  @Override
  public TacoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
    final View itemView =
        LayoutInflater.from(context).inflate(layoutId, parent, false);
    itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        final int type = viewType;
        final ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
          StaggeredGridLayoutManager.LayoutParams sglp =
              (StaggeredGridLayoutManager.LayoutParams) lp;
          switch (type) {
            case TYPE_FULL:
              sglp.setFullSpan(true);
              break;
            case TYPE_HALF:
              sglp.setFullSpan(false);
              sglp.width = itemView.getWidth() / 2;
              break;
            case TYPE_QUARTER:
              sglp.setFullSpan(false);
              sglp.width = itemView.getWidth() / 2;
              sglp.height = itemView.getHeight() / 2;
              break;
          }
          itemView.setLayoutParams(sglp);
          final StaggeredGridLayoutManager lm =
              (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
          lm.invalidateSpanAssignments();
        }
        itemView.getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
      }
    });

    TacoViewHolder holder = new TacoViewHolder(itemView);
    return holder;
  }

  @Override
  public int getItemViewType(int position) {
    final int modeEight = position % 8;
    switch (modeEight) {
      case 0:
      case 5:
        return TYPE_HALF;
      case 1:
      case 2:
      case 4:
      case 6:
        return TYPE_QUARTER;
    }
    return TYPE_FULL;
  }
*/
  public static class TacoViewHolder extends RecyclerView.ViewHolder{

    TextView tvTacoName;
    ImageView ivTacoImg;

    public TacoViewHolder(View v) {
      super(v);
      tvTacoName = (TextView) v.findViewById(R.id.tvTacoName);
      ivTacoImg = (ImageView) v.findViewById(R.id.ivTacoImg);
    }
  }
}
