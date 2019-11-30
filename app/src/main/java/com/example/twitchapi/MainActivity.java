package com.example.twitchapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> viewCount = new ArrayList<>();
    ArrayList<String> thumbnail = new ArrayList<>();
    RecyclerView recyclerView;
    String target = "{width}x{height}";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.refresh:
            Toast.makeText(getApplicationContext(), "Refresh List!", Toast.LENGTH_LONG).show();
            Intent intent=getIntent();
            finish();
            startActivity(intent);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.twitch.tv/helix/streams";
        final String clientID="CLIENT_ID_HERE";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try{
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("data");
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject data = array.getJSONObject(i);
                                // Get the current student (json object) data
                                userName.add(data.getString("user_name"));
                                type.add(data.getString("type"));
                                title.add(data.getString("title"));
                                viewCount.add(data.getString("viewer_count"));
                                thumbnail.add(data.getString("thumbnail_url").replace(target,"280x280"));
                            }
                            addTask();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Client-ID", clientID);

                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
    private void addTask(){
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, userName, type, title, viewCount, thumbnail);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }
}
