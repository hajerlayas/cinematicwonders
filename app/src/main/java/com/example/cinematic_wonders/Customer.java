package com.example.cinematic_wonders;

public class Customer {
   public String Name;
   public String NoSeats;
   public String Phone;
   public String MovieName;
   public String MovieDate;
   public String MovieTime;
   public String TotalPrice;

    public Customer(String name, String noSeats, String phone, String movieName, String movieDate, String movieTime, String totalPrice) {
        Name = name;
        NoSeats = noSeats;
        Phone = phone;
        MovieName = movieName;
        MovieDate = movieDate;
        MovieTime = movieTime;
        TotalPrice = totalPrice;
    }

    public Customer() {
    }


}
