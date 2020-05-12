package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Informacja extends JPanel {
    private ArrayList<Panel.Kula> listaKul;

    public Informacja(ArrayList<Panel.Kula> listaKul){
        this.listaKul = listaKul;
        setBackground(Color.BLACK);
        addMouseListener(new Listener());
        addMouseMotionListener(new Listener());
        addMouseWheelListener(new Listener());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Panel.Kula k : listaKul) {

            g.setColor(new Color(255,255,255));
            g.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size);
        }
    }

    private class Listener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }
    }
}
