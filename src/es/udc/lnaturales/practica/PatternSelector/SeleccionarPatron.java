package es.udc.lnaturales.practica.PatternSelector;

import java.util.*;

import es.udc.lnaturales.practica.util.Dictionary;

public class SeleccionarPatron{
	
	private static HashMap<String, Dictionary> preguntas;
	
	static{
		preguntas = new HashMap<String, Dictionary>();
		preguntas.put("cuál", Dictionary.NOMBRE_PROPIO);
		preguntas.put("cuáles", Dictionary.NOMBRE_PROPIO);
		preguntas.put("qué", Dictionary.NOMBRE_PROPIO);
		preguntas.put("cómo", Dictionary.NOMBRE_PROPIO);
		preguntas.put("cuándo", Dictionary.FECHA);
		preguntas.put("cuántos", Dictionary.NUMERAL);
		preguntas.put("cuántas", Dictionary.NUMERAL);
		preguntas.put("dónde", Dictionary.NOMBRE_PROPIO);
		preguntas.put("quién", Dictionary.NOMBRE_PROPIO);
		preguntas.put("quiénes", Dictionary.NOMBRE_PROPIO);
		preguntas.put("a qué", Dictionary.NOMBRE_PROPIO);
		preguntas.put("en qué año", Dictionary.FECHA);
		preguntas.put("en qué", Dictionary.NOMBRE_PROPIO);
		preguntas.put("sobre qué", Dictionary.NOMBRE_PROPIO);
		preguntas.put("a partir de qué", Dictionary.FRASE);
		preguntas.put("ar el nombre de", Dictionary.NOMBRE_PROPIO);
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
	
}