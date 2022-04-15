package Ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {

    private static BufferedImage engranajeMenu;

    public static void dibujaMenu(Graphics2D drawer){
        drawer.setColor(Color.orange);
        int anchomid = (Juego.ANCHOFINAL-200)/2;
        drawer.setFont(new Font("Serif",Font.BOLD,40));
        drawer.drawString("NYANSNAKE", anchomid-27, (Juego.ALTOFINAL/8)+50);
        drawer.setColor(Color.green);
        drawer.setFont(new Font("Serif",Font.BOLD,24));
        drawer.drawRect(anchomid, Juego.ALTOFINAL*3/8, 200, Juego.ALTOFINAL/8);
        drawer.drawString("JUGAR", anchomid+55, (Juego.ALTOFINAL*3/8)+46);
        drawer.setColor(new Color(221,160,221));
        drawer.drawRect(anchomid, Juego.ALTOFINAL*5/8, 200, Juego.ALTOFINAL/8);
        drawer.drawString("SALIR", anchomid+60, (Juego.ALTOFINAL*5/8)+46);
        drawer.setColor(Color.white);
        drawer.drawString("Best Score: " + Juego.bestScore, anchomid+30, Juego.ALTOFINAL*7/8);
        try {
            engranajeMenu = ImageIO.read(new File("src/Graficos/Assets/Engine.png"));
            drawer.drawImage(engranajeMenu,0,Juego.ALTOFINAL-32, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
