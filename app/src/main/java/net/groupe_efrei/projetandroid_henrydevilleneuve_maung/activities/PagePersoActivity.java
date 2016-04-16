package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.VolleyQueueSingleton;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.adapters.EventAdapter;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PagePersoActivity extends AppCompatActivity{

    private List<Event> listEvents;
    private EventAdapter eAdapt;
    private ProgressBar pBar;
    SharedPreferences sharedpreferences;
    private final static String EVENT_URL = "http://opendata.paris.fr/api/records/1.0/search/?dataset" +
            "=evenements-a-paris&facet=updated_at&facet=tags&facet=department&facet=region&facet=city&" +
            "facet=date_start&facet=date_end&refine.tags=musique&refine.tags=sortir";
    private RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_perso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView prenom = (TextView) findViewById(R.id.prenom);
        TextView nom = (TextView) findViewById(R.id.nom);
        TextView dateNaissance = (TextView) findViewById(R.id.datenaissance);

        ImageView edit = (ImageView) findViewById(R.id.edit);

        sharedpreferences = getApplicationContext().getSharedPreferences("EventMe", Context.MODE_PRIVATE);
        String value = sharedpreferences.getString("prenom", null);
        if(value == null){
            prenom.setText("N/C");
        } else {
            prenom.setText(value);
        }
        value = sharedpreferences.getString("nom",null);
        if(value == null){
            nom.setText("N/C");
        } else {
            nom.setText(value);
        }
        value = sharedpreferences.getString("date",null);
        if(value == null){
            dateNaissance.setText("N/C");
        } else {
            dateNaissance.setText(value);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(PagePersoActivity.this, EditActivity.class);
                startActivity(edit);
            }
        });

        //Setup recyclerView
        pBar = (ProgressBar) findViewById(R.id.progressbar);
        recList = (RecyclerView) findViewById(R.id.listmusic);
        recList.setHasFixedSize(true);
        recList.setVisibility(View.GONE);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        loadDatas();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
    }

    private void loadDatas() {

        listEvents = new ArrayList<>();
        RequestQueue queue = VolleyQueueSingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EVENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listEvents.clear();
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonArray = json.getJSONArray("records");
                            for(int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject event = (JSONObject)jsonArray.get(i);
                                event = event.getJSONObject("fields");
                                if(event.has("pricing_info")){
                                    listEvents.add(new Event(event.getString("uid"),event.getString("title"), event.getString("description"), event.getString("free_text"),
                                            event.getString("image"), event.getString("pricing_info"), event.getString("address"), event.getString("link"),event.getString("date_start")));
                                } else {
                                    listEvents.add(new Event(event.getString("uid"), event.getString("title"), event.getString("description"), event.getString("free_text"),
                                            event.getString("image"), "N/C", event.getString("address"), event.getString("link"),event.getString("date_start")));
                                }

                            }
                        } catch (JSONException e){
                            Log.e("ERROR", e.getMessage());
                        }
                        List<Event> favEvent = new ArrayList<>();
                        for (Event e : listEvents){
                            if(sharedpreferences.getString("fav","").contains(e.getId())){
                                favEvent.add(e);
                            }
                        }
                        eAdapt = new EventAdapter(favEvent,getApplicationContext(), "pageperso");
                        recList.setAdapter(eAdapt);
                        pBar.setVisibility(View.GONE);
                        recList.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR : Connection failed.", Toast.LENGTH_LONG).show();
            }
        });

        VolleyQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent in = new Intent(PagePersoActivity.this, ListMusicActivity.class);
                startActivity(in);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
