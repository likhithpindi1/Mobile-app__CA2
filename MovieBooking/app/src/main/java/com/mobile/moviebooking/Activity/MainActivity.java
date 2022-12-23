package com.mobile.moviebooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.moviebooking.Adapter.MovieAdapter;
import com.mobile.moviebooking.R;
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

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView editTextDate,Moviecash,Movieonline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportActionBar().setTitle("Movie List");

        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.mobile.moviebooking.PickerDialog.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.mobile.moviebooking.PickerDialog.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        Moviecash=findViewById(R.id.Moviecash);
        Movieonline=findViewById(R.id.Movieonline);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        binding.editTextDate.setText(currentDate);

        binding.recyclerviewMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
        binding.recyclerviewMovie.setHasFixedSize(true);
        getTasks(binding.recyclerviewMovie,binding.editTextDate.getText().toString());
        binding.editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTasks(binding.recyclerviewMovie,binding.editTextDate.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    private void getTasks(RecyclerView recyclerView,String date) {
        class GetTasks extends AsyncTask<Void, Void, List<MovieBook>> {

            @Override
            protected List<MovieBook> doInBackground(Void... voids) {
                List<MovieBook> movieBooks = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .movieDao()
                        .getAll(date);


                return movieBooks;
            }

            @Override
            protected void onPostExecute(List<MovieBook> movieBooks) {
                super.onPostExecute(movieBooks);
                MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieBooks,"0");
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
        Intent intent = new Intent(MainActivity.this, BookingActivity.class);
        startActivity(intent);
        finish();
    }

}