package es.udc.lnaturales.practica.ER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.udc.lnaturales.practica.Search.Search;
import es.udc.lnaturales.practica.Transformation.Translation;
import es.udc.lnaturales.practica.util.Appearances;
import es.udc.lnaturales.practica.util.Dictionary;

public class Main {

	private static String questionFile_PATH = "C://questions.log";
	private static FileReader fr;
	private static HashMap<Integer, String> questions = new HashMap<Integer, String>();
	
	private static Dictionary buscado = Dictionary.NOMBRE;
	
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
		List<String> wordList = new ArrayList<String>(); 
		List<Dictionary> codeList = new ArrayList<Dictionary>();
		Translation t = new Translation();
		for (String string : questions.values()){
			System.out.print("¿" + string + "? ");
			long tiempoInicio = System.currentTimeMillis();
			t.codeTranslation(string, wordList, codeList);
			selectBuscados(wordList, codeList);
			Search.search(wordList);
			System.out.println(" +> DOCS: " + wordList.size());
			Appearances respuesta = 
				Search.calcularRespuesta(buscado, Search.search(wordList), wordList);
			System.out.println(respuesta.toString());
			long totalTiempo = System.currentTimeMillis() - tiempoInicio;
			System.out.println("El tiempo de repuesta es :" + totalTiempo/60000 + 
					" min (" + totalTiempo/1000 + "seg)");
		}
	}
	
	private static void selectBuscados(List<String> wordList, List<Dictionary> codeList){
		for (int i=codeList.size()-1;i>=0; i--) {
			if(!codeList.get(i).equals(buscado)){
				wordList.remove(i);
				codeList.remove(i);
			}
		}
	}

}
