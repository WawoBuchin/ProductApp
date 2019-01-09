package com.example.eliabrian.productapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> productList;
    private static String url_all_products = "http://192.168.0.103/w12/read_all_products.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";

    JSONArray products = null;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        listView = (ListView)findViewById(R.id.list);
        productList = new ArrayList<HashMap<String, String>>();

        new LoadAllProducts().execute();

        //TODO: Belum ada getListView()
       /* ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pid = ((TextView)view.findViewById(R.id.pid)).getText().toString();
                Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
                i.putExtra(TAG_PID, pid);
                startActivity(i, 100);
            }
        });*/
    }

    private class LoadAllProducts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Log.d("DEBUG", "ON PRE-EXECUTE");
            pDialog = new ProgressDialog(AllProductActivity.this);
            pDialog.setMessage("Loading products, please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("DEBUG", "Do In Background");
            List<Pair<String, String>> args = new ArrayList<Pair<String, String>>();
            JSONObject jsonObject = null;

            try{
                Log.d("DEBUG", "Try connecting to server");
                jsonObject = jsonParser.getJsonObject(url_all_products, "POST", args);
                Log.d("DEBUG", url_all_products);
            }catch (Exception e){
                Log.d("DEBUG", e.getLocalizedMessage());
                Log.d("DEBUG", e.getMessage());
            }
/*
            Log.d("All Products : ", jsonObject.toString());*/

            try {
                int success = jsonObject.getInt(TAG_SUCCESS);
                if (success == 1){
                    products = jsonObject.getJSONArray(TAG_PRODUCTS);

                    for(int i=0; i<products.length(); i++){
                        JSONObject c = products.getJSONObject(i);
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        productList.add(map);
                    }
                }else{
                    Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s){
            Log.d("DEBUG", "On Post Execute");
            pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(AllProductActivity.this, productList, R.layout.list_item,new String []{TAG_PID, TAG_NAME}, new int[]{R.id.pid, R.id.pname});
            //TODO: belum ada seeListAdapter
            //seeListAdapter(adapter);
            listView.setAdapter(adapter);
        }
    }
}
