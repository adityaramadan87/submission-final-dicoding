package co.id.ramadanrizky.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.DetailActivity;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;

public class TVFavoriteAdapter extends RecyclerView.Adapter<TVFavoriteAdapter.TVFavoriteViewHolder> {
    Context context;
    private ArrayList<ListTVEntity> listTVEntities = new ArrayList<>();

    public TVFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setListTVEntities(ArrayList<ListTVEntity> tvEntities) {
        listTVEntities.clear();
        listTVEntities.addAll(tvEntities);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TVFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new TVFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVFavoriteViewHolder holder, int position) {
        holder.bindTv(listTVEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return listTVEntities.size();
    }

    public class TVFavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title, txt_date, txt_desc;
        private ImageView img_poster;
        private Button btn_delete;

        public TVFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title_favorite);
            txt_date = itemView.findViewById(R.id.txt_date_favorite);
            txt_desc = itemView.findViewById(R.id.txt_desc_favorite);
            img_poster = itemView.findViewById(R.id.img_favorite);
            btn_delete = itemView.findViewById(R.id.btn_delete_favorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra(DetailActivity.TVSHOW, listTVEntities.get(getPosition()));
                    context.startActivity(i);
                }
            });
        }
        public void bindTv(final ListTVEntity tvEntity) {
            txt_title.setText(tvEntity.getTvTittle());
            txt_date.setText(tvEntity.getTvDate());
            txt_desc.setText(tvEntity.getTvDescription());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/"+tvEntity.getTvPoster())
                    .into(img_poster);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = tvEntity.getId();
                    FavoriteHelper favoriteHelper = new FavoriteHelper(context);
                    favoriteHelper.open();
                    favoriteHelper.deleteTvShow(id);
                    listTVEntities.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    notifyItemRangeChanged(getAdapterPosition(), listTVEntities.size());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Deleted "+tvEntity.getTvTittle()+ " from favorite", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
