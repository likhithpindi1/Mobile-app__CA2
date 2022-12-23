package com.mobile.moviebooking.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MovieDao {
    @Query("SELECT * FROM moviebook WHERE bookdate=:bookdate")
    List<MovieBook> getAll(String bookdate);
    @Query("SELECT * FROM moviebook WHERE booking=:booking")
    List<MovieBook> getBook(String booking);
    @Query("SELECT * FROM moviebook")
    List<MovieBook> getAll();
    @Query("SELECT * FROM moviebook WHERE bookdate=:bookdate")
    int isDataExist(String bookdate);
    @Query("SELECT * FROM moviebook WHERE timing=:timing")
    int isDataExist1(String timing);
    @Query("UPDATE moviebook SET PaymentMode =:PaymentMode,booking=:booking,TicketPrice=:TicketPrice," +
            "available=:available,cashbooking=:cashbooking,OnlineBooking=:OnlineBooking,CollectionCash=:CollectionCash,CollectionOnline=:CollectionOnline," +
            "UserEmail=:UserEmail,UserName=:UserName  WHERE id=:id")
    void  updatePayment(String PaymentMode,String booking,String available,
                        String TicketPrice,String cashbooking,String OnlineBooking,String CollectionCash,
                        String CollectionOnline,String UserEmail,String UserName,int id);

    @Insert
    void insert(MovieBook movie);

    @Delete
    void delete(MovieBook movie);

    @Update
    void update(MovieBook movie);


}
