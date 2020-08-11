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

public class Products extends AppCompatActivity {

    int categoryID,subCategoryId,ProductTypeId;
    int productsId[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryID = getIntent().getIntExtra("categoryId",0);
        subCategoryId = getIntent().getIntExtra("subCategoryId",0);
        ProductTypeId = getIntent().getIntExtra("productTypeId",0);
        categoryID++;
        subCategoryId++;
        ProductTypeId++;

        RequestQueue scQueue = Volley.newRequestQueue(this);
        String productsUrl = "https://www.accurateco2spares.com/api/product/category/" + categoryID + "/subcategory/"
                + subCategoryId + "/type/" + ProductTypeId;

        Toast.makeText(getApplicationContext(), productsUrl, Toast.LENGTH_LONG).show();

        StringRequest srProducts = new StringRequest(Request.Method.GET, productsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> products = new ArrayList<>();
                ArrayList<String> productsUrl = new ArrayList<>();
                JSONArray jaProducts;
                Toast.makeText(getApplicationContext(), "Products fetched", Toast.LENGTH_LONG).show();

                try {
                    jaProducts = new JSONArray(response);
                    productsId = new int[jaProducts.length()];
                    for(int i=0;i<jaProducts.length();i++){
                        products.add(jaProducts.getJSONObject(i).get("name").toString());
                        productsUrl.add("https://www.accurateco2spares.com/"+jaProducts.getJSONObject(i).get("image_path").toString());
                        productsId[i] = (int)(jaProducts.getJSONObject(i).get("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.sc_list_design,products);
                MyListAdapter productObj = new MyListAdapter(Products.this, products, productsUrl);

                ListView listView = (ListView) findViewById(R.id.category_list);
                listView.setAdapter(productObj);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(Products.this,ProductDetails.class);
                        i.putExtra("productId",productsId[position]);
                        startActivity(i);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Products ma no maja aaivi",Toast.LENGTH_LONG).show();
            }
        });
        scQueue.add(srProducts);
    }
}
