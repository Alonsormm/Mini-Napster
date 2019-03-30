import java.io.File;
import java.util.ArrayList;

public class Cliente implements LeeRed{
	Red r;
	public Cliente(){
		r = new Red(this);
		String temp = "Puto el que lo lea";
		File folder = new File("Music");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> lista = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile())
		   lista.add(listOfFiles[i].getName());
		}
		r.escribeRed(lista);
	}
	public static void main(String[] ards){
		new Cliente();
	}
	public void leeRed(Object obj){
		
	}

}
