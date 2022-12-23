package com.mobile.moviebooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.moviebooking.R;
import com.mobile.moviebooking.databinding.ActivityAddMovieBinding;
import com.mobile.moviebooking.db.DatabaseClient;
import com.mobile.moviebooking.db.MovieBook;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
TextView editTextDate;
String Imagepath;
ImageView image;
int s=0;
    List<MovieBook> movieBooks=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie);
        image=findViewById(R.id.image);
        getSupportActionBar().setTitle("Add Movie");
        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.mobile.moviebooking.PickerDialog.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.mobile.moviebooking.PickerDialog.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (DatabaseClient.getInstance(AddMovieActivity.this).getAppDatabase().movieDao().isDataExist(binding.editTextDate.getText().toString()) == 0){
                                saveTask(binding.editTextmovie.getText().toString(),binding.editTextmovieprice.getText().toString(),
                                        binding.spinnerMovie.getSelectedItem().toString(),binding.editTextDate.getText().toString(),binding.editAvailable.getText().toString(),Imagepath);
                            }else if(DatabaseClient.getInstance(AddMovieActivity.this).getAppDatabase().movieDao().isDataExist1( binding.spinnerMovie.getSelectedItem().toString()) == 0) {
                            saveTask(binding.editTextmovie.getText().toString(),binding.editTextmovieprice.getText().toString(),
                                    binding.spinnerMovie.getSelectedItem().toString(),binding.editTextDate.getText().toString(),binding.editAvailable.getText().toString(),Imagepath);
                        }else if( DatabaseClient.getInstance(AddMovieActivity.this).getAppDatabase().movieDao().isDataExist(binding.editTextDate.getText().toString()) == 0&& DatabaseClient.getInstance(AddMovieActivity.this).getAppDatabase().movieDao().isDataExist1( binding.spinnerMovie.getSelectedItem().toString()) == 0) {
                            saveTask(binding.editTextmovie.getText().toString(),binding.editTextmovieprice.getText().toString(),
                                    binding.spinnerMovie.getSelectedItem().toString(),binding.editTextDate.getText().toString(),binding.editAvailable.getText().toString(),Imagepath);
                        }
                            else {

                            }



                    }
                });

            }
        });
       binding.image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                       MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               startActivityForResult(pickPhoto,123);
           }
       });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editTextDate=findViewById(R.id.editTextDate);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(mCalendar.getTime());
        editTextDate.setText(selectedDate);
    }
    private void saveTask(String name,String price,String time,String date,String available,String image) {


        if (name.isEmpty()) {
            Toast.makeText(AddMovieActivity.this, "Please Add Movie Name", Toast.LENGTH_SHORT).show();
            return;
        }


        if (date.isEmpty()) {
            Toast.makeText(AddMovieActivity.this, "Please Add Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.isEmpty()) {
            Toast.makeText(AddMovieActivity.this, "Please Ticket Price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (available.isEmpty()) {
            Toast.makeText(AddMovieActivity.this, "Please Add Available Seats", Toast.LENGTH_SHORT).show();
            return;
        }


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                MovieBook movieBook = new MovieBook();
                movieBook.setMoviename(name);
                movieBook.setBookdate(date);
                movieBook.setTicketPrice(price);
                movieBook.setTiming(time);
                movieBook.setPaymentMode("");
                movieBook.setAvailable(available);
                movieBook.setImagepath(image);
                movieBook.setCashbooking("0");
                movieBook.setCollectionCash("0");
                movieBook.setOnlineBooking("0");
                movieBook.setCollectionOnline("0");

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .movieDao()
                        .insert(movieBook);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent =new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("MovieAdd","10");
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        // Match the request 'pic id with requestCode
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Uri picUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                Imagepath= String.valueOf(bitmap);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(AddMovieActivity.this,ListActivity.class);
        startActivity(intent);
        finish();
    }
}