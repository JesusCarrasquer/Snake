package Ventanas;

import javax.swing.JFrame;

/**
 * @author Jesus Carrasquer Gonzalvo
 */
public class Ventana extends JFrame {
    
    
    public Ventana(){
        super("Snake");
        this.getContentPane().setBackground(null);
        setUndecorated(true);
        Juego juego = new Juego();
        this.getContentPane().add(juego);
        this.pack();
        juego.comenzar();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    
}
