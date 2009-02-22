package es.udc.lnaturales.practica.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;

import es.udc.lnaturales.practica.IndexGenerator.DataSource;
import es.udc.lnaturales.practica.Transformation.Translation;
import es.udc.lnaturales.practica.util.Appearances;
import es.udc.lnaturales.practica.util.Dictionary;
public class Search {
	
	private static String index_path = DataSource.index_path;
	
	public static List<String> search(List<String> claves){
		
		String queryString = new String();
		String clave;
		queryString = claves.get(0);
		//System.out.println("Estas son las claves: "+queryString);
		for (int i=1; i<claves.size();i++){
			clave = claves.get(i);
			clave = transformarContraccion(clave);
			claves.set(i, clave);
			//System.out.println(" + "+claves.get(i));
			queryString+=" AND "+clave.replaceAll("_"," AND ");
		}
		queryString=queryString.trim();
		//System.out.println("QUERY: " + queryString);
		List <String> lista = new ArrayList <String>();
		
		try{
			IndexSearcher is = new IndexSearcher(index_path);
			QueryParser qp = new QueryParser("text", 
				new StandardAnalyzer());
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			qp.setUseOldRangeQuery(true);
			Query query = qp.parse(queryString);
//			System.out.println("query:_" + queryString + "_");
			Hits hits = is.search(query, Sort.RELEVANCE);
//			System.out.println("INITIAL HITS: " + hits.length());
			for (int i=0; i< hits.length(); i++) {
				Document doc = hits.doc(i);
				String[] phrases = new String(doc.get("text")).split("[.]");
//				System.out.println(doc.get("id"));
				for(int j=0;j<phrases.length; j++){
					if(containsKeys(phrases[j], claves)){
						lista.add(phrases[j].trim());
					}
				}
			}
//			for (int i=0; i< 1; i++) {
//				Document doc = hits.doc(i);
//				String[] phrases = new String(doc.get("text")).split("[.]");
//				for(int j=0;j<phrases.length; j++){
//					if(containsKeys(phrases[j], claves)){
//						lista.add(phrases[j].trim());
//					}
//				}
//			}

			
			
			hits.doc(0).get("id");
			return lista;
		}
		catch (Exception e) {
			return lista;
		}
	}
	
	private static boolean containsKeys(String phrase, List<String> keys){
		boolean flag = true;
		for (String key : keys){
//			System.out.println("FRASE: " + phrase);
//			System.out.println("claves: ");
//			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
//				System.out.print(iterator.next().toString() + " - ");
//				
//			}
			flag=flag&&phrase.toLowerCase().contains(key.toLowerCase().replaceAll("_", " "));
//			System.out.println();
//			System.out.println(flag);
		}
		return flag;
	}
	
	public static Appearances calcularRespuesta(Dictionary tipo, List<String> phrases, List<String> claves){
		Appearances apariciones = new Appearances();
		String phrase;
		for (int i = 0; i < phrases.size(); i++) {
			phrase = phrases.get(i);
			buscarResultadosParciales(claves, phrase, tipo, apariciones);
		}
		return apariciones;

	}
	
	private static String transformarContraccion(String cadena){
		cadena=cadena.replaceAll("_de_el_", " del ");
		cadena=cadena.replaceAll("_a_el_", " al ");
		return cadena;
	}

	private static void buscarResultadosParciales(List<String> claves, String phrase, 
			Dictionary tipo, Appearances apariciones){
		
		Translation t = new Translation();
		List<String> lexemas = new ArrayList<String>();
		List<Dictionary> tipos = new ArrayList<Dictionary>();
		List<Dictionary> tiposEspecificos = new ArrayList<Dictionary>();
		t.codeTranslation(phrase, lexemas, tipos, tiposEspecificos);
		for (int i = 0; i < lexemas.size(); i++) {
			if(tiposEspecificos.get(i).equals(tipo)){
					if(!claves.contains(lexemas.get(i))){
						apariciones.addAppearance(lexemas.get(i));
					}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			IndexSearcher is = new IndexSearcher(DataSource.index_path);
			QueryParser qp = new QueryParser("text", 
					new StandardAnalyzer());
			Query query = qp.parse("compañía AND Suiza");
			
			
			Hits hits = is.search(query, Sort.RELEVANCE);
			System.out.print(hits.length());
			Document doc = hits.doc(0);
			System.out.print(doc.get("id"));
			System.out.print(doc.get("text"));
			
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
