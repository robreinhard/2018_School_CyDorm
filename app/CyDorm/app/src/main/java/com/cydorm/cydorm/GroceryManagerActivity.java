package com.cydorm.cydorm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroceryManagerActivity extends AppCompatActivity {

    private ListView mGroceryList;
    private EditText mItemEdit;
    private Button mAddButton;
    private GroceryListNetwork listNetwork;

    private ArrayAdapter<GroceryItem> mAdapter;

    protected int itemInd;
    private static String LOG_TAG = "GroceryMan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        mGroceryList = (ListView) findViewById(R.id.grocery_listView);
        mItemEdit = (EditText) findViewById(R.id.item_editText);
        mAddButton = (Button) findViewById(R.id.add_button);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        mGroceryList.setAdapter(mAdapter);
        this.listNetwork = new GroceryListNetwork(this);


        this.itemInd = -1;

        final ArrayList<GroceryItem> list = new ArrayList<>();
        this.listNetwork.getGroceryList(new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.print(response.toString());
                Log.d(LOG_TAG, "Now we are in the response");
                JSONObject curObj;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        curObj = response.getJSONObject(i);
                        list.add(new GroceryItem(curObj.getString(
                                "groceryItem")));
                        Log.d(LOG_TAG,
                                "Here is is " + curObj.getString(
                                        "groceryItem"));
                    } catch (Exception e) {
                        //Not going to do anything
                        Log.d(LOG_TAG, "You messed up parsing");
                    }
                }

                for(GroceryItem i : list) {
                    mAdapter.add(i);
                }
            }
        });


        //Add button click
        mAddButton.setOnClickListener(new AddButtonClickedListener());

        // Clicking Item
        mGroceryList.setOnItemClickListener(new GroceryItemClickedListener());
    }

    private class AddButtonClickedListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                String item;
                if(itemInd >= 0) {
                    item = mItemEdit.getText().toString();

                    mAdapter.getItem(itemInd).setItem(item);

                    //HERE insert update code
                    listNetwork.updateListItem(mAdapter.getItem(itemInd));

                    itemInd = -1;
                    mAdapter.notifyDataSetChanged();
                    mItemEdit.setText("");

                } else {
                    item = mItemEdit.getText().toString();
                    GroceryItem gi = new GroceryItem(item);
                    mAdapter.add(gi);

                    // HERE INSERT UPDATE CODE
                    listNetwork.addListItem(gi);

                    mAdapter.notifyDataSetChanged();
                    mItemEdit.setText("");
                }

            }
    }

    private class GroceryItemClickedListener implements AdapterView.OnItemClickListener {
             @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemInd = i;
                mItemEdit.setText(adapterView.getItemAtPosition(i).toString());
            }
    }

}
