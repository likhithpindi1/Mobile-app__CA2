package com.mobile.moviebooking.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class MovieBook implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "moviename")
    private String moviename;

    @ColumnInfo(name = "timing")
    private String timing;

    @ColumnInfo(name = "bookdate")
    private String bookdate;

    @ColumnInfo(name = "Imagepath")
    private String Imagepath;
    @ColumnInfo(name = "PaymentMode")
    private String PaymentMode;
    @ColumnInfo(name = "TicketPrice")
    private String TicketPrice;
    @ColumnInfo(name = "booking")
    private String booking;
    @ColumnInfo(name = "available")
    private String available;
    @ColumnInfo(name = "CashBooking")
    private String cashbooking;
    @ColumnInfo(name = "OnlineBooking")
    private String OnlineBooking;
 @ColumnInfo(name = "CollectionCash")
    private String CollectionCash;
    @ColumnInfo(name = "CollectionOnline")
    private String CollectionOnline;
    @ColumnInfo(name = "UserEmail")
    private String UserEmail;
    @ColumnInfo(name = "UserName")
    private String UserName;


    /*
     * Getters and Setters
     * */

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setBooking(String booking){
        this.booking=booking;
    }

    public void setAvailable(String available){
        this.available=available;
    }
    public String getAvailable(){
        return available;
    }

    public String getBooking(){
        return booking;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public void setImagepath(String Imagepath) {
        this.Imagepath = Imagepath;
    }
    public String getPaymentMode(){
        return PaymentMode;
    }
    public void setPaymentMode(String PaymentMode){
        this.PaymentMode=PaymentMode;
    }
    public String getTicketPrice(){
        return TicketPrice;
    }
    public void setTicketPrice(String TicketPrice){
        this.TicketPrice=TicketPrice;
    }

    public void setCashbooking(String cashbooking){
        this.cashbooking=cashbooking;
    }
    public String getCashbooking(){
        return cashbooking;
    }
    public void setOnlineBooking(String OnlineBooking){
        this.OnlineBooking=OnlineBooking;
    }
    public String getOnlineBooking(){
        return OnlineBooking;
    }
    public void  setCollectionCash(String collectionCash){
        this.CollectionCash=collectionCash;
    }
    public String getCollectionCash(){
        return CollectionCash;
    }
    public void setCollectionOnline(String collectionOnline){
        this.CollectionOnline=collectionOnline;
    }
    public String getCollectionOnline(){
        return CollectionOnline;
    }
}
