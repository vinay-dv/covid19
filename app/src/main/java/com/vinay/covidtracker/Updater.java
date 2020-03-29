package com.vinay.covidtracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Updater extends AppCompatActivity {
    public static final List<String> country_list =
            Collections.unmodifiableList(Arrays.asList("Afghanistan","Albania","Algeria","AmericanSamoa","Andorra","Angola","Anguilla","Antarctica","AntiguaandBarbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","BosniaandHerzegovina","Botswana","BouvetIsland","Brazil","BritishIndianOceanTerritory","BritishVirginIslands","Brunei","Bulgaria","BurkinaFaso","Burundi","Cambodia","Cameroon","Canada","CapeVerde","CaymanIslands","CentralAfricanRepublic","Chad","Chile","China","ChristmasIsland","Cocos(Keeling)Islands","Colombia","Comoros","Congo","CookIslands","CostaRica","Coted\'Ivoire","Croatia","Cuba","Cyprus","CzechRepublic","DemocraticRepublicoftheCongo","Denmark","Djibouti","Dominica","DominicanRepublic","EastTimor","Ecuador","Egypt","ElSalvador","EquatorialGuinea","Eritrea","Estonia","Ethiopia","FaeroeIslands","FalklandIslands","Fiji","Finland","FormerYugoslavRepublicofMacedonia","France","FrenchGuiana","FrenchPolynesia","FrenchSouthernTerritories","Gabon","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","HeardIslandandMcDonaldIslands","Honduras","HongKong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","MarshallIslands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","NetherlandsAntilles","NewCaledonia","NewZealand","Nicaragua","Niger","Nigeria","Niue","NorfolkIsland","NorthKorea","NorthernMarianas","Norway","Oman","Pakistan","Palau","Panama","PapuaNewGuinea","Paraguay","Peru","Philippines","PitcairnIslands","Poland","Portugal","PuertoRico","Qatar","Reunion","Romania","Russia","Rwanda","SqoTomeandPrincipe","SaintHelena","SaintKittsandNevis","SaintLucia","SaintPierreandMiquelon","SaintVincentandtheGrenadines","Samoa","SanMarino","SaudiArabia","Senegal","Serbia","Seychelles","SierraLeone","Singapore","Slovakia","Slovenia","SolomonIslands","Somalia","SouthAfrica","SouthGeorgiaandtheSouthSandwichIslands","SouthKorea","SouthSudan","Spain","SriLanka","Sudan","Suriname","SvalbardandJanMayen","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","TheBahamas","TheGambia","Togo","Tokelau","Tonga","TrinidadandTobago","Tunisia","Turkey","Turkmenistan","TurksandCaicosIslands","Tuvalu","VirginIslands","Uganda","Ukraine","UnitedArabEmirates","UnitedKingdom","UnitedStates","UnitedStatesMinorOutlyingIslands","Uruguay","Uzbekistan","Vanuatu","VaticanCity","Venezuela","Vietnam","WallisandFutuna","WesternSahara","Yemen","Yugoslavia","Zambia","Zimbabwe"));
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("");

    }


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> wrong_country_names = new ArrayList<>();
            for(int country= 0;country<country_list.size();country++){
                try {
                    HttpResponse<String> response = Unirest.get("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=" + country_list.get(country))
                            .header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                            .header("x-rapidapi-key", "972845f4d2msh3554974eda5a114p171a76jsna7d8826f2816")
                            .asString();
//                    Log.i("respone", response.getBody());

                    JSONObject myObject = new JSONObject(response.getBody());
                    if(!myObject.get("statusCode").toString().equals("200")){
                        wrong_country_names.add(country_list.get(country));
                        continue;
                    }
                    JSONArray stats= myObject.getJSONObject("data").getJSONArray("covid19Stats");
                    int confirmed_cases=0;
                    int deaths=0;
                    int recovered =0;
                    for(int i=0;i<stats.length();i++){
                        JSONObject stat_obj = stats.getJSONObject(i);
                        confirmed_cases+= stat_obj.getInt("confirmed");
                        deaths+=stat_obj.getInt("deaths");
                        recovered+=stat_obj.getInt("recovered");
                    }
                    Log.i("country is ", country_list.get(country));
                    Log.i("deaths ", String.valueOf(deaths));
                    Log.i("confirmed ", String.valueOf(confirmed_cases));
                    Log.i("recovered ", String.valueOf(recovered));
                    List<Map<String,List<String>>> result = new ArrayList<Map<String,List<String>>>();
                    Map<String, List<String>> map1 = new HashMap<String, List<String>>();
                    List<String> parameters = new ArrayList<String>();
                    parameters.add(String.valueOf(confirmed_cases));
                    parameters.add(String.valueOf(deaths));
                    parameters.add(String.valueOf(recovered));
                    map1.put(country_list.get(country), parameters);
                    result.add(map1);
                } catch (UnirestException | JSONException e) {
                    e.printStackTrace();
                }
            }
            String wrong = TextUtils.join(", ", wrong_country_names);
            Log.i("wrong country names", wrong);
            return null;
        }
    }

}