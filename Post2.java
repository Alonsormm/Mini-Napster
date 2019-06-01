import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
//javac -cp .:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar Post2.java
//java -cp .:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar Post2
public class Post2 extends JLabel{
	public Boolean reproduciendo, pausado;
	JButton bc1, bc2; 
	Reproductor aTunesPlayer;
	public Post2(){
	    reproduciendo=false;  
	    pausado=false; 
		aTunesPlayer=new Reproductor(this);
	}

	public void reproducir(String nombre){
		accion(2,nombre);
	}

	public void pausa(){
		accion(3,"s");
	}
	
	public void accion(int n, String mp3){
		if (n == 2){
			System.out.println("Entro a algo");
			if (reproduciendo){
				try{
					aTunesPlayer.Pausa();
					reproduciendo = false;
					pausado = true;
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else{  
				System.out.println("Entro un poco mas");
				if (pausado){
					try{
						aTunesPlayer.Continuar();
						reproduciendo = true;
						pausado =false;
						System.out.println("Entro c");
					}catch(Exception xd){
							xd.printStackTrace();
					}
				} else{
					System.out.println("Entro donde quiero");
					try{					
	                    String songPath = mp3;                      
	                	try {
							aTunesPlayer.AbrirFichero(songPath);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						aTunesPlayer.Play();
						reproduciendo = true;
						System.out.println("play");
					}catch(Exception xd){
						System.out.println("Error "+xd);
						xd.printStackTrace();
					}
				}
			}
		}
		if (n == 3){
			try {
				System.out.println("stop");
				aTunesPlayer.Stop();
				reproduciendo=false;  
	                	pausado=false;  
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
