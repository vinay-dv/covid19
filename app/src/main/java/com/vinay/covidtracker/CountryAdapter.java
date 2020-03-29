package com.vinay.covidtracker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{

    private ArrayList<Country_item> mCountryList;
    public static class CountryViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mCountryName;
        public TextView mCases;
        public TextView mDeaths;
        public TextView mRecovered;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            // finding views in country item layout file (country_item)
            mImageView = itemView.findViewById(R.id.countryflagimageView);
            mCountryName = itemView.findViewById(R.id.countryName);
            mCases = itemView.findViewById(R.id.displayCase);
            mDeaths = itemView.findViewById(R.id.displayDeaths);
            mRecovered = itemView.findViewById(R.id.displayCaseRecovered);
        }
    }

    public CountryAdapter(ArrayList<Country_item> countyList)
    {
        mCountryList = countyList;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);
        CountryViewHolder countryviewHolder = new CountryViewHolder(v);
        return countryviewHolder;
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {

        Log.e("called","on bind view");
        Country_item country = mCountryList.get(position);
        holder.mImageView.setImageResource(country.getImageResource());
        holder.mCountryName.setText(country.getCountryName());
        holder.mCases.setText(country.getCases());
        holder.mDeaths.setText(country.getDeaths());
        holder.mRecovered.setText(country.getRecovered());

    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }
}

