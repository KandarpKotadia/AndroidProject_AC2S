package com.example.json_5;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {

    int pdId;
    String pdName, pdType, pdSubCat, pdCat,pdImage;
    TextView tvName,tvType,tvSubcat,tvCat;
    ImageView pdImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        pdId = getIntent().getIntExtra("productId", 0);

        RequestQueue queue = Volley.newRequestQueue(this);
        String pd = "https://www.accurateco2spares.com/api/getProduct?id=" + pdId;

        StringRequest srPd = new StringRequest(Request.Method.GET, pd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject asPd;
                JSONArray jsPd;
                try {
//                    Toast.makeText(getApplicationContext(),"Got product : " + response.toString(),Toast.LENGTH_LONG).show();
                    asPd = new JSONObject(response);
//                    jsPd = new JSONArray(response);
                    pdName = asPd.get("productName").toString();
                    pdType = asPd.get("productType").toString();
                    pdSubCat = asPd.get("productSubcategory").toString();
                    pdCat = asPd.get("productCategory").toString();
                    JSONArray temparray = asPd.getJSONArray("productImages");
                    pdImage = "https://www.accurateco2spares.com/" + temparray.getJSONObject(0).get("imagePath").toString();

                    Toast.makeText(getApplicationContext(),"Name : " + pdName + " | type : " + pdType,Toast.LENGTH_LONG).show();

                    tvName = findViewById(R.id.tvName);
                    tvName.setText("Product Name: "+pdName);

                    tvType = findViewById(R.id.tvType);
                    tvType.setText("Product Type: "+pdType);

                    tvSubcat = findViewById(R.id.tvSubCat);
                    tvSubcat.setText("Subcategory: "+pdSubCat);

                    tvCat = findViewById(R.id.tvCat);
                    tvCat.setText("Category: "+pdCat);

                    pdImg = findViewById(R.id.image_view);
                    Picasso.get().load(pdImage).into(pdImg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"no maja aaivi",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(srPd);
    }
}
