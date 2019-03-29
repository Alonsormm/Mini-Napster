package test;

import java.io.File;
//ESte es un comentario para git
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class AudioTest {
public static void main(String[] args) {
    Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
    Format input2 = new AudioFormat(AudioFormat.MPEG);
    Format output = new AudioFormat(AudioFormat.LINEAR);
    PlugInManager.addPlugIn(
        "com.sun.media.codec.audio.mp3.JavaDecoder",
        new Format[]{input1, input2},
        new Format[]{output},
        PlugInManager.CODEC
    );
    try{
        Player player = Manager.createPlayer(new MediaLocator(new File("a.mp3").toURI().toURL()));
        player.start();
    }
    catch(Exception ex){
        ex.printStackTrace();
    }
}
}
