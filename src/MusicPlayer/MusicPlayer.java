package MusicPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private boolean activado;
    private int nivel;

    private Clip clip;
    private FloatControl volume;


    public MusicPlayer(){
        activado = true;
        nivel = 2;
        try {
            AudioInputStream audioIntro = AudioSystem.getAudioInputStream(new File("src/Graficos/MusicaEspacio.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioIntro);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float deciBelios = (float) (Math.log(0.2f)/Math.log(10.0)*20);
            volume.setValue(deciBelios);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    public void cambiaModo(){
        activado = !activado;
        if(!activado){
            stop();
        }else{
            play();
        }
    }

    public void setVolume(float v){
        float deciBelios = (float) (Math.log(v)/Math.log(10.0)*20);
        volume.setValue(deciBelios);
    }

    public boolean getActivado(){
        return activado;
    }

    public int getNivel(){
        return nivel;
    }

    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    
}
