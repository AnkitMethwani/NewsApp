package com.example.ankit1.pricyfynews;

/**
 * Created by Ankit1 on 12-10-2017.
 */

import android.graphics.Bitmap;

/**
 * Created by Belal on 10/29/2015.
 */
public class Config {

    public static String[] author, title, description, url, urlToImage, publishedAt;

    public static Bitmap[] bitmaps;

    public static final String GET_URL = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=cc4a9a3c46854af6b1c2b035cdf2a201";
    public static final String TAG_sport_URL = "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=cc4a9a3c46854af6b1c2b035cdf2a201";
  //  public static final String TAG_IMAGE_NAME = "name";
  //  public static final String TAG_JSON_ARRAY="result";

    public Config(int i){
        title = new String[i];
        description = new String[i];
        author = new String[i];
        url = new String[i];
        urlToImage = new String[i];
        publishedAt = new String[i];
        bitmaps = new Bitmap[i];
    }
}
