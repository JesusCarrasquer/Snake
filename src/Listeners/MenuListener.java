package Listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Ventanas.Juego;

public class MenuListener implements MouseListener {
    Juego j;

    public MenuListener(Juego juego){
        j = juego;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        if(click.getX()>(Juego.ANCHOFINAL/2)-100 && click.getX()<(Juego.ANCHOFINAL/2)+100){
            if(click.getY() >Juego.ALTOFINAL*3/8 && click.getY() < (Juego.ALTOFINAL*3/8)+Juego.ALTOFINAL/8){
                j.cambiaModo(Juego.STATE.JUEGO);
            }
            if(click.getY() > Juego.ALTOFINAL*5/8 && click.getY() < (Juego.ALTOFINAL*5/8)+Juego.ALTOFINAL/8){
                System.exit(1);
            }
        }
        if(click.getX() < 32){
            if(click.getY()> Juego.ALTOFINAL-32){
                j.cambiaModo(Juego.STATE.OPCIONES);
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
