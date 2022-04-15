package Listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import MusicPlayer.MusicPlayer;
import Ventanas.Juego;
import Ventanas.Juego.STATE;
public class OpcionesListener implements MouseListener {
    MusicPlayer m;
    Juego j;

    public OpcionesListener(MusicPlayer music, Juego juego){
        m = music;
        j = juego;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        int anchomid = (Juego.ANCHOFINAL-200)/2;

        if(click.getX() > anchomid && click.getX() < anchomid+200){
            if(click.getY()> (Juego.ALTOFINAL*4/10)+28 && click.getY() < ((Juego.ALTOFINAL*4/10)+28)+Juego.ALTOFINAL/8){
                m.cambiaModo();
            }

            if(click.getY()>Juego.ALTOFINAL*6/8 && click.getY() <(Juego.ALTOFINAL*6/8)+Juego.ALTOFINAL/8){
                j.cambiaModo(STATE.MENU);
            }
        }
        
        if(click.getY()>(Juego.ALTOFINAL*6/10)+10 && click.getY()<((Juego.ALTOFINAL*6/10)+10)+50){
            //NIVEL 1
            if(click.getX()>anchomid+60 && click.getX()<anchomid+70){
                m.setVolume(0.1f);
                m.setNivel(1);
            }
            //NIVEL 2
            if(click.getX()>anchomid+80 && click.getX()<anchomid+90){
                m.setVolume(0.2f);
                m.setNivel(2);
            }
            //NIVEL 3
            if(click.getX()>anchomid+100 && click.getX()<anchomid+110){
                m.setVolume(0.3f);
                m.setNivel(3);
            }
            //NIVEL 4
            if(click.getX()>anchomid+120 && click.getX()<anchomid+130){
                m.setVolume(0.4f);
                m.setNivel(4);
            }
        }

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
    
}
