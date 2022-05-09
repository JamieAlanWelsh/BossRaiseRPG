package com.example.bossraise;

public class CSVFormatError extends IllegalArgumentException {

    public CSVFormatError(String message)  {
        super(message);
    }
}
