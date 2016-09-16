package com.example.merguez.lieu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lieu extends AppCompatActivity implements TextWatcher {

    private EditText lieuETchoixAeroport;
    private ListView lieuLVlisteAeroports;
    private ListAdapter simpleadapter;

    ArrayList<HashMap<String,String>> listeMaj;
    ArrayList<HashMap<String,String>> listeGlobale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lieu);
        lieuETchoixAeroport  = (EditText) findViewById(R.id.lieuETchoixAeroport);
        lieuLVlisteAeroports = (ListView) findViewById(R.id.list);
        FileOutputStream fos = null;
        Log.w("TAG",Lieu.this.getFilesDir().getAbsolutePath());
        try {
            fos = openFileOutput("vols.txt", MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            new ListeVols().creerVol(fos, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lieuETchoixAeroport.addTextChangedListener(this);
        masquerClavier();

        lieuETchoixAeroport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherClavier();


            }
        });

        lieuLVlisteAeroports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lieuETchoixAeroport.setText(listeGlobale.get(position).get("text2"));
            }
        });

    }

    private void afficherClavier() {
                getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );

    }

    private void masquerClavier() {
                getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String choix = lieuETchoixAeroport.getText().toString();
        listeMaj = new ListeAeroports(this).majBDD(choix);
        simpleadapter = new SimpleAdapter(getApplicationContext(),listeMaj, R.layout.list_view_lieu, new String[]{"text1","text2"}, new int[] {R.id.list_content, R.id.list_content2});
        lieuLVlisteAeroports.setAdapter(simpleadapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
/*
        DatePickerDialog.OnDateSetListener date;
        Calendar myCalendar;

        myCalendar = Calendar.getInstance(timeZone,francais);


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        lieuETchoixAeroport.setText(sdf.format(myCalendar.getTime()));
    }

        new DatePickerDialog(Lieu.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
*/

}
