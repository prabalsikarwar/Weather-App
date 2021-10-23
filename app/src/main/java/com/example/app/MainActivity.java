package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText et1;
    private TextView t1,t2,temp,longi,lati,datetime,faren,humidity,cod,press,wind1,feel;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datetime=findViewById(R.id.datetime);
        feel=findViewById(R.id.feel1);
        press=findViewById(R.id.pressure);
        wind1=findViewById(R.id.windspeed);
        cod=findViewById(R.id.code1);
        faren=findViewById(R.id.farenheit);
        button=findViewById(R.id.button);
        et1=findViewById(R.id.entercity);
        humidity=findViewById(R.id.humidity);
        t1=findViewById(R.id.weatherreport);
        longi=findViewById(R.id.longi);
        lati=findViewById(R.id.lati);
        t2=findViewById(R.id.city);
        temp=findViewById(R.id.temp);
        icon=findViewById(R.id.imageforecast);
        wind1=findViewById(R.id.windspeed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
findweather();
            }
        });

    }
    public  void findweather(){
        final String city=et1.getText().toString();
        String url="http://api.weatherapi.com/v1/current.json?key=b80c6e918a8b40ed86a60659212210&q="+city+"&aqi=yes";
        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Object1=jsonObject.getJSONObject("location");
                    String name = Object1.getString("name");
                    et1.setText(name);
                    String country = Object1.getString("country");
                    t1.setText(country);
                    String region =Object1.getString("region");
                    t2.setText(region);
                    String lat =Object1.getString("lat");
                    longi.setText(lat);
                    String lon=Object1.getString("lon");
                    lati.setText(lon);

                    String date = Object1.getString( "localtime");
                    datetime.setText(date);
                    JSONObject object2 = jsonObject.getJSONObject("current");
                    String temp1 =object2.getString("temp_c");
                    temp.setText(temp1+"°C");
                    String far=object2.getString("temp_f");
                    faren.setText(far+"°F");
                    JSONObject object3 = jsonObject.getJSONObject("current").getJSONObject("condition");
                    String icon1 = object3.getString("icon");
                    Picasso.get().load("http:".concat(icon1)).into(icon);
                    String code= object3.getString("code");
                    cod.setText(code);
                    JSONObject object4 = jsonObject.getJSONObject("current");
                    String humidity1 = object4.getString("humidity");
                    humidity.setText(humidity1);
                    String pressure=object4.getString("pressure_mb");
                    press.setText(pressure);
                    String wind=object4.getString("wind_kph");
                    wind1.setText(wind+"km/hr");
                    String feel1=object4.getString("feelslike_c");
                  feel.setText(feel1+"°C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringrequest);
    }
}