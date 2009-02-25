package es.udc.lnaturales.practica.ER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.udc.lnaturales.practica.PatternSelector.SeleccionarPatron;
import es.udc.lnaturales.practica.Search.Search;
import es.udc.lnaturales.practica.Transformation.Translation;
import es.udc.lnaturales.practica.util.Appearances;
import es.udc.lnaturales.practica.util.Dictionary;

public class Main {

	private static String questionFile_PATH = "d://questions2.log";
	private static FileReader fr;
	private static HashMap<Integer, String> questions = new HashMap<Integer, String>();
	
	static List<String> wordList = new ArrayList<String>(); 
	static List<String> documentos = new ArrayList<String>();
	static List<Dictionary> codeList = new ArrayList<Dictionary>();
	static List<Dictionary> especificList = new ArrayList<Dictionary>();
	static Translation t = new Translation();
	
	private static Dictionary buscado = Dictionary.DESCONOCIDO;
	
	private static int maxHits = 100;
	
	static{
		try {
			fr = new FileReader(questionFile_PATH);
			BufferedReader bf = new BufferedReader(fr);
			String question; String sCadena; int key; 
			while ((sCadena = bf.readLine())!=null) {
				if (sCadena.isEmpty()) break;
				key = new Integer(sCadena.substring(6, 10));
				question = sCadena.substring(12, sCadena.length()-1);
				questions.put(key, question);
			}
		} catch (Exception e){ e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		
		for (String string : questions.values()){
			buscado = SeleccionarPatron.devolverPatron(string);
			answerQuestion(string);
		}
	}
	
	private static void answerQuestion(String string){
		
		System.out.print("¿" + string + "? ");
		if(buscado.equals(Dictionary.DESCONOCIDO)){
			System.out.println("PATRON DE RESPUESTA NO ENCONTRADO...");
			return;
		}
		long tiempoInicio = System.currentTimeMillis();
		t.codeTranslation(string, wordList, codeList, especificList);
		
		selectBuscados(wordList, codeList);
		List<String> hits = Search.search(wordList, documentos);


		if(hits.size()==0){
			System.out.println("");
			System.out.println("1 plnaex031ms NIL");
			System.out.println("2 plnaex031ms NIL");
			System.out.println("3 plnaex031ms NIL");
			return;
		}
		if(hits.size()>maxHits){
			hits = hits.subList(0, maxHits);
			documentos = documentos.subList(0, maxHits);
		}
		//System.out.println("FRASES ENCONTRADAS: " + hits.size());
		Appearances respuesta = 
			Search.calcularRespuesta(buscado, hits, wordList, documentos);
		//System.out.println(respuesta.toString());
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("[" + totalTiempo/60000 + " min (" + totalTiempo/1000 + "seg)]");
		System.out.println(respuesta.toString());
	}
	
	private static void selectBuscados(List<String> wordList, List<Dictionary> codeList){
		for (int i=codeList.size()-1;i>=0; i--) {
			if(!(codeList.get(i).equals(Dictionary.NOMBRE))){
				wordList.remove(i);
				codeList.remove(i);
			}
		}
	}

}
