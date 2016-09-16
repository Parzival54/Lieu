package com.example.merguez.lieu;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by merguez on 12/09/2016.
 */
public class ListeVols {
    String aeroportDepart;
    String aeroportArrivee;
    String heureDepart;
    String heureArrivee;
    String[] tableauHoraires = {"06:00","07:05","08:10","09:20","10:30","11:40","12:50","13:55","15:00","16:00","17:10","18:20","19:30","20:40","21:50","22:55"};
    int compagnie;
    int classe;
    double prixEco;
    double prixBusiness;
    double prixEnfant;
    double latitudeDepart;
    double longitudeDepart;
    double latitudeArrivee;
    double longitudeArrivee;
    double distance;
    double duree;
    double prix;
    DecimalFormat df = new DecimalFormat();


    Context context;

    public ListeVols() {
    }

    public void creerVol(FileOutputStream fos, Context context) throws IOException {
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        //fos = context.openFileOutput("vols.txt", Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        String[][] listeAeroport = {{"CDG", "49.012779", "2.55"}, {"LHR", "51.4775", "-0.461389"}, {"GIG", "-22.808903", "-43.243647"}, {"BCN", "41.297078", "2.078464"},
                {"RKV", "64.13", "-21.940556"}, {"FCO", "41.804475", "12.250797"}, {"LAX", "33.942536", "-118.408075"}, {"JFK", "40.639751", "-73.778925"},
                {"ICN", "37.469075", "126.450517"}, {"HND", "35.552258", "139.779694"}};
        for (int l = 0; l < 10; l++) {
            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < 10; j++) {
                    if (i != j) {
                        int alea = new Random().nextInt(16);
                        int compagnie = new Random().nextInt(5);
                        for (int k = 1; k <= 3; k++) {
                            aeroportDepart = listeAeroport[i][0];
                            latitudeDepart = Double.parseDouble(listeAeroport[i][1]);
                            longitudeDepart = Double.parseDouble(listeAeroport[i][2]);
                            aeroportArrivee = listeAeroport[j][0];
                            latitudeArrivee = Double.parseDouble(listeAeroport[j][1]);
                            longitudeArrivee = Double.parseDouble(listeAeroport[j][2]);
                            distance = distance(latitudeDepart, longitudeDepart, latitudeArrivee, longitudeArrivee);
                            duree = distance / 800 + 0.5;
                            classe = k;
                            switch (classe) {
                                case 1:
                                    prix = (double) (int) ((100 + duree * 15 * Math.log10(distance)) * 100) / 100;
                                    break;
                                case 2:
                                    prix = (double) (int) (100 + duree * 15 * Math.log10(distance) * (1.5 + new Random().nextInt(11) / 10) * 100) / 100;
                                    break;
                                case 3:
                                    prix = (double) (int) (100 + duree * 15 * Math.log10(distance) * (0.8 + new Random().nextInt(2) / 10) * 100) / 100;
                                    break;
                            }

                            heureDepart = tableauHoraires[alea];
                            int harr = Integer.parseInt(heureDepart.substring(0, 2)) + (int) Math.floor(duree);
                            int marr = (int) (Integer.parseInt(heureDepart.substring(3)) + (duree - Math.floor(duree)) * 60);
                            int jarr = 0;

                            if (marr >= 60) {
                                marr -= 60;
                                harr++;
                            }

                            if (harr >= 24) {
                                harr -= 24;
                                jarr++;
                            }

                            heureArrivee = new DecimalFormat("00").format(harr) + ":" + new DecimalFormat("00").format(marr) + "+" + jarr;
                            String texte = aeroportDepart + "," + aeroportArrivee + "," + heureDepart + "," + heureArrivee + ","
                                    + compagnie + "," + classe + "," + prix;
                            try {
                                bufferedWriter.newLine();
                                bufferedWriter.write(texte);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                    }

                }


            }
        }

        outputStreamWriter.close();
    }

    //Conversion des degrés en radian
    public double convertRad(double input){
        return (Math.PI * input)/180;
    }

    public double distance(double lat_a_degre, double lon_a_degre, double lat_b_degre, double lon_b_degre){

        double R = 6378000; //Rayon de la terre en mètre

        double lat_a = convertRad(lat_a_degre);
        double lon_a = convertRad(lon_a_degre);
        double lat_b = convertRad(lat_b_degre);
        double lon_b = convertRad(lon_b_degre);

        double d = R * (Math.PI/2 - Math.asin( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));
        return d/1000;
    }
}

