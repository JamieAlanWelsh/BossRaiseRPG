package com.example.bossraise;

public class Inventory {

    boolean coffee = false;
    boolean photos = false;


    public boolean checkCoffee()  { return coffee; }
    public boolean checkPhotos()  { return photos;  }


    public void setCoffee(int num)  {
        switch(num)  {
            case 1:
                coffee = true;
                break;
            case 0:
                coffee = false;
                break;
        }
    }

    public void setPhotos(int num)  {
        switch(num)  {
            case 1:
                photos = true;
                break;
            case 0:
                photos = false;
                break;
        }
    }
}
