package com.mobile.moviebooking.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.moviebooking.Activity.BookedActivity;
import com.mobile.moviebooking.R;
import com.mobile.moviebooking.db.DatabaseClient;
import com.mobile.moviebooking.db.MovieBook;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mCtx;
    private List<MovieBook> movieBooks;
   String view;

    public MovieAdapter(Context mCtx, List<MovieBook> movieBooks,String view) {
        this.mCtx = mCtx;
        this.movieBooks = movieBooks;
        this.view = view;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieBook m = movieBooks.get(position);
        if(view.equals("0")){
            holder.btn_book.setVisibility(View.VISIBLE);
            holder.MoviePrice.setVisibility(View.GONE);
            holder.linearCash.setVisibility(View.GONE);
            holder.linearPay.setVisibility(View.GONE);
        }else if(view.equals("10")){
            holder.btn_book.setVisibility(View.GONE);
            holder.MoviePrice.setVisibility(View.GONE);
            holder.linearCash.setVisibility(View.GONE);
            holder.linearPay.setVisibility(View.GONE);
        }else {
            holder.btn_book.setVisibility(View.GONE);
            holder.linearCash.setVisibility(View.VISIBLE);
            holder.linearPay.setVisibility(View.VISIBLE);
            holder.MoviePrice.setVisibility(View.GONE);
        }
        holder.Movieonline.setText(String.valueOf(m.getOnlineBooking()+" / "+m.getCollectionOnline()));
        holder.Moviecash.setText(String.valueOf(m.getCashbooking()+" / "+m.getCollectionCash()));

        holder.Moviename.setText(m.getMoviename());
        holder.MoviePrice.setText(m.getTicketPrice());
        holder.Movietiming.setText(m.getTiming());
        holder.Moviedate.setText(m.getBookdate());
        holder.available.setText(m.getAvailable());

        try {
            Uri image = Uri.parse(m.getImagepath());
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mCtx.getContentResolver() , image);

            holder.image.setImageBitmap(bitmap);
        }catch (Exception exception){
            holder.image.setImageResource(R.drawable.dummy);
        }
        Glide.with(mCtx).load(m.getImagepath()).placeholder(R.drawable.dummy).into(holder.image);
        if(holder.available.getText().toString().equals("0")){
            holder.btn_book.setText("No Seats Available");
            holder.btn_book.setEnabled(false);
        }
        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mCtx, BookedActivity.class);
                intent.putExtra("Moviename",m.getMoviename());
                intent.putExtra("Availableseats",m.getAvailable());
                intent.putExtra("MovieCollectionCash",m.getCollectionCash());
                intent.putExtra("MovieCollectionOnline",m.getCollectionOnline());
                intent.putExtra("MovieOnline",m.getOnlineBooking());
                intent.putExtra("MovieCash",m.getCashbooking());
                intent.putExtra("MovieId",m.getId());
                intent.putExtra("TicketPrice",m.getTicketPrice());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieBooks.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Moviename, MoviePrice, Movietiming, Moviedate,available,Movieonline,Moviecash;
        Button btn_book;
        LinearLayout linearCash,linearPay;
        ImageView image;
        public MovieViewHolder(View itemView) {
            super(itemView);

            Moviename = itemView.findViewById(R.id.Moviename);
            Movieonline = itemView.findViewById(R.id.Movieonline);
            MoviePrice = itemView.findViewById(R.id.MoviePrice);
            Movietiming = itemView.findViewById(R.id.Movietiming);
            linearCash = itemView.findViewById(R.id.linearCash);
            linearPay = itemView.findViewById(R.id.linearPay);
            Moviecash = itemView.findViewById(R.id.Moviecash);
            Moviedate = itemView.findViewById(R.id.Moviedate);
            image = itemView.findViewById(R.id.image);
            available = itemView.findViewById(R.id.available);
            btn_book = itemView.findViewById(R.id.btn_book);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
