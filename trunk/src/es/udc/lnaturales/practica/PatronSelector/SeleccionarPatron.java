package es.udc.lnaturales.practica.PatronSelector;

import java.util.*;

import es.udc.lnaturales.practica.util.Dictionary;

public class SeleccionarPatron{
	
	private static HashMap<String, Dictionary> preguntas;
	
	static{
		preguntas = new HashMap<String, Dictionary>();
		preguntas.put("cuál", Dictionary.NOMBRE);
		preguntas.put("cuáles", Dictionary.NOMBRE);
		preguntas.put("qué", Dictionary.NOMBRE);
		//preguntas.put("cómo", Dictionary.FRASE);
		preguntas.put("cómo", Dictionary.NOMBRE);
		preguntas.put("cuándo", Dictionary.FECHA);
		preguntas.put("cuántos", Dictionary.NUMERAL);
		preguntas.put("cuántas", Dictionary.NUMERAL);
		preguntas.put("dónde", Dictionary.NOMBRE);
		preguntas.put("quién", Dictionary.NOMBRE);
		preguntas.put("quiénes", Dictionary.NOMBRE);
		preguntas.put("a qué", Dictionary.NOMBRE);
		preguntas.put("en qué año", Dictionary.FECHA);
		preguntas.put("en qué", Dictionary.NOMBRE);
		preguntas.put("sobre qué", Dictionary.NOMBRE);
		preguntas.put("a partir de qué", Dictionary.FRASE);
		preguntas.put("ar el nombre de", Dictionary.NOMBRE);
	}
	
	public static Dictionary devolverPatron(String pregunta){
		String question = pregunta.toLowerCase();	// Todo en minusculas		
		Set lista = preguntas.keySet();	    		// Las claves de la tabla
		for (Iterator it = lista.iterator();it.hasNext();){
			Object actual = it.next();
			if (question.startsWith(actual.toString())){
				return preguntas.get(actual);
			}
		}
		return Dictionary.DESCONOCIDO;
	}
	
	/*public static void main(String[]args)
	{
		// Le pasariamos las preguntas
		String pregunta1 = "¿Quién fue Napoleon?";
		String pregunta2 = "¿En que año murio Cristobal Colon?";
		String pregunta3 = "¿Cuál es el diminutivo de casa?";
		String pregunta4 = "¿Qué ha ocurrido antes?";
		HashMap tabla=crearTabla();
		devolverPatron(pregunta1, tabla);
		devolverPatron(pregunta2, tabla);
		devolverPatron(pregunta3, tabla);
		devolverPatron(pregunta4, tabla);
		
	}
*/	
}