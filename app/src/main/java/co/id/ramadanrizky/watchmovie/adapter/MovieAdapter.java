package co.id.ramadanrizky.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.DetailActivity;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.MovieEntity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVHolder> {

    Context context;
    ArrayList<ListMovieEntity> entListMovie = new ArrayList<>();

    public void setEntMovie(ArrayList<ListMovieEntity> entMovie) {
        entListMovie.clear();
        entListMovie.addAll(entMovie);
        notifyDataSetChanged();
    }

    public ArrayList<ListMovieEntity> getEntListMovie() {
        return entListMovie;
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieVHolder holder, int position) {
        holder.bind(entListMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return entListMovie.size();
    }

    public class MovieVHolder extends RecyclerView.ViewHolder {

        TextView mTxtTitleMovie, mTxtDescriptionMovie, mTxtDateMovie;
        ImageView mImgPosterMovie;

        public MovieVHolder(@NonNull final View itemView) {
            super(itemView);
            mTxtTitleMovie = itemView.findViewById(R.id.txt_title_movie);
            mTxtDescriptionMovie = itemView.findViewById(R.id.txt_desc_movie);
            mTxtDateMovie = itemView.findViewById(R.id.txt_date_movie);
            mImgPosterMovie = itemView.findViewById(R.id.img_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra(DetailActivity.MOVIE, entListMovie.get(getPosition()));
                    context.startActivity(i);
                }
            });

        }

        public void bind(ListMovieEntity listMovieEntity) {
            mTxtTitleMovie.setText(listMovieEntity.getMovieTittle());
            mTxtDescriptionMovie.setText(listMovieEntity.getMovieDescription());
            mTxtDateMovie.setText(listMovieEntity.getMovieDate());
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/"+listMovieEntity.getMoviePosterPath())
                    .into(mImgPosterMovie);
        }
    }
}
