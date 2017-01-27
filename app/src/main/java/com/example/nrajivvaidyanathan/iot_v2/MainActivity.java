package com.example.nrajivvaidyanathan.iot_v2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout;
    EditText ip;
    Button submit, toggle;
    RadioGroup choices;
    RadioButton on, off;
    int choice_id;
    String url_on, url_off, main_part;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.activity_main);
        ip = (EditText) findViewById(R.id.ip);
        submit = (Button) findViewById(R.id.submit);
        toggle = (Button) findViewById(R.id.toggle);
        choices = (RadioGroup) findViewById(R.id.choices);
        on = (RadioButton) findViewById(R.id.on);
        off = (RadioButton) findViewById(R.id.off);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_part = ip.getText().toString();
                url_on = "http://" + main_part + ":5000/?state=on";
                url_off = "http://" + main_part + ":5000/?state=off";
                ip.setText("");
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice_id = choices.getCheckedRadioButtonId();
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                if(on.getId()==choice_id) {
                    stringRequest = new StringRequest(Request.Method.POST, url_on, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            requestQueue.stop();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            layout.setBackgroundColor(Color.RED);
                            requestQueue.stop();
                        }
                    });
                    requestQueue.add(stringRequest);
                }
                else{
                stringRequest = new StringRequest(Request.Method.POST, url_off, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        layout.setBackgroundColor(Color.RED);
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);
            }}
        });
    }
}
