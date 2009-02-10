package es.udc.lnaturales.practica.IndexGenerator;

public class IndexInfo {
	
	public String id;
	public String text;
	
	public IndexInfo(String id, String text){
		
		this.id=id;
		this.text=text;
		
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

}
