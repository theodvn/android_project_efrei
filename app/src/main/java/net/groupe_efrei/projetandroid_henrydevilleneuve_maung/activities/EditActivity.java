package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("EventMe", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        final EditText prenom = (EditText) findViewById(R.id.inprenom);
        final EditText nom = (EditText) findViewById(R.id.innom);
        final DatePicker datenaissance = (DatePicker) findViewById(R.id.incalendar);
        Button valider = (Button) findViewById(R.id.valider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!prenom.getText().toString().isEmpty()){
                    editor.putString("prenom", prenom.getText().toString());
                }
                if(!nom.getText().toString().isEmpty()){
                    editor.putString("nom", nom.getText().toString());
                }
                String selectedDate = datenaissance.getDayOfMonth() + "/" + datenaissance.getMonth() + "/" + datenaissance.getYear();
                editor.putString("date", selectedDate);
                editor.commit();
                Intent in = new Intent(EditActivity.this, PagePersoActivity.class);
                startActivity(in);
                EditActivity.this.finish();
            }
        });
    }
}
