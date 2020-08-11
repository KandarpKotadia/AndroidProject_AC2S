package com.example.json_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> c_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.accurateco2spares.com/api/categories";

        final MainActivity appContext = this;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        c_names = new ArrayList<String>();
                        ArrayList<String> c_urls = new ArrayList<>();
                        JSONArray ja = null;

                        try {
                            ja = new JSONArray(response);

                            for(int i=0;i<ja.length();i++){
                                c_names.add(ja.getJSONObject(i).get("name").toString());
                                c_urls.add("https://www.accurateco2spares.com" + ja.getJSONObject(i).get("img_path").toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_design,c_names);
                        MyListAdapter ob = new MyListAdapter(appContext, c_names, c_urls);

                        ListView listView = (ListView) findViewById(R.id.category_list);
                        listView.setAdapter(ob);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent i = new Intent(MainActivity.this, SubCategory.class);
                                //Intent i = new Intent("com.example.json_5.SUBCATEGORY");
                                i.putExtra("name",c_names.get(position));
                                i.putExtra("position",position);
                                startActivity(i);
                                //SubCategory od = new SubCategory(c_names.get(position).toString());
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Category fetch karvama maja na aavi!", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}