package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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

public class ListMusicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Event> listEvents;
    private EventAdapter eAdapt;
    private ProgressBar pBar;
    private SwipeRefreshLayout swipeRefresh;
    private final static String EVENT_URL = "http://opendata.paris.fr/api/records/1.0/search/?dataset" +
            "=evenements-a-paris&facet=updated_at&facet=tags&facet=department&facet=region&facet=city&" +
            "facet=date_start&facet=date_end&refine.tags=musique&refine.tags=sortir";
    private RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //startService(new Intent(ListMusicActivity.this, NotifService.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        pBar = (ProgressBar) findViewById(R.id.progressbar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
        //Setup recyclerView
        recList = (RecyclerView) findViewById(R.id.listmusic);
        recList.setHasFixedSize(true);
        recList.setVisibility(View.GONE);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
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

                        eAdapt = new EventAdapter(listEvents,getApplicationContext(), "listmusic");
                        recList.setAdapter(eAdapt);
                        pBar.setVisibility(View.GONE);
                        recList.setVisibility(View.VISIBLE);
                        swipeRefresh.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"ERROR : Connection failed.",Toast.LENGTH_LONG).show();
                swipeRefresh.setRefreshing(false);
            }
        });

        VolleyQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_music, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(ListMusicActivity.this, EditSettings.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            // CALL THIS ACTIVITY
        } else if (id == R.id.nav_favorite) {
            //CREATE PAGE PERSO ACTIVITY
            Intent intPerso = new Intent(ListMusicActivity.this, PagePersoActivity.class);
            startActivity(intPerso);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
