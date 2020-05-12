package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.company.Plik.listaZderzen;


public class Panel extends JPanel {

    private final int DELAY = 16; //ms dla 60fps
    private final int GRAWITACJA = 600000; //grawitacja uaktywania się po 10s bezczynności
    private final double CZAS_DEFORMACJI = 500.0; // Kulka po zderzeniu ze ścianą deformuje się na 0.5s
    private final int KULKI_PRZY_WEJSCIU = 3; //Ile kulek ma się pojawiać przy wejściu myszki w obszar okna
    private ArrayList<Kula> listaKul;
    private int size = 50;
    private Timer timer;
    private long startTime = System.currentTimeMillis();
    public Plik pliczek;
    JFrame nowe = null;

    public Panel() {
        listaKul = new ArrayList<>();
        setBackground(Color.BLACK);

        addMouseListener(new Listener());
        addMouseMotionListener(new Listener());
        addMouseWheelListener(new Listener());

        timer = new Timer(DELAY, new Listener());
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Kula k : listaKul) {
            g.setColor(k.kolor);
            if(k.xc == true && k.yc == true){
                g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size - (int)(k.size*(1/4.0 * ((CZAS_DEFORMACJI+ k.odksztalceniex - System.currentTimeMillis())/CZAS_DEFORMACJI))), k.size - (int)(k.size*(1/4.0 * ((CZAS_DEFORMACJI+ k.odksztalceniey - System.currentTimeMillis())/CZAS_DEFORMACJI))));
                if(System.currentTimeMillis() - k.odksztalceniex >= CZAS_DEFORMACJI){
                    k.xc = false;
                }
                else if(System.currentTimeMillis() - k.odksztalceniey >= CZAS_DEFORMACJI){
                    k.yc = false;
                }
            }
            else if(k.xc == true){
                g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size - (int)(k.size*(1/4.0 * ((CZAS_DEFORMACJI+ k.odksztalceniex - System.currentTimeMillis())/CZAS_DEFORMACJI))), k.size);
                if(System.currentTimeMillis() - k.odksztalceniex >= CZAS_DEFORMACJI){
                    k.xc = false;
                }
            }
            else if(k.yc == true){
                g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size - (int)(k.size*(1/4.0 * ((CZAS_DEFORMACJI+ k.odksztalceniey - System.currentTimeMillis())/CZAS_DEFORMACJI))));
                if(System.currentTimeMillis() - k.odksztalceniey >= CZAS_DEFORMACJI){
                    k.yc = false;
                }
            }
            else{
                g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size);
            }
            //g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size);
        }
        //licznik kul
        g.setColor(Color.YELLOW);
        g.drawString("Ilość: " + Integer.toString(listaKul.size()), 40, 40);
        g.drawString("Rozmiar: " + Integer.toString(size), 40, 51);
        if(System.currentTimeMillis() - startTime >= GRAWITACJA){
            g.drawString("GRAWITACJA", 40, 62);
        }
    }

    private class Listener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }



        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if(mouseEvent.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK){
                boolean czyBlisko = false;
                for (Kula k: listaKul) {
                    double odleglosc = Math.sqrt(Math.pow((mouseEvent.getX() - k.x),2) + Math.pow((mouseEvent.getY() - k.y),2));
                    double sumaPromieni = (k.size + size) /2.0;
                    if(odleglosc < sumaPromieni+1 ){
                        czyBlisko = true;
                    }
                }
                if(czyBlisko == false){
                    listaKul.add(new Kula(mouseEvent.getX(), mouseEvent.getY(), size));
                    repaint();
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
//           for(int i =0;i <KULKI_PRZY_WEJSCIU;i++){
//               listaKul.add(new Kula(mouseEvent.getX(), mouseEvent.getY(), size));
//           }
            if(nowe != null){
                nowe.dispose();
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            ArrayList<Plik.Kulla> lista = pliczek.listaZderzen();
            ArrayList<Kula> odczytKul = new ArrayList<>();
            for (Plik.Kulla k: lista) {
                odczytKul.add(new Kula(k.x,k.y,k.size));
            }
            nowe = new JFrame("Nowe");
            nowe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            nowe.setPreferredSize(new Dimension(1280,720));
            nowe.setLocation(getX()+getWidth(), 0);
            nowe.getContentPane().add(new Informacja(odczytKul));
            nowe.pack();
            nowe.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            for (Kula k : listaKul) {
                try {
                    k.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if(mouseEvent.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK){
                boolean czyBlisko = false;
                for (Kula k: listaKul) {
                    double odleglosc = Math.sqrt(Math.pow((mouseEvent.getX() - k.x),2) + Math.pow((mouseEvent.getY() - k.y),2));
                    double sumaPromieni = (k.size + size) /2.0;
                    if(odleglosc < sumaPromieni+1 ){
                        czyBlisko = true;
                    }
                }
                if(czyBlisko == false){
                    listaKul.add(new Kula(mouseEvent.getX(), mouseEvent.getY(), size));
                    repaint();

                }
            }
            else if(mouseEvent.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK){
                for (Kula k: listaKul) {
                    double odleglosc = Math.sqrt(Math.pow((mouseEvent.getX() - k.x),2) + Math.pow((mouseEvent.getY() - k.y),2));
                    if(odleglosc <= k.size){
                        k.yspeed = -10;
                        startTime = System.currentTimeMillis();
                    }
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            size = size - mouseWheelEvent.getWheelRotation();
            if(size < 2){
                size = 2;
            }
        }
    }

    protected class Kula {
        public int x, y, size;
        public double xspeed = 0, yspeed = 0;
        public boolean kolizja = true, xc = false, yc = false;
        public Color kolor;
        public long odksztalceniex;
        public long odksztalceniey;
        private final double MAX_SPEED = 10;

        public Kula(int x, int y, int size){
            this.x = x;
            this.y = y;
            this.size = size;

            // składowe RGB
            kolor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

            do{
                xspeed = (Math.random() * MAX_SPEED * 2 - MAX_SPEED);

                yspeed = (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            }while(xspeed == 0 || yspeed == 0);

        }

        public void update() throws IOException {
            x += xspeed;
            y += yspeed;

            if (x - size / 2.0 <= 0) {
                x = (size/2) + 1 ;
                xspeed = -xspeed;
                xc = true;
                odksztalceniex = System.currentTimeMillis();
            }
            else if(x + size / 2.0 >= getWidth()){
                x = getWidth() - (size/2) - 1;
                xspeed = -xspeed;
                xc = true;
                odksztalceniex = System.currentTimeMillis();
            }
            else if(y - size / 2.0 <= 0){
                y = (size/2) + 1;
                yspeed = -yspeed;
                yc = true;
                odksztalceniey = System.currentTimeMillis();
            }

            else if(!(y + size / 2.0 >= getHeight()) && (System.currentTimeMillis() - startTime >= GRAWITACJA)){
                yspeed= yspeed + 0.1;
            }

            else if (y + size / 2.0 >= getHeight() && System.currentTimeMillis() - startTime >= GRAWITACJA ){
                y = getHeight() - (size/2);
                yspeed = -0.25 * yspeed;
                //xspeed = 0.75 * xspeed;
            }

            else if (y + size / 2.0 >= getHeight()){
                y = getHeight() - (size/2)- 1;
                yspeed = -yspeed;
                yc = true;
                odksztalceniey = System.currentTimeMillis();
            }


            //kolizje
            for (Kula k1: listaKul) {
                if(this != k1) {
                    double sumaPromieni = (size + k1.size) / 2.0;
                    double odleglosc = Math.sqrt(Math.pow((x - k1.x), 2) + Math.pow((y - k1.y), 2));
                    if ((odleglosc <= sumaPromieni) && kolizja ==  true) {
                        pliczek.zapis(this, k1);
                        double xs = xspeed, ys = yspeed;
                        xspeed = ((xspeed * (size - k1.size) + 2 * k1.size * k1.xspeed) / (size + k1.size));
                        yspeed = ((yspeed * (size - k1.size) + 2 * k1.size * k1.yspeed) / (size + k1.size));
                        k1.xspeed = ((k1.xspeed * (k1.size - size) + 2 * size * xs) / (size + k1.size));
                        k1.yspeed = ((k1.yspeed * (k1.size - size) + 2 * size * ys) / (size + k1.size));
                        if (x >= k1.x) {
                            x = (int) (x - (odleglosc - sumaPromieni));
                        }
                        if (x < k1.x) {
                            x = (int)(x + (odleglosc-sumaPromieni));
                        }
                        if(y >= k1.y){
                            y = (int) (y - (odleglosc - sumaPromieni));
                        }
                        if (y < k1.y) {
                            y = (int)(y + (odleglosc-sumaPromieni));
                        }
                        kolizja = false;
                    }
                    else if(odleglosc >= (sumaPromieni + 1)){
                        kolizja = true;
                    }
                }
            }

            if(xspeed == 0 || yspeed == 0){
                do{
                    xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);

                    yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
                }while(xspeed == 0 || yspeed == 0);
            }

        }

    }

    private class oknoDziecko{

    }
}