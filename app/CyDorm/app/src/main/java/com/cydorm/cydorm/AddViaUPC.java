package com.cydorm.cydorm;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;


import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.zxing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.*;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import com.cydorm.cydorm.GroceryManagerActivity;

public class AddViaUPC extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    HashMap<String, String[]> previousScans = new HashMap<String, String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("TAG", rawResult.getText()); // Prints scan results
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(scanItem(rawResult.getText()));
        AlertDialog alert1 = builder.create();
        alert1.show();

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    private String scanItem(String barcode){ //Don't want to waste API calls
        String item;
        String price;
        /**if (previousScans.containsKey(barcode)){

            item = previousScans.get(barcode)[0];
            price = previousScans.get(barcode)[1];
        }*/
        String key = "&key=ply4u1uw4of4htn9gfnsgpnxe1k4hg";
        String url = "https://api.barcodelookup.com/v2/products?barcode=" + barcode + key;


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonData = responses.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONArray Jarray = Jobject.getJSONArray("products");

            item = Jarray.getJSONObject(0).get("product_name").toString();
            price = Jarray.getJSONObject(0).getJSONArray("stores").getJSONObject(0).get("store_price").toString();

            String[] itemArray = new String[]{item, price};
            //previousScans.put(barcode, itemArray);

            process(itemArray);
            return "Adding " + item + " for $" + price;
        }
        catch (Exception E){
            System.out.println(E);
            return(E.toString());
        }


    }

    private void process(String[] itemArray){
        Intent i = new Intent(AddViaUPC.this,
                AddGroceryActivity.class);
        i.putExtra("price", itemArray[1]);
        i.putExtra("name", itemArray[0]);
        i.putExtra("id", "");
        startActivity(i);
    }

}