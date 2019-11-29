package co.id.ramadanrizky.consumerwatchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import co.id.ramadanrizky.consumerwatchmovie.DetailActivity;
import co.id.ramadanrizky.consumerwatchmovie.R;
import co.id.ramadanrizky.consumerwatchmovie.entity.ListMovieEntity;

import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavViewHolder> {

    private Context context;
    private ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();

    public void setListMovieEntities(ArrayList<ListMovieEntity> entities) {
        listMovieEntities.clear();
        listMovieEntities.addAll(entities);
        notifyDataSetChanged();
    }

    public MovieFavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);

        return new MovieFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavViewHolder holder, int position) {
        holder.bindMovies(listMovieEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovieEntities.size();
    }

    public class MovieFavViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title, txt_date, txt_desc;
        private ImageView img_poster;
        private Button btn_delete;

        public MovieFavViewHolder(@NonNull View itemView) {
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
                    i.putExtra(DetailActivity.MOVIE, listMovieEntities.get(getPosition()));
                    context.startActivity(i);
                }
            });
        }

        public void bindMovies(final ListMovieEntity movieEntity) {
            txt_title.setText(movieEntity.getMovieTittle());
            txt_date.setText(movieEntity.getMovieDate());
            txt_desc.setText(movieEntity.getMovieDescription());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/"+movieEntity.getMoviePosterPath())
                    .into(img_poster);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = movieEntity.getId();
                    Uri uriId = Uri.parse(CONTENT_URI + "/" + id);
                    context.getContentResolver().delete(uriId, null, null);
                    listMovieEntities.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    notifyItemRangeChanged(getAdapterPosition(), listMovieEntities.size());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Deleted "+movieEntity.getMovieTittle()+ " from favorite", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
