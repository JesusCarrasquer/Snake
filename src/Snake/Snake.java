/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import java.util.ArrayList;

/**
 *
 * @author Yisus
 */
public class Snake {
    
    ArrayList<Nodo> nodos;
    private boolean alive;
    
    public Snake(){
        Nodo nodo1 = new Nodo(0,0,Nodo.dir.SOUTH);
        nodos = new ArrayList<>();
        nodos.add(nodo1);
        alive = true;
    }
    
    public boolean getAlive(){
        return this.alive;
    }
    
    public void updateNodos(){
        int nodosSize = nodos.size();
        
        for (int i = 1; i < nodosSize; i++) {
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
}
