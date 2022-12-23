package com.mobile.moviebooking.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.moviebooking.R;
import com.mobile.moviebooking.databinding.ActivityBookingBinding;
import com.mobile.moviebooking.databinding.ActivityMainBinding;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBookingBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_booking);
        getSupportActionBar().setTitle("Dashboard");
        binding.btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(BookingActivity.this,MainActivity.class);
                intent.putExtra("MovieAdd","0");
                startActivity(intent);
            }
        });
        binding.btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialogView = inflater.inflate(R.layout.lyt_login, null);
                builder.setView(dialogView);
                AlertDialog alertDialog=builder.create();
                alertDialog.setCancelable(false);
                EditText etEmail=dialogView.findViewById(R.id.etEmail);
                EditText etPass=dialogView.findViewById(R.id.etPass);
                Button btnLogin=dialogView.findViewById(R.id.btnLogin);
                Button btnBack=dialogView.findViewById(R.id.btnBack);
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etEmail.getText().toString().equals("admin@gmail.com")&&etPass.getText().toString().equals("123456")){
                            Intent intent =new Intent(BookingActivity.this,ListActivity.class);
                            intent.putExtra("MovieAdd","0");
                            startActivity(intent);
                        }else {
                            Toast.makeText(BookingActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}