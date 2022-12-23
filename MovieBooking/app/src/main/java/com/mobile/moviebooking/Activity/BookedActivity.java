package com.mobile.moviebooking.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.moviebooking.R;
import com.mobile.moviebooking.databinding.ActivityBookedBinding;
import com.mobile.moviebooking.db.DatabaseClient;

public class BookedActivity extends AppCompatActivity {
   int i;
    ActivityBookedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_booked);
       getSupportActionBar().setTitle("Booked Ticket");
        Intent intent =getIntent();
       String name = intent.getStringExtra("Moviename");
       String seats=  intent.getStringExtra("Availableseats");
       String TicketPrice=  intent.getStringExtra("TicketPrice");
       int id=  intent.getIntExtra("MovieId",0);
       int CollectionCash= Integer.parseInt(intent.getStringExtra("MovieCollectionCash"));
       int CollectionOnline= Integer.parseInt(intent.getStringExtra("MovieCollectionOnline"));
       int Online= Integer.parseInt(intent.getStringExtra("MovieOnline"));
       int Cash= Integer.parseInt(intent.getStringExtra("MovieCash"));
       binding.ticketPrice.setText(TicketPrice);
       binding.Moviename.setText(name);
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.seatsEt.getText().toString().isEmpty()){
                    i=1;
                }
                if (i < Integer.parseInt(seats)) {
                    i++;
                } else {
                    Toast.makeText(BookedActivity.this, "No Seats Available More than that", Toast.LENGTH_SHORT).show();
                }
                binding.seatsEt.setText(String.valueOf(i));
            }
        });
        binding.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.seatsEt.getText().toString().isEmpty()){
                    i=1;
                }
                if(i<=1)
                {
                    binding.seatsEt.setText(String.valueOf("1"));
                    i=0;

                }
                else {
                    i--;
                    binding.seatsEt.setText(String.valueOf(i));
                }
            }
        });
      binding.btnBook.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(binding.etEmail.getText().toString().isEmpty()){
                  binding.etEmail.setError("Enter Email");
                  return;
              }
              if(binding.etName.getText().toString().isEmpty()){
                  binding.etName.setError("Enter Name");
                  return;
              }
              binding.etEmail.setError(null);
              binding.etName.setError(null);
              int s=Integer.parseInt(seats)-Integer.parseInt(binding.seatsEt.getText().toString());

              int price=Integer.parseInt(binding.seatsEt.getText().toString())*Integer.parseInt(binding.ticketPrice.getText().toString());

              AlertDialog.Builder builder = new AlertDialog.Builder(BookedActivity.this);

              LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              View dialogView = inflater.inflate(R.layout.lyt_book, null);
              builder.setView(dialogView);
              AlertDialog alertDialog=builder.create();
              Button btn_pay=dialogView.findViewById(R.id.btn_pay);
              Button btn_cash=dialogView.findViewById(R.id.btn_cash);
              btn_pay.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      int onlinePrice=price+CollectionOnline;
                      int On=Online+ Integer.parseInt(binding.seatsEt.getText().toString());
                      AsyncTask.execute(new Runnable() {
                          @Override
                          public void run() {
                              DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                      .movieDao()
                                      .updatePayment("Online","Book",String.valueOf(s),
                                              binding.ticketPrice.getText().toString(),String.valueOf(Cash),
                                             String.valueOf(On),String.valueOf(CollectionCash),String.valueOf(onlinePrice),binding.etEmail.getText().toString(),binding.etName.getText().toString()

                                      ,id);
                          }
                      });
                      screenShot();
                      alertDialog.dismiss();
                  }
              });
              btn_cash.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      int cashPrice=price+CollectionCash;
                      int Cas=Cash+ Integer.parseInt(binding.seatsEt.getText().toString());
                      AsyncTask.execute(new Runnable() {
                          @Override
                          public void run() {
                              DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                      .movieDao()
                                      .updatePayment("Cash","Book",String.valueOf(s),
                                              binding.ticketPrice.getText().toString(),String.valueOf(Cas),
                                              String.valueOf(Online),String.valueOf(cashPrice),String.valueOf(CollectionOnline),binding.etEmail.getText().toString(),binding.etName.getText().toString()

                                              ,id);
                          }
                      });
                      screenShot();
                      alertDialog.dismiss();
                  }
              });
              alertDialog.show();

          }
      });
    }
    public void screenShot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookedActivity.this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.lyt_screenshot, null);
        builder.setView(dialogView);
        AlertDialog alertDialog=builder.create();
        alertDialog.setCancelable(false);
        TextView txt_name=dialogView.findViewById(R.id.txt_name);
        TextView txt_Email=dialogView.findViewById(R.id.txt_Email);
        TextView txt_Price=dialogView.findViewById(R.id.txt_Price);
        TextView txt_Seats=dialogView.findViewById(R.id.txt_Seats);
        Button btn_ok=dialogView.findViewById(R.id.btn_ok);
        txt_name.setText("Name : "+binding.etName.getText().toString());
        txt_Email.setText("Email : "+binding.etEmail.getText().toString());
        int price=Integer.parseInt(binding.ticketPrice.getText().toString())*Integer.parseInt(binding.seatsEt.getText().toString());
        txt_Price.setText("Price : "+price+" ");
        txt_Seats.setText("Seats : "+binding.seatsEt.getText().toString());
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(BookedActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(BookedActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}