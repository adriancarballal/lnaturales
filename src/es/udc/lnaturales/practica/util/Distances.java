package es.udc.lnaturales.practica.util;

import java.util.ArrayList;
import java.util.List;

public class Distances {

	/**
	 * 
	 * @param sentence : frase 
	 * @param wordKey : palabra con una posible respuesta
	 * @param reference : palabra referencia
	 * @return distancia minima entre ambas palabras dentro de la frase
	 */
	public int distanceBetweenWords(String sentence, String wordKey, String reference) {
		String words[] = sentence.split(" ");
		List reference_index = new ArrayList();
		List wordkey_index = new ArrayList();
		for (int i=0;i<words.length;i++) {
			System.out.println(words[i]);
			if (words[i].compareTo(wordKey)==0)
				wordkey_index.add(i);
			if (words[i].compareTo(reference)==0)
				reference_index.add(i);
		}
		int minus=0;
		int minimum=1000000;
		for (int i=0;i<wordkey_index.size();i++)
			for (int j=0; j<reference_index.size();j++) {
				minus = Math.abs((Integer)wordkey_index.get(i)- (Integer)reference_index.get(j));
				if (minus<=minimum)
					minimum=minus;
			}		
			
		return (int) (Math.pow(minimum, 2.0));
	}
	
//	@SuppressWarnings("static-access")
//	public static void main(String[] args) {
//		String frase="La capital es Zagreb y la capital de Yugoslavia es Belgrado";
//		String wordkey="Zagreb";
//		String reference="capital";
//		
//		Distances distances = new Distances();
//		int dist = distances.distanceBetweenWords(frase, wordkey, reference);
//		
//		System.out.println("La distancia es: "+dist);
//	}
}
