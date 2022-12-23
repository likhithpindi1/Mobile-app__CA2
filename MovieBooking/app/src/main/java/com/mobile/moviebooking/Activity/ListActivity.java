package com.mobile.moviebooking.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.moviebooking.Adapter.MovieAdapter;
import com.mobile.moviebooking.R;
import com.mobile.moviebooking.databinding.ActivityListBinding;
import com.mobile.moviebooking.databinding.ActivityMainBinding;
import com.mobile.moviebooking.db.DatabaseClient;
import com.mobile.moviebooking.db.MovieBook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    TextView editTextDate;
    ArrayList<String> online=new ArrayList<>();
    ArrayList<String>cash=new ArrayList<>();
    String show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);


        binding.floatingButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });
        ActivityCompat.requestPermissions(ListActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        binding.recyclerviewMovie.setLayoutManager(new LinearLayoutManager(ListActivity.this,RecyclerView.VERTICAL,false));
        binding.recyclerviewMovie.setHasFixedSize(true);
        binding.editMovieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle("Movie List");
                binding.editTextDate.setEnabled(true);
                binding.editMovieList.setTextColor(Color.RED);
                binding.editTextbooking.setTextColor(Color.BLACK);
                String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
                binding.editTextDate.setText(currentDate);
                getTasks(binding.recyclerviewMovie,"0",binding.editTextDate.getText().toString());
            }
        });
        binding.editTextbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle("Booking List");
                binding.editTextDate.setEnabled(false);
                binding.editMovieList.setTextColor(Color.BLACK);
                binding.editTextbooking.setTextColor(Color.RED);
                binding.editTextDate.setText("");
                getTasks(binding.recyclerviewMovie,"1","");
            }
        });
        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.mobile.moviebooking.PickerDialog.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.mobile.moviebooking.PickerDialog.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        binding.editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTasks(binding.recyclerviewMovie,"0",binding.editTextDate.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Intent intent =getIntent();
        show=intent.getStringExtra("MovieAdd");
        if(show.equals("10")){
            binding.editMovieList.setTextColor(Color.RED);
            binding.editTextbooking.setTextColor(Color.BLACK);
            String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
            binding.editTextDate.setText(currentDate);
            getTasks(binding.recyclerviewMovie,"0",binding.editTextDate.getText().toString());
            getSupportActionBar().setTitle("Movie List");
            binding.editTextDate.setEnabled(true);
        }else {
            binding.editMovieList.setTextColor(Color.BLACK);
            binding.editTextbooking.setTextColor(Color.RED);
            binding.editTextDate.setText("");
            getTasks(binding.recyclerviewMovie,"1","");
            binding.editTextDate.setEnabled(false);
            getSupportActionBar().setTitle("Booking List");
        }

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
    private void getTasks(RecyclerView recyclerView,String value,String date) {
        class GetTasks extends AsyncTask<Void, Void, List<MovieBook>> {
            List<MovieBook> movieBooks;
            @Override
            protected List<MovieBook> doInBackground(Void... voids) {
                if(value.equals("1")) {
                    movieBooks = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .movieDao()
                            .getBook("Book");
                }else if(value.equals("0")){
                    movieBooks = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .movieDao()
                            .getAll(date);
                }
                return movieBooks;
            }

            @Override
            protected void onPostExecute(List<MovieBook> movieBooks) {
                super.onPostExecute(movieBooks);
    if(value.equals("1")) {
        MovieAdapter adapter = new MovieAdapter(ListActivity.this, movieBooks, "1");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }else if(value.equals("0")) {
        MovieAdapter adapter = new MovieAdapter(ListActivity.this, movieBooks, "10");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(ListActivity.this,BookingActivity.class);
        startActivity(intent);
        finish();
    }
}