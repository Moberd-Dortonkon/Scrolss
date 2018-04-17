package com.example.koolguy.scroll;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListDictActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dict);
        int i=getIntent().getIntExtra("bbb",0);
        if (i==0){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.Hello);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dict);
            ListView listView = (ListView) findViewById(R.id.ListDict);
            listView.setAdapter(dictAdapter);
        }
        if (i==1){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.How);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dict);
            ListView listView = (ListView) findViewById(R.id.ListDict);
            listView.setAdapter(dictAdapter);
        }
        if (i==2){
            Resources res=getResources();
            String[] dict = res.getStringArray(R.array.Help);
            ArrayAdapter<String> dictAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dict);
            ListView listView = (ListView) findViewById(R.id.ListDict);
            listView.setAdapter(dictAdapter);
        }

    }
}
