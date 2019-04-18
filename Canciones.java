import java.util.*;

public class Canciones{
	private Map <String,ArrayList<String>> ListaCanciones;
	private Map <String,ArrayList<String>> ListaIp;
	public Canciones(){
		ListaCanciones = new HashMap<String,ArrayList<String>>();
		ListaIp = new HashMap<String,ArrayList<String>>();
	}
	public void agregarCancion(String ip, String nombre){
		ArrayList <String> temp1 = new ArrayList<String>();
		if(ListaCanciones.containsKey(nombre))
			temp1 = ListaCanciones.get(nombre);
		temp1.add(ip);
        ListaCanciones.put(nombre,temp1);
		ArrayList <String> temp2 = new ArrayList<String>();
		if(ListaIp.containsKey(ip))
			temp2 = ListaIp.get(ip);
		temp2.add(nombre);
		ListaIp.put(ip,temp2);
	}
	public void quitarIp(String ip){
		ArrayList <String> tempIp = new ArrayList<String>();
		ArrayList <String> tempCanciones = new ArrayList<String>();
		if(!ListaIp.containsKey(ip))
			return;
		tempCanciones = ListaIp.get(ip);
		for(String i : tempCanciones){
			tempIp = ListaCanciones.get(i);
			tempCanciones.remove(ip);
		}
		ListaIp.remove(ip);
	}
	public String consultarIp(String nombre){
		ArrayList <String> temp = new ArrayList<String>();
		if(!ListaCanciones.containsKey(nombre))
			return "";
		return ListaCanciones.get(nombre).get(0);
	}
	public ArrayList<String> consultarCanciones(String ip){
		ArrayList <String> temp = new ArrayList<String>();
		if(!ListaIp.containsKey(ip))
			return temp;
		temp = ListaIp.get(ip);
		return temp;
	}
	public ArrayList<String> consultarTodasLasCanciones(){
		ArrayList <String> temp;
		temp = new ArrayList<String>(ListaCanciones.keySet());
		return temp;
	}
}
