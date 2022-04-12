package Snake;

public class Comida implements Comparable<Nodo> {

    private int coordX;
    private int coordY;

    public Comida(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    @Override
    public int compareTo(Nodo o) {
        if(o.getCoordX() == coordX && o.getCoordY() == coordY){
            return 0;
        }
        //Magic hitbox bullshit shinanigans
        if ((coordX >= o.getCoordX() && coordX <= o.getCoordX()+32) || (coordX+32 >= o.getCoordX() && coordX <=o.getCoordX())) {
            if ((coordY <= o.getCoordY() && coordY+32 >= o.getCoordY()) || (coordY>=o.getCoordY() && coordY <= o.getCoordY()+32)) {
                return 0;
            }
        }
        return 1;
    }
    
    public int getCoordX(){
        return coordX;
    }

    public int getCoordY(){
        return coordY;
    }

}
