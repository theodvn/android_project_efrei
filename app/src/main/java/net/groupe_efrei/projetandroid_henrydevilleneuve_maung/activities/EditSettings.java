package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.services.NotifService;

public class EditSettings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Switch notif = (Switch) findViewById(R.id.notif);
        final EditText time = (EditText) findViewById(R.id.time);
        final Button appliquer = (Button) findViewById(R.id.appliquer);


        sharedPreferences = getApplicationContext().getSharedPreferences("EventMe", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("notif","").equals("oui")){
            notif.setChecked(true);
            time.setText(sharedPreferences.getString("frequence",""));
        } else {
            time.setText("10");
        }


        editor = sharedPreferences.edit();

        appliquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notif.isChecked()){
                    String freq = "10";
                    if(!time.getText().toString().isEmpty()){
                        freq = time.getText().toString();
                    }
                    editor.putString("frequence", freq);
                    editor.putString("notif", "oui");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Notifications activées, fréquence de " + freq + " minute(s).", Toast.LENGTH_LONG).show();
                    startService(new Intent(EditSettings.this, NotifService.class));
                } else {
                    editor.remove("notif");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Notifications désactivées.", Toast.LENGTH_LONG).show();
                    stopService(new Intent(EditSettings.this, NotifService.class));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in = new Intent(EditSettings.this, ListMusicActivity.class);
                startActivity(in);
                EditSettings.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
