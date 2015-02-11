package com.yoabhi.adobeworld;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Main extends ListActivity {
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://adobe.0x10.info/api/products?type=json";
 
    // JSON Node names
    public static final String TAG_NAME = "name";
	public static final String TAG_TYPE = "type";
	public static final String TAG_URL = "url";
	public static final String TAG_IMAGE = "image";
	public static final String TAG_RATING = "rating";
	public static final String TAG_LAST_UPDATED = "last updated";
	public static final String TAG_IN_APP = "inapp-purchase";
	public static final String TAG_DESCRIPTION = "description";
    // contacts JSONArray
    JSONArray name_array = null;
    JSONObject jsonObj;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> nameList;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        nameList = new ArrayList<HashMap<String, String>>();
        
        ListView lv = getListView();
        new LoadProducts().execute();
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
                    HashMap<String, String> names = nameList.get(position);
                   
                    Intent intent = new Intent(getApplicationContext(), Desc.class);
                    intent.putExtra("block", names);
                    startActivity(intent);
                   
            }

			
    });
}
    
    
    private class LoadProducts extends AsyncTask<Void, Void, Void> {
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                  //  JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    name_array = new JSONArray(jsonStr);
 
                    // looping through All Contacts
                    for (int i = 0; i < name_array.length(); i++) {
                    	JSONObject c = name_array.getJSONObject(i);
                        String name = c.getString(TAG_NAME);
                        String app_url = c.getString(TAG_URL);
                        String image_url = c.getString(TAG_IMAGE);
                        String rating = c.getString(TAG_RATING);
                        String inapp = c.getString(TAG_IN_APP);
                        String description = c.getString(TAG_DESCRIPTION);
                        String last_updated = c.getString(TAG_LAST_UPDATED);
                        String type=c.getString(TAG_TYPE);

                        HashMap<String, String> names = new HashMap<String, String>();
                        names.put(TAG_NAME, name);
                        names.put(TAG_URL, app_url);
                        names.put(TAG_IMAGE, image_url);
                        names.put(TAG_RATING, rating);
                        names.put(TAG_IN_APP, inapp);
                        names.put(TAG_TYPE, type);
                        names.put(TAG_DESCRIPTION, description);
                        names.put(TAG_LAST_UPDATED, last_updated);

                        nameList.add(names);
                       
                        
                        
                }

        } catch (JSONException jse) {
                jse.printStackTrace();
        }
                
} 
           
            else {
        Log.d("parse_error", "couldn't get any data from url");
}
           
            	
           
            return null;
}
        public Drawable LoadImageFromWebOperations(String url) {
        	  try {
        	    InputStream is = (InputStream) new URL(url).getContent();
        	    Drawable d = Drawable.createFromStream(is, "TAG_IMAGE");
        	    return d;
        	} catch (Exception e) {
        	    return null;
        	 }
        	
        
        
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Main.this, nameList,
                    R.layout.all_products, new String[] { TAG_IMAGE, TAG_NAME, TAG_IN_APP,TAG_LAST_UPDATED
                             }, new int[] {R.id.icon, R.id.product_name,
                            R.id.product_availability,R.id.LastUpdate });
            setListAdapter(adapter);
         
        }
 
    }

}




