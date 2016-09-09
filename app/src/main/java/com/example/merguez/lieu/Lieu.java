package com.example.merguez.lieu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Lieu extends AppCompatActivity implements TextWatcher {

    private EditText lieuETchoixAeroport;
    private List<HashMap<String,String>> liste;
    private ListView lieuLVlisteAeroports;
    private ArrayAdapter<String> arrayAdapter;
    private ListAdapter simpleadapter;
    private HashMap<String,String> listeAeroports;
    Calendar myCalendar;
    ArrayList<HashMap<String,String>> listeMaj;
    //DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lieu);
        lieuETchoixAeroport  = (EditText) findViewById(R.id.lieuETchoixAeroport);
        liste  = new ArrayList<HashMap<String,String>>();
        lieuLVlisteAeroports = (ListView) findViewById(R.id.list);
        Locale francais = Locale.FRENCH;
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        myCalendar = Calendar.getInstance(timeZone,francais);
        lieuETchoixAeroport.addTextChangedListener(this);

        masquerClavier();

/*
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
*/
        lieuETchoixAeroport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherClavier();
                /*new DatePickerDialog(Lieu.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

            }
        });

        lieuLVlisteAeroports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //lieuETchoixAeroport.setText(getBDD().get(position).get("text2"));
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
/*
    private ArrayList<HashMap<String,String>> getBDD() {
        InputStream fic = getResources().openRawResource(R.raw.aeroports);
        InputStreamReader streamReader = new InputStreamReader(fic);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        StringBuilder text = new StringBuilder();
        ArrayList<HashMap<String,String>> listeGlobale = new ArrayList<>();

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
                listeGlobale.add(listeAeroports);
                //Log.w("TAG",""+listeGlobale.toString());
            }
        } catch (IOException e) {
            return null;
        }
        return listeGlobale;

    }
*/
    private ArrayList<HashMap<String,String>> majBDD(String saisieTexte) {
        boolean estContenu;
        InputStream fic = getResources().openRawResource(R.raw.aeroports);
        InputStreamReader streamReader = new InputStreamReader(fic);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        StringBuilder text = new StringBuilder();
        ArrayList<HashMap<String,String>> listeGlobale = new ArrayList<>();
        String[] mots = saisieTexte.split(" ");
        if (saisieTexte.length() >= 3) {
            try {
                while (( line = bufferedReader.readLine()) != null) {
                    estContenu = true;
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

                    for (int i = 0; i < mots.length; i++) {
                        if (!line.substring(0,index5).toUpperCase().contains(mots[i].toUpperCase())) {
                           estContenu =  false;
                        }
                    }

                    if (estContenu) {
                        String line1 = line.substring(index1 + 2,index2 - 1) + ", " + line.substring(index3 + 2,index4 - 1);
                        listeAeroports.put("text1",line1);
                        listeAeroports.put("text2",line.substring(index4 + 2,index5 - 1));
                        listeGlobale.add(listeAeroports);
                    } // end if
                } // end while
            } catch (IOException e) {
                return null;
            }
            //Log.w("TAG","" +listeGlobale.size());
            if (listeGlobale.size() != 0){
                return listeGlobale;
            }
            else {
                listeAeroports = new HashMap<>();
                listeAeroports.put("text1","Aucune réponse");
                listeGlobale.add(listeAeroports);
                return listeGlobale;
            }
        } else {
            HashMap<String,String> listeVide = new HashMap<>();
            listeVide.put("text1","");
            listeGlobale.add(listeVide);
            return listeGlobale;
        }

    }

    /*private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        lieuETchoixAeroport.setText(sdf.format(myCalendar.getTime()));
    }*/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String choix = lieuETchoixAeroport.getText().toString();
        listeMaj = majBDD(choix);
        simpleadapter = new SimpleAdapter(getApplicationContext(),listeMaj, R.layout.list_view_lieu, new String[]{"text1","text2"}, new int[] {R.id.list_content, R.id.list_content2});
        lieuLVlisteAeroports.setAdapter(simpleadapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
