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

public class SubSubCategory extends AppCompatActivity {

    String sc_category;
    int categoryId;
    int subCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sc_category = getIntent().getStringExtra("name1");
        subCategoryId = getIntent().getIntExtra("position",0);
        categoryId = getIntent().getIntExtra("categoryId",0);

        RequestQueue scQueue = Volley.newRequestQueue(this);
        String sccUrl = "https://www.accurateco2spares.com/api/productTypes";

        StringRequest sc_sr = new StringRequest(Request.Method.GET, sccUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Product type madi gai!", Toast.LENGTH_LONG).show();
                ArrayList<String> sc_sc_category = new ArrayList<>();
                JSONArray ja_sc_sc;

                try {
                    ja_sc_sc = new JSONArray(response);
                    for(int i=0;i<ja_sc_sc.length();i++) {
                        if(sc_category.equals(ja_sc_sc.getJSONObject(i).get("subcategory").toString())) {
                            sc_sc_category.add(ja_sc_sc.getJSONObject(i).get("name").toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.sc_list_design,sc_sc_category);
                ListView listView = (ListView) findViewById(R.id.category_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i= new Intent(SubSubCategory.this,Products.class);
                        i.putExtra("categoryId",categoryId);
                        i.putExtra("subCategoryId",subCategoryId);
                        i.putExtra("productTypeId",position);
                        startActivity(i);
                    }
                });
            }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Product Type fetch karvama maja na aavi!", Toast.LENGTH_LONG).show();
                }
        });
        scQueue.add(sc_sr);
    }
}
