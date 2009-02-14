package es.udc.lnaturales.practica.PatronSelector;

import java.util.*;

import es.udc.lnaturales.practica.util.Dictionary;

public class SeleccionarPatron{
	
	private static HashMap<String, Dictionary> preguntas;
	
	static{
		preguntas = new HashMap<String, Dictionary>();
		preguntas.put("cu�l", Dictionary.NOMBRE);
		preguntas.put("cu�les", Dictionary.NOMBRE);
		preguntas.put("qu�", Dictionary.NOMBRE);
		//preguntas.put("c�mo", Dictionary.FRASE);
		preguntas.put("c�mo", Dictionary.NOMBRE);
		preguntas.put("cu�ndo", Dictionary.FECHA);
		preguntas.put("cu�ntos", Dictionary.NUMERAL);
		preguntas.put("cu�ntas", Dictionary.NUMERAL);
		preguntas.put("d�nde", Dictionary.NOMBRE);
		preguntas.put("qui�n", Dictionary.NOMBRE);
		preguntas.put("qui�nes", Dictionary.NOMBRE);
		preguntas.put("a qu�", Dictionary.NOMBRE);
		preguntas.put("en qu� a�o", Dictionary.FECHA);
		preguntas.put("en qu�", Dictionary.NOMBRE);
		preguntas.put("sobre qu�", Dictionary.NOMBRE);
		preguntas.put("a partir de qu�", Dictionary.FRASE);
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
		String pregunta1 = "�Qui�n fue Napoleon?";
		String pregunta2 = "�En que a�o murio Cristobal Colon?";
		String pregunta3 = "�Cu�l es el diminutivo de casa?";
		String pregunta4 = "�Qu� ha ocurrido antes?";
		HashMap tabla=crearTabla();
		devolverPatron(pregunta1, tabla);
		devolverPatron(pregunta2, tabla);
		devolverPatron(pregunta3, tabla);
		devolverPatron(pregunta4, tabla);
		
	}
*/	
}