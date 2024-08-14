package com.gc.news_subscription_bot.exception;

public class InvalidCategoryException extends RuntimeException{
    public InvalidCategoryException(String mssge){
        super(mssge);
    }
}
