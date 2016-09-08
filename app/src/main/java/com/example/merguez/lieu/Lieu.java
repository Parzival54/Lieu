package com.example.merguez.lieu;

import android.app.DatePickerDialog;
import android.support.annotation.RawRes;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Lieu extends AppCompatActivity {

    private EditText lieuETchoixAeroport;
    private List<HashMap<String,String>> liste;
    private Button lieuBtnMiseAJourListe;
    private ListView lieuLVlisteAeroports;
    private ArrayAdapter<String> arrayAdapter;
    private ListAdapter simpleadapter;
    private HashMap<String,String> listeAeroports;
    Calendar myCalendar;
    DatePicker lieuDPafficherCalendrier;
    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lieu);
        lieuETchoixAeroport  = (EditText) findViewById(R.id.lieuETchoixAeroport);
        liste  = new ArrayList<HashMap<String,String>>();
        lieuBtnMiseAJourListe = (Button) findViewById(R.id.lieuBtnMiseAJourListe);
        lieuLVlisteAeroports = (ListView) findViewById(R.id.list);
        Locale francais = Locale.FRENCH;
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        myCalendar = Calendar.getInstance(timeZone,francais);
        lieuDPafficherCalendrier = (DatePicker) findViewById(R.id.lieuDPafficherCalendrier);
        lieuDPafficherCalendrier.setVisibility(View.GONE);

        masquerClavier();


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        simpleadapter = new SimpleAdapter(this,getBDD(), android.R.layout.simple_list_item_2, new String[]{"text1","text2"}, new int[] {android.R.id.text1, android.R.id.text2});
        lieuLVlisteAeroports.setAdapter(simpleadapter);

        lieuETchoixAeroport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lieuDPafficherCalendrier.setVisibility(View.VISIBLE);
                new DatePickerDialog(Lieu.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                lieuDPafficherCalendrier.setVisibility(View.GONE);

            }
        });

        lieuBtnMiseAJourListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String choix = lieuETchoixAeroport.getText().toString();


            }
        });

        lieuLVlisteAeroports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lieuETchoixAeroport.setText(getBDD().get(position).get("text2"));
            }
        });

    }

    private void afficherClavier() {
                getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );

    }

    private void masquerClavier() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private ArrayList<HashMap<String,String>> getBDD() {
        InputStream fic = getResources().openRawResource(R.raw.test);
        InputStreamReader streamReader = new InputStreamReader(fic);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        StringBuilder text = new StringBuilder();
        ArrayList<HashMap<String,String>> listeglobale = new ArrayList<>();

        try {
            while (( line = bufferedReader.readLine()) != null) {
                listeAeroports = new HashMap<>();
                text.append(line);
                text.append('\n');

                int index1 = line.indexOf(",");
                int index2 = line.indexOf(",",index1+1);
                int index3 = line.indexOf(",",index2+1);
                int index4 = line.indexOf(",",index3+1);
                int index5 = line.indexOf(",",index4+1);
                int index6 = line.indexOf(",",index5+1);
                int index7 = line.indexOf(",",index6+1);
                int index8 = line.indexOf(",",index7+1);
                int index9 = line.indexOf(",",index8+1);

                String line1 = line.substring(index1 + 2,index2 - 1) + ", " + line.substring(index3 + 2,index4 - 1);

                listeAeroports.put("text1",line1);
                listeAeroports.put("text2",line.substring(index4 + 2,index5 - 1));
                listeglobale.add(listeAeroports);
                //Log.w("TAG",""+listeglobale.toString());
            }
        } catch (IOException e) {
            return null;
        }
        return listeglobale;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        lieuETchoixAeroport.setText(sdf.format(myCalendar.getTime()));
    }

}
