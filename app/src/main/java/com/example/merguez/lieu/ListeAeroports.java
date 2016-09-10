package com.example.merguez.lieu;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by grap on 10/09/2016.
 */
public class ListeAeroports {

    private Context context;

    public ListeAeroports(){}

    public ListeAeroports(Context current){
        this.context = current;
    }

    public ArrayList<HashMap<String,String>> majBDD(String saisieTexte) {
        boolean estContenu = false;
            InputStream fic = context.getResources().openRawResource(R.raw.aeroports);
            InputStreamReader streamReader = new InputStreamReader(fic);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        StringBuilder text = new StringBuilder();
        ArrayList<HashMap<String,String>> listeGlobale = new ArrayList<>();
        HashMap<String,String> listeAeroports;
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


}
