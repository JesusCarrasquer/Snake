/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

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
        if (this.getCoordX() > a.coordX && this.getCoordX() < a.getCoordX()+32) {
            if (this.getCoordY() > a.getCoordY() && this.getCoordY() < this.getCoordY()+32) {
                return true;
            }
        }
        return false;
    }
    
}