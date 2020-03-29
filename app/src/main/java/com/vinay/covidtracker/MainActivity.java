package com.vinay.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    // contains only the names of countries
    private ImageView mliveIcon;
    private ArrayList<String> countryNames = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mliveIcon = findViewById(R.id.liveicon);
        Animation animation =  AnimationUtils.loadAnimation(this,R.anim.blink);
        mliveIcon.startAnimation(animation);


        ArrayList<Country_item> countryList = new ArrayList<>();
        countryList.add(new Country_item(R.drawable.united_states,"Unites States","value","value","value"));
        countryList.add(new Country_item(R.drawable.india,"India","value","value","value"));
        countryList.add(new Country_item(R.drawable.australia,"Australia","value","value","value"));
        countryList.add(new Country_item(R.drawable.malaysia,"Malasiya","value","value","value"));
        countryList.add(new Country_item(R.drawable.greece,"Greece","value","value","value"));
        countryList.add(new Country_item(R.drawable.pakistan,"Pakistan","value","value","value"));
        countryList.add(new Country_item(R.drawable.united_kingdom,"United Kingdom","value","value","value"));
        countryList.add(new Country_item(R.drawable.turkey,"Turkey","value","value","value"));
        countryList.add(new Country_item(R.drawable.canada,"Canada","value","value","value"));
        countryList.add(new Country_item(R.drawable.united_arab_emirates,"United Arab\nAmirates","value","value","value"));
        countryList.add(new Country_item(R.drawable.jamaica,"Jamaica","value","value","value"));
        countryList.add(new Country_item(R.drawable.iran,"Iran","value","value","value"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CountryAdapter(countryList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
