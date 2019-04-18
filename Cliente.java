import java.io.File;
import java.util.ArrayList;

public class Cliente implements LeeRed{
	Red r;
	ArrayList<String> songs;
	String ipAct;
	public Cliente(){
		r = new Red(this);
		ArrayList<String> lista = listaArchivos();
		r.escribeRed(lista);
		songs = new ArrayList<String>();
	}
	public ArrayList<String> listaArchivos(){
		File folder = new File("Music");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> lista = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile())
           lista.add(listOfFiles[i].getName());
        }
		return lista;
	}


	public static void main(String[] ards){
		new Cliente();
	}


	public void leeRed(Object obj){
		if(obj instanceof ArrayList){
			songs = (ArrayList<String>)obj;
		}
		else if(obj instanceof String){
			ipAct = (String)obj;
		}	
	}

}
