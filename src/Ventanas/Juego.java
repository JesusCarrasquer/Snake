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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Yisus
 */
public class Juego extends Canvas implements Runnable {

    //VARIABES DE TAMAÑO DE PANTALLA Y ESCALADO
    private final int ANCHO = 400;
    private final int ALTO = (ANCHO / 12) * 9;
    private final int ESCALADO = 2;

    //THREAD VARIABLES
    private boolean arrancado = false;
    private Thread hiloEjecucion;
    
    //BUFFER FONDO
    BufferedImage fondo; // se inicializa en el constructor
    
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
        
        g.drawImage(fondo,0,0, getWidth(), getHeight(), this);
        
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
        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
