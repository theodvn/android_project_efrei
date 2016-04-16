package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.model.Event;

import java.util.StringTokenizer;

public class EventDetails extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Event event;
    MaterialFavoriteButton vFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        event = getIntent().getParcelableExtra("event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView image = (ImageView) findViewById(R.id.imageevent);
        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        TextView longDescription = (TextView) findViewById(R.id.longdescription);
        TextView date = (TextView) findViewById(R.id.date);
        vFavorite = (MaterialFavoriteButton) findViewById(R.id.favorite);

        sharedpreferences = getApplicationContext().getSharedPreferences("EventMe", Context.MODE_PRIVATE);

        String favOld = sharedpreferences.getString("fav", "");
        Log.i("prefcomp", favOld + " : " + event.getId());
        if (favOld.contains(event.getId())){
            vFavorite.setFavorite(true, false);
        }

        vFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if(favorite){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String favOld = sharedpreferences.getString("fav", "");
                    favOld = favOld + event.getId() + ";";
                    editor.putString("fav", favOld);
                    editor.commit();
                } else if (!favorite) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String favOld = sharedpreferences.getString("fav", "");
                    StringTokenizer multiTokenizer = new StringTokenizer(favOld, ";");
                    String newFav ="";
                    while (multiTokenizer.hasMoreTokens()) {

                        String token = multiTokenizer.nextToken();
                        if(!token.equals(event.getId())){
                            newFav += token + ";";
                        }
                    }
                    editor.putString("fav", newFav);
                    editor.commit();
                }
            }
        });



        Picasso.with(getApplicationContext()).load(event.getImageUrl()).into(image);
        title.setText(event.getTitle());
        description.setText(event.getShortDescription());
        longDescription.setText(event.getLongDescription());

        date.setText(event.getDate());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().getStringExtra("caller").equals("listmusic")){
                    Intent in = new Intent(EventDetails.this, ListMusicActivity.class);
                    startActivity(in);
                } else {
                    Intent in = new Intent(EventDetails.this, PagePersoActivity.class);
                    startActivity(in);
                }
                EventDetails.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String favOld = sharedpreferences.getString("fav", "");
        Log.i("prefcomp", favOld + " : " + event.getId());
        if (favOld.contains(event.getId())){
            vFavorite.setFavorite(true, false);
        }
    }
}
