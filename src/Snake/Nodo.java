/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import Ventanas.Juego;

/**
 *
 * @author Yisus
 */
public class Nodo {
    
    private int coordX;
    private int coordY;
    public enum dir{
        NORTH,
        WEST,
        SOUTH,
        EAST
    }
    
    private dir direccion;
    

    public Nodo(int coordX, int coordY, dir direccion){
        this.direccion = direccion;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void updatePos(){
        int ancho = Juego.ANCHO * Juego.ESCALADO;
        int alto = Juego.ALTO * Juego.ESCALADO;
        switch(direccion){
            case EAST:
                coordX+=5;
                if(coordX>ancho){
                    coordX -= ancho;
                }
                break;
            case NORTH:
                coordY-=5;
                if(coordY<0){
                    coordY += alto;
                }
                break;
            case SOUTH:
                coordY+=5;
                if(coordY>alto){
                    coordY -= alto;
                }
                break;
            case WEST:
                coordX-=5;
                if(coordX<0){
                    coordX += ancho;
                }
                break;
            default:
                break;
        
        }
        
    }

    public dir getDireccion() {
        return direccion;
    }

    public void setDireccion(dir direccion) {
        this.direccion = direccion;
    }
    

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
    
    @Override
    public boolean equals(Object e){
        if (!(e instanceof Nodo)) {
            return false;
        }
        Nodo a = (Nodo) e;
        if (coordX > a.getCoordX() && coordX < (a.getCoordX()+32)) {
            if (coordY > a.getCoordY() && coordY < (coordY+32)) {
                return true;
            }
        }
        return false;
    }
    
}
