package com.company;

import java.io.*;
import java.util.ArrayList;

public class Plik extends Panel{
    public static File plik = new File("kolizje.txt");
    private static ArrayList<Zderzenie> listaKolizji = new ArrayList<>();

    public static void zapis(Kula k1, Kula k2){
        listaKolizji.add(new Zderzenie(k1,k2));
        FileWriter fw;
        if(listaKolizji.size() >20){
            for (Zderzenie i: listaKolizji) {
                try{
                    fw = new FileWriter(plik, true);
                    String zderzenie;
                    zderzenie = Integer.toString(i.k1.x) + "," + Integer.toString(i.k1.y) + "," + Integer.toString(i.k1.size) + "," + Integer.toString(i.k2.x) + "," + Integer.toString(i.k2.y) + "," + Integer.toString(i.k2.size) + "\n";
                    fw.append(zderzenie);
                    fw.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            listaKolizji.clear();
        }
    }

    public static ArrayList<Kulla> listaZderzen(){
        ArrayList<String> listaLinii = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(plik));
            String linia;
            //StringBuilder sb = new StringBuilder();
            while((linia = br.readLine()) != null){
                listaLinii.add(linia);//sb.append(linia).append("\n");
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Kulla> wyjscie = new ArrayList<>();
        for (String linia: listaLinii) {
            String[] tab;
            tab = linia.split(",");
            Kulla k1 = new Kulla(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
            Kulla k2 = new Kulla(Integer.parseInt(tab[3]), Integer.parseInt(tab[4]), Integer.parseInt(tab[5]));
            wyjscie.add(k1);
            wyjscie.add(k2);
        }
        return wyjscie;
    }

    private static class Zderzenie{
        private Kula k1;
        private Kula k2;

        public Zderzenie(Kula k1, Kula k2){
            this.k1 = k1;
            this.k2 = k2;
        }
    }

    public static class Kulla{
        public int x,y,size;

        public Kulla(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
    }
}
