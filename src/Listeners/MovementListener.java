package Listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Snake.Nodo;
import Snake.Snake;

public class MovementListener extends KeyAdapter {
    
    Snake s;

    public MovementListener(Snake s){
        this.s = s;
    }

    public void keyPressed(KeyEvent e){

        if(!s.getAlive()){
            return;
        }

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){
            s.cambiaDir(Nodo.dir.NORTH);
        }

        if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){
            s.cambiaDir(Nodo.dir.WEST);
        }

        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){
            s.cambiaDir(Nodo.dir.EAST);
        }

        if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){
            s.cambiaDir(Nodo.dir.SOUTH);
        }
    }

}
