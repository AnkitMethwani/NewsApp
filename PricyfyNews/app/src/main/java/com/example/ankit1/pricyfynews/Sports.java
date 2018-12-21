package com.example.ankit1.pricyfynews;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Sports extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Config config;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);


        imageView=(ImageView)findViewById(R.id.image_sports);
        textView=(TextView)findViewById(R.id.title_sports);
        recyclerView=(RecyclerView)findViewById(R.id.horizontal_recycler_view_sports);
        progressBar=(ProgressBar)findViewById(R.id.progressSports);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        getData();

    }
    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            //ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              //  progressDialog = ProgressDialog.show(Sports.this, "Fetching Data", "Please wait...",false,false);
            progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                parseJSON(s);
                parseJSONTop(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(Config.TAG_sport_URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void showData(){
        adapter = new CardAdapterSports(Config.author,Config.title,Config.description,Config.url,Config.urlToImage,Config.publishedAt, Config.bitmaps);
        recyclerView.setAdapter(adapter);
    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("articles");

            config = new Config(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                Config.author[i] = getauthor(j);
                Config.title[i] = gettitle(j);
                Config.description[i] = getdescription(j);
                Config.url[i] = geturl(j);
                Config.urlToImage[i] = geturlToImage(j);
                Config.publishedAt[i] = getpublicationAt(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetBitmapSports gb = new GetBitmapSports(this,this, Config.urlToImage);
        gb.execute();
    }
    private void parseJSONTop(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("articles");

            config = new Config(array.length());

               JSONObject j = array.getJSONObject(0);
                Config.author[0] = getauthor(j);
                Config.title[0] = gettitle(j);
                Config.description[0] = getdescription(j);
                Config.url[0] = geturl(j);
                Config.urlToImage[0] = geturlToImage(j);
                Config.publishedAt[0] = getpublicationAt(j);

             textView.setText(Config.title[0]);
            GetXMLTask task = new GetXMLTask();
            // Execute the task
            task.execute(new String[] { Config.urlToImage[0] });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getauthor(JSONObject j){
        String name = null;
        try {
            name = j.getString("author");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String gettitle(JSONObject j){
        String name = null;
        try {
            name = j.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getdescription(JSONObject j){
        String name = null;
        try {
            name = j.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String geturl(JSONObject j){
        String name = null;
        try {
            name = j.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String geturlToImage(JSONObject j){
        String name = null;
        try {
            name = j.getString("urlToImage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getpublicationAt(JSONObject j){
        String name = null;
        try {
            name = j.getString("publicationAt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
