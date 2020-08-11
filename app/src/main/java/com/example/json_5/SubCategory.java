package com.example.json_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SubCategory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String category;
    ArrayList<String> sc_names;
    int c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = getIntent().getStringExtra("name");
        c_id = getIntent().getIntExtra("position",0);

        Toast.makeText(getApplicationContext(),"Got category : "+category,Toast.LENGTH_LONG).show();

        RequestQueue scQueue = Volley.newRequestQueue(this);
        String scUrl = "https://www.accurateco2spares.com/api/subcategories";

        StringRequest sr = new StringRequest(Request.Method.GET, scUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sc_names = new ArrayList<>();
                final ArrayList<String> sc_category = new ArrayList<>();
                JSONArray ja_sc;

                    try {
                        ja_sc = new JSONArray(response);
                        Toast.makeText(getApplicationContext(),"Sub cateogry madi gai!",Toast.LENGTH_LONG).show();
                        for(int i=0;i<ja_sc.length();i++) {
                            if(category.equals(ja_sc.getJSONObject(i).get("category").toString())) {
                                //sc_names.add(ja_sc.getJSONObject(i).get("category").toString());
                                sc_category.add(ja_sc.getJSONObject(i).get("name").toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Subcategory ma json ma maja na aavi!",Toast.LENGTH_LONG).show();
                    }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.sc_list_design,sc_category);
                ListView listView = (ListView) findViewById(R.id.category_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(SubCategory.this, SubSubCategory.class);
                        i.putExtra("name1",sc_category.get(position));
                        i.putExtra("position",position);
                        i.putExtra("categoryId",c_id);
                        startActivity(i);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Subcategory fetch karvama maja na aavi!", Toast.LENGTH_LONG).show();
            }
        });
        scQueue.add(sr);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
