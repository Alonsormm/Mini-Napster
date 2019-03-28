import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Reproductor{
	String nombre;
	Media archivo;
	MediaPlayer mp;
	public Reproductor(String n){
		nombre = n;
		archivo = new Media(new File(nombre).toURI().toString());
		mp = new MediaPlayer(archivo);	
	}
	public void play(){
		mp.play();
	}
	public static void main(String[] args){
		Reproductor p = new Reproductor("a.mp3");
		p.play();
	}
}
