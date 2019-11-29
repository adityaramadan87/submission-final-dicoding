package co.id.ramadanrizky.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.DetailActivity;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.entity.TVEntity;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    Context context;
    ArrayList<ListTVEntity> listTVEntities = new ArrayList<>();

    public void setListTVEntities(ArrayList<ListTVEntity> tvEntities) {
        listTVEntities.clear();
        listTVEntities.addAll(tvEntities);
        notifyDataSetChanged();
    }

    public TVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        holder.bind(listTVEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return listTVEntities.size();
    }

    public class TVViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtTitleTV, mTxtDateTV, mTxtDescTV;
        ImageView mImgPosterTV;

        public TVViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtTitleTV = itemView.findViewById(R.id.txt_title_movie);
            mTxtDescTV = itemView.findViewById(R.id.txt_desc_movie);
            mTxtDateTV = itemView.findViewById(R.id.txt_date_movie);
            mImgPosterTV = itemView.findViewById(R.id.img_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra(DetailActivity.TVSHOW, listTVEntities.get(getPosition()));
                    context.startActivity(i);
                }
            });
        }

        public void bind(ListTVEntity listtvEntity) {
            mTxtTitleTV.setText(listtvEntity.getTvTittle());
            mTxtDescTV.setText(listtvEntity.getTvDescription());
            mTxtDateTV.setText(listtvEntity.getTvDate());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/"+listtvEntity.getTvPoster())
                    .into(mImgPosterTV);
        }
    }
}
