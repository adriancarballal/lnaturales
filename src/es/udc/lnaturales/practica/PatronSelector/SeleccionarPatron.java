package es.udc.lnaturales.practica.PatronSelector;

import java.util.*;

public class SeleccionarPatron
{
	public enum Tipos_Sintacticos {Numeral, Sustantivo, Tiempo, Frase, SustLugar, SustPropio}
	
	public static HashMap crearTabla(){
		// Creamos la tabla hash con las diferentes preguntas
		
		HashMap<String, Tipos_Sintacticos> preguntas = 
			new HashMap<String, Tipos_Sintacticos>();			
		preguntas.put("�cu�l", Tipos_Sintacticos.Sustantivo);
		preguntas.put("�cu�les", Tipos_Sintacticos.Sustantivo);
		preguntas.put("�qu�", Tipos_Sintacticos.Sustantivo);
		preguntas.put("�c�mo", Tipos_Sintacticos.Frase);
		preguntas.put("�cu�ndo", Tipos_Sintacticos.Tiempo);
		preguntas.put("�cu�ntos", Tipos_Sintacticos.Numeral);
		preguntas.put("�cu�ntas", Tipos_Sintacticos.Numeral);
		preguntas.put("�d�nde", Tipos_Sintacticos.SustLugar);
		preguntas.put("�qui�n", Tipos_Sintacticos.SustPropio);
		preguntas.put("�qui�nes", Tipos_Sintacticos.SustPropio);
		preguntas.put("�a qu�", Tipos_Sintacticos.Frase);
		preguntas.put("�en qu�", Tipos_Sintacticos.Frase);
		preguntas.put("�sobre qu�", Tipos_Sintacticos.Frase);
		preguntas.put("�a partir de qu�", Tipos_Sintacticos.Frase);
		preguntas.put("dar el nombre de", Tipos_Sintacticos.SustPropio);
		
		// Faltan mas combinaciones con preposiciones...
		return preguntas;
	}
	
	public static String devolverPatron(String pregunta, HashMap tabla){
		String quo = pregunta.toLowerCase();	// Todo en minusculas		
		Set lista = tabla.keySet();				// Las claves de la tabla
		boolean finded = false;					// Si se encontro
		for (Iterator it = lista.iterator();it.hasNext();){
			Object actual = it.next();
			if (quo.startsWith(actual.toString())){
				finded=true;
				System.out.println("Pregunta: "+pregunta);
				System.out.println("Encontrado: "+tabla.get(actual.toString()));
			}
			else if ((!it.hasNext())&&(finded==false)){
				System.out.println("Pregunta: "+pregunta);
				System.out.println("No Encontrado: YOU FAIL!!!");
			}
		}
		return "fin";
	}
	
	public static void main(String[]args)
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
	
}