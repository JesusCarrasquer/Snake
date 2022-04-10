/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;

import Listeners.MovementListener;
import Snake.Nodo;
import Snake.Snake;
import Snake.Nodo.dir;

/**
 *
 * @author Yisus
 */
public class Juego extends Canvas implements Runnable {

    //VARIABES DE TAMAÑO DE PANTALLA Y ESCALADO
    public final static int ANCHO = 400;
    public final static int ALTO = (ANCHO / 12) * 9;
    public final static int ESCALADO = 2;

    //THREAD VARIABLES
    private boolean arrancado = false;
    private Thread hiloEjecucion;
    
    //BUFFER FONDO
    BufferedImage fondo; // se inicializa en el constructor
    BufferedImage headUp;
    BufferedImage headRight;
    BufferedImage headLeft;
    BufferedImage headDown;
    BufferedImage bodyVert;
    BufferedImage bodyHorz;

    //VARIABLE JUGADOR
    Snake s;
    
    /**
     * Metodo para comenzar nuestro hilo de ejecucion en el juego
     */
    public synchronized void comenzar() {
        //si ya esta arrancado, salimos del metodo
        if (arrancado) {
            return;
        }
        //Si no actualizamos la variable e inicializamos el hilo
        arrancado = true;
        hiloEjecucion = new Thread(this);
        hiloEjecucion.start();
    }
    
    /**
     * Metodo para parar nuestro hilo de ejecucion en el juego
     */
    private synchronized void parar() {
        //si ya estaba parado salimos
        if (!arrancado) {
            return;
        }
        //si no estaba parado, actualizamos nuestra variable de control, juntamos todos los hilos de ejecucion y salimos del programa
        arrancado = false;
        try {
            hiloEjecucion.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    /**
     * Este metodo sera esencialmente nuestro motor de actualizacion, desde donde llamaremos al render y al tick cada vez
     */
    @Override
    public void run() {
        //variables relacionadas con el reloj de control
        int frames = 0, updates = 0;
        long timer = System.currentTimeMillis();
        //Variables relacionadas con el update del juego
        long ultimoTick = System.nanoTime();
        final double frameRate = 60.0;
        double ns = 1000000000 / frameRate;
        double catchup = 0;
        //Comprobacion cada vez que el metodo ejecute
        while (arrancado) {
            //Calculamos la diferencia entre el momento actual y el ultimo comprobado (en un pseudoporcentaje)
            long actual = System.nanoTime();
            catchup += (actual - ultimoTick) / ns;
            ultimoTick = actual;
            //Si esta diferencia es mayor a 1, significa que hemos pasado el 100%, por lo que actualizamos juego, añadimos update y reiniciamos el %
            if (catchup >= 1) {
                tick();
                updates++;
                catchup--;
            }
            //Tras cada comprobacion renderizamos el juego y añadimos frame
            render();
            frames++;
            
            //Cada segundo imprimimos los frames y updates registrados
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Frames: " + frames + " FPS Updates: " + updates);
                updates = 0;
                frames = 0;
            }
        }
        //Cuando el juego deje de estar arrancado paramos
        parar();
    }

    private void tick() {
        if(!s.getAlive()){
            s = new Snake();
        }
        s.updateMovimientoNodos();
        s.updateSeguimientoNodos();
        
    }

    private void render() {
        //Esta variable devuelve el bufferStrategy del canvas actual, devolvera null si no hemos hecho nada y el actual usado a partir de ahi.
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        //COMIENZA EL RENDER
        
        //dibujando el fondo
        g.drawImage(fondo,0,0, getWidth(), getHeight(), this);

        //dibujando la serpiente
        ArrayList<Nodo> nodos = s.getListaNodos();
        Graphics2D drawer = (Graphics2D) g;
        
        switch(nodos.get(0).getDireccion()){
            case EAST:
                drawer.drawImage(headRight,nodos.get(0).getCoordX(),nodos.get(0).getCoordY(),null);
                break;
            case NORTH:
                drawer.drawImage(headUp,nodos.get(0).getCoordX(),nodos.get(0).getCoordY(),null);
                break;
            case SOUTH:
                drawer.drawImage(headDown,nodos.get(0).getCoordX(),nodos.get(0).getCoordY(),null);
                break;
            case WEST:
                drawer.drawImage(headLeft,nodos.get(0).getCoordX(),nodos.get(0).getCoordY(),null);
                break;
        }

        for(int i = 1; i<nodos.size(); i++){
            if(nodos.get(i).getDireccion()==dir.NORTH ||  nodos.get(i).getDireccion()==dir.NORTH){
                drawer.drawImage(bodyVert,nodos.get(i).getCoordX(),nodos.get(i).getCoordY(),null);
            }
            else{
                drawer.drawImage(bodyHorz,nodos.get(i).getCoordX(),nodos.get(i).getCoordY(),null);
            }
        }
        
        //TERMINA EL RENDER
        g.dispose();
        bs.show();
    }
    

    public Juego() {
        this.setPreferredSize(new Dimension(ANCHO * ESCALADO, ALTO * ESCALADO));
        this.setMaximumSize(new Dimension(ANCHO * ESCALADO, ALTO * ESCALADO));
        this.setMinimumSize(new Dimension(ANCHO * ESCALADO, ALTO * ESCALADO));
        try{
            fondo = ImageIO.read(new File("src/Graficos/Assets/Background.png"));
            headUp = ImageIO.read(new File("src/Graficos/Assets/NyanHeadUp.png"));
            headDown = ImageIO.read(new File("src/Graficos/Assets/NyanHeadDown.png"));
            headLeft = ImageIO.read(new File("src/Graficos/Assets/NyanHeadLeft.png"));
            headRight = ImageIO.read(new File("src/Graficos/Assets/NyanHeadRight.png"));
            bodyVert = ImageIO.read(new File("src/Graficos/Assets/NyanRainbow.png"));
            bodyHorz = ImageIO.read(new File("src/Graficos/Assets/NyanRainbow.png"));
        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
        s = new Snake();
        addKeyListener(new MovementListener(s));
    }

}
