/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics2D;

import Listeners.MenuListener;
import Listeners.MovementListener;
import Snake.Comida;
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
    public static final int ANCHOFINAL = ANCHO*ESCALADO;
    public static final int ALTOFINAL = ALTO*ESCALADO;

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
    BufferedImage food;

    //LISTENERS
    MovementListener listenerJuego;
    MenuListener listenerMenu;

    //VARIABLE JUGADOR
    Snake s;

    //VARIABLE COMIDA
    Comida c;

    //SCORE
    private int scoreActual = 0;
    private int bestScore = 0;

    //ESTADO DEL JUEGO
    public enum STATE{
        MENU,JUEGO
    }

    private STATE gameState;

    public STATE getState(){
        return this.gameState;
    }

    public void setState(STATE e){
        gameState = e;
    }

    public void cambiaModo(){
        if(gameState == STATE.JUEGO){
            gameState = STATE.MENU;
            this.addMouseListener(listenerMenu);;
            this.removeKeyListener(listenerJuego);
        }
        else{
            
            s = new Snake();
            c = new Comida((int) (Math.random()*(ANCHOFINAL-100))+100,((int) (Math.random()*(ALTOFINAL-100)))+100);
            listenerJuego = new MovementListener(s);
            this.addKeyListener(listenerJuego);
            this.removeMouseListener(listenerMenu);
            gameState = STATE.JUEGO;
        }
    }
    
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
        final double frameRate = 15.0;
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
        if(gameState != STATE.JUEGO){
            return;
        }
        s.updateMovimientoNodos();
        s.updateSeguimientoNodos();
        s.updateAlive();
        if(!s.getAlive()){
            if(scoreActual > bestScore){
                bestScore = scoreActual;
            }
            scoreActual = 0;
            cambiaModo();
        }
        Nodo headNode = s.getListaNodos().get(0);
        if(c.compareTo(headNode)==0){
            s.creaNodo();
            scoreActual++;
            c = new Comida((int) (Math.random()*(ANCHOFINAL-100))+50,((int) (Math.random()*(ALTOFINAL-100)))+50);
        }
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
        Graphics2D drawer = (Graphics2D) g;
        //dibujando el juego
        if(gameState == STATE.JUEGO){
            dibujaSerpiente(drawer);
            //dibujando comida
            drawer.drawImage(food, c.getCoordX(), c.getCoordY(),null);
            drawer.setFont(new Font("Serif",Font.BOLD,40));
            drawer.setColor(Color.white);
            drawer.drawString("Score: " + scoreActual, (ANCHOFINAL-150)/2, ALTOFINAL/8);
        }
        else{
            dibujaMenu(drawer);
        }
        //TERMINA EL RENDER
        g.dispose();
        bs.show();
    }
    /**
     * Renderizado del menu
     * @param drawer
     */
    public void dibujaMenu(Graphics2D drawer){
        drawer.setColor(Color.orange);
        int anchomid = (ANCHOFINAL-200)/2;
        drawer.setFont(new Font("Serif",Font.BOLD,40));
        drawer.drawString("NYANSNAKE", anchomid-27, (ALTOFINAL/8)+50);
        drawer.setColor(Color.green);
        drawer.setFont(new Font("Serif",Font.BOLD,24));
        drawer.drawRect(anchomid, ALTOFINAL*3/8, 200, ALTOFINAL/8);
        drawer.drawString("JUGAR", anchomid+55, (ALTOFINAL*3/8)+46);
        drawer.setColor(new Color(221,160,221));
        drawer.drawRect(anchomid, ALTOFINAL*5/8, 200, ALTOFINAL/8);
        drawer.drawString("SALIR", anchomid+60, (ALTOFINAL*5/8)+46);
        drawer.setColor(Color.white);
        drawer.drawString("Best Score: " + bestScore, anchomid+30, ALTOFINAL*7/8);
    }

    /**
     * Metodo que dibuja la serpiente entera, en caso de que se esté jugando
     * @param drawer
     */
    public void dibujaSerpiente(Graphics2D drawer){
        ArrayList<Nodo> nodos = s.getListaNodos();
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
            if(nodos.get(i).getDireccion()==dir.NORTH ||  nodos.get(i).getDireccion()==dir.SOUTH){
                drawer.drawImage(bodyVert,nodos.get(i).getCoordX(),nodos.get(i).getCoordY(),null);
            }
            else{
                drawer.drawImage(bodyHorz,nodos.get(i).getCoordX(),nodos.get(i).getCoordY(),null);
            }
        }
    }

    /**
     * Constructor del canvas, donde toda la lógica del juego funciona
     */
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
            food = ImageIO.read(new File("src/Graficos/Assets/Food.png"));
            bodyVert = ImageIO.read(new File("src/Graficos/Assets/NyanRainbowVert.png"));
            bodyHorz = ImageIO.read(new File("src/Graficos/Assets/NyanRainbowHorz.png"));
        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
        gameState = STATE.MENU;
        listenerMenu = new MenuListener(this);
        addMouseListener(listenerMenu);
        //PLAYER SONIDO
        try {
            AudioInputStream au = AudioSystem.getAudioInputStream(new File("src/Graficos/MusicaEspacio.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(au);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl volumen = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = 0.05f;
            float deciBelios = (float) (Math.log(gain)/Math.log(10.0)*20);
            volumen.setValue(deciBelios);;
            clip.start();
        } catch (UnsupportedAudioFileException | IOException |LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
