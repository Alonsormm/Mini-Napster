public class Cliente implements LeeRed{
	Red r;
	Reproductor p;
	public Cliente(){
		r = new Red(this);
		String temp = "GetBy.mp3";
		r.escribeRed(temp);
	}
	public static void main(String[] ards){
		new Cliente();
	}
	public void leeRed(Object obj){
		if(obj instanceof String){
			System.out.println((String)obj);
			p = new Reproductor((String)obj);
			p.play();
		}
	}

}
