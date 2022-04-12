/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import java.util.ArrayList;

import Snake.Nodo.dir;

/**
 *
 * @author Yisus
 */
public class Snake {
    
    private ArrayList<Nodo> nodos;
    private boolean alive;
    public static int snakeSpeed = 32;
    
    public Snake(){
        Nodo nodo1 = new Nodo(0,0,Nodo.dir.SOUTH);
        nodos = new ArrayList<>();
        nodos.add(nodo1);
        alive = true;
    }
    
    public boolean getAlive(){
        return this.alive;
    }

    public void updateAlive(){
        
        for(int i = 0; i<nodos.size(); i++){
            for(int k = i+1; k<nodos.size();k++){
                if(nodos.get(i).equals(nodos.get(k))){
                    this.alive = false;
                    break;
                }
            }
        }
    }

    public void creaNodo(){
        Nodo lastNodo = nodos.get(nodos.size()-1);
        dir direccionLast = lastNodo.getDireccion();
        switch(direccionLast){
            case EAST:
                nodos.add(new Nodo(lastNodo.getCoordX()-32, lastNodo.getCoordY(), direccionLast));
                break;
            case NORTH:
                nodos.add(new Nodo(lastNodo.getCoordX(), lastNodo.getCoordY()+32, direccionLast));
                break;
            case SOUTH:
                nodos.add(new Nodo(lastNodo.getCoordX(), lastNodo.getCoordY()-32, direccionLast));
                break;
            case WEST:
                nodos.add(new Nodo(lastNodo.getCoordX()+32, lastNodo.getCoordY(), direccionLast));
                break;
            default:
                break;
        }
    }
    
    public void updateMovimientoNodos(){
        for(Nodo e : nodos){
            e.updatePos();
        }
    }

    public void updateSeguimientoNodos(){
        int nodosSize = nodos.size();
        
        for (int i = nodosSize-1; i>0; i--) {
            Nodo nAnterior = nodos.get(i-1);
            Nodo nActual = nodos.get(i);

            dir direccionActual = nActual.getDireccion();
            dir direccionAnterior = nAnterior.getDireccion();

            if (direccionActual != direccionAnterior) {
                nActual.setDireccion(direccionAnterior);
            }
        }
    }
    
    public void cambiaDir(Nodo.dir direccion){
        nodos.get(0).setDireccion(direccion);
    }

    public ArrayList<Nodo> getListaNodos(){
        return nodos;
    }
}
