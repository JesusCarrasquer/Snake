package Ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import MusicPlayer.MusicPlayer;

public class Opciones {

    public static void dibujaOpciones(Graphics2D drawer,MusicPlayer m){
        int anchomid = (Juego.ANCHOFINAL-200)/2;
        //TITULO OPCIONES
        drawer.setColor(new Color(221,160,221));
        drawer.setFont(new Font("Serif",Font.BOLD,40));
        drawer.drawString("SETTINGS", anchomid, (Juego.ALTOFINAL/8)+50);
        //VOLUMEN ON/OFF
        drawer.fillRect(anchomid, (Juego.ALTOFINAL*4/10)+28, 200, Juego.ALTOFINAL/8);
        drawer.setColor(Color.black);
        drawer.setFont(new Font("Monospaced",Font.BOLD,25));
        String on = m.getActivado() ? "ON" : "OFF";
        drawer.drawString("Musica: " + on, anchomid+25, ((Juego.ALTOFINAL*4/10)+28)+42);
        //BOTON NIVEL DE VOLUMEN
        drawer.setColor(new Color(221,160,221));
        drawer.fillRect(anchomid, Juego.ALTOFINAL*6/10, 200, Juego.ALTOFINAL/8);
        drawer.setColor(Color.yellow);
        drawer.fillRect(anchomid+60, (Juego.ALTOFINAL*6/10)+40, 10, 20);
        if(m.getNivel()==1){
            drawer.setColor(Color.black);
        }
        drawer.fillRect(anchomid+80, (Juego.ALTOFINAL*6/10)+30, 10, 30);
        if(m.getNivel()==2){
            drawer.setColor(Color.black);
        }
        drawer.fillRect(anchomid+100, (Juego.ALTOFINAL*6/10)+20, 10, 40);
        if(m.getNivel()==3){
            drawer.setColor(Color.black);
        }
        drawer.fillRect(anchomid+120, (Juego.ALTOFINAL*6/10)+10, 10, 50);
        
        //BOTON DE SALIR
        drawer.setColor(new Color(221,160,221));
        drawer.fillRect(anchomid, Juego.ALTOFINAL*6/8, 200, Juego.ALTOFINAL/8);
        drawer.setColor(Color.black);
        drawer.setFont(new Font("Monospaced",Font.BOLD,25));
        drawer.drawString("Volver", anchomid+50, (Juego.ALTOFINAL*6/8)+45);
    }


    
}
