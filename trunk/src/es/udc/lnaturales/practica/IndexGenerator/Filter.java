package es.udc.lnaturales.practica.IndexGenerator;

public class Filter {
	
	public static String textFilter(String text){
		
		text =  (text.replace(".-", ".").replace("\n", "").replace("   ", ""));
		return text.substring(0,text.lastIndexOf(".")+1).trim();
		
	}
	
	public static String titleFilter(String id){
		
		return (id.replace("\n", "").replace("  ", "").replace("...", "")).substring(1).trim() + ". ";
		
		
	}

}
