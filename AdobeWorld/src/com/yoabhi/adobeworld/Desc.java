package com.yoabhi.adobeworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Desc extends Activity {
	
	{ new AsyncTask<Void, Void, Void>() {                  
	        protected Void doInBackground(Void... params) {
	            try {
	                InputStream in = new URL(names.get(TAG_URL)).openStream();
	                bmp = BitmapFactory.decodeStream(in);
	            } catch (Exception e) {
	               // log error
	            }
	            return null;
	        }

	        protected void onPostExecute(Void result) {
	            if (bmp != null)
	                iv.setImageBitmap(bmp);
	        }

	   }.execute();
	}
	
	public static final String TAG_NAME = "name";
	public static final String TAG_TYPE = "type";
	public static final String TAG_URL = "url";
	public static final String TAG_IMAGE = "image";
	public static final String TAG_RATING = "rating";
	public static final String TAG_LAST_UPDATED = "last updated";
	public static final String TAG_IN_APP = "inapp-purchase";
	public static final String TAG_DESCRIPTION = "description";
	public static  String image_url;
	public static HashMap<String,String> names;
	public static ImageView iv;
	private Bitmap bmp;
	
	TextView txt_main,rating;
	Button appstore,share,sms,back;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desc_activity);
        
        appstore=(Button) findViewById(R.id.appstore);
        share=(Button) findViewById(R.id.share); 
      
        back=(Button) findViewById(R.id.back);
       rating=(TextView) findViewById(R.id.ratingTxt);
        txt_main=(TextView) findViewById(R.id.txt_main);
        txt_main.setVisibility(View.GONE);
       RatingBar rating_bar=(RatingBar) findViewById(R.id.ratingBar1);
       
      
        
        appstore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Uri uri = Uri.parse("http://play.google.com");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
        
 back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				finish();
			}
		});
        
 share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent share = new Intent(android.content.Intent.ACTION_SEND);
			        share.setType("text/plain");
			        share.putExtra(Intent.EXTRA_SUBJECT, "Adobe Apps");
			        share.putExtra(Intent.EXTRA_TEXT,names.get(TAG_URL));
			        startActivity(Intent.createChooser(share, "Adobe Android Apps"));

			}
		});
        
               
        Bundle extras = getIntent().getExtras();
    	
    	if(extras == null){
    		
    		return;
    	}
    	
    	names = (HashMap<String, String>) extras.get("block");
    	
    	ImageView iv=(ImageView) findViewById(R.id.proImg);
        TextView proName=(TextView) findViewById(R.id.proName);
        //TextView proType=(TextView) findViewById(R.id.proType);
        TextView proTypename=(TextView) findViewById(R.id.proTypename);
       // TextView proInapp_purchase=(TextView) findViewById(R.id.proInapp_purchase);
        TextView proinappYesNo=(TextView) findViewById(R.id.proInappYesNo);
        TextView proLast=(TextView) findViewById(R.id.LastUpdate);
        
        
        txt_main=(TextView) findViewById(R.id.txt_main);
      
        
        proName.setText(names.get(TAG_NAME));
        proTypename.setText(names.get(TAG_TYPE));
        proinappYesNo.setText(names.get(TAG_IN_APP));
        proLast.setText(names.get(TAG_LAST_UPDATED));
        txt_main.setText(names.get(TAG_DESCRIPTION));
       
        proTypename.setText(names.get(TAG_TYPE));
       
      
        
        rating.setText(names.get(TAG_RATING));
       rating_bar.setEnabled(false);
        rating_bar.setMax(5); // I assume 5 is your max rating
        rating_bar.setRating(Float.parseFloat(names.get(TAG_RATING)));
	
    Picasso.with(getBaseContext()).load("https://lh3.ggpht.com//Ogc0XYIjLMsPl97PErdrRtidVmfBrRzv5wb7NQYEOWxzg_j2txnxrOrGFh-E21e_kkw=w300-rw").into(iv);
       
	}	
	public void toggle_contents(View v){
	      txt_main.setVisibility( txt_main.isShown()
	                          ? View.GONE
	                          : View.VISIBLE );
	      
	      }
	
	
}


