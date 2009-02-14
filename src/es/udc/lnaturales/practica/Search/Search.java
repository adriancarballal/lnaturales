package es.udc.lnaturales.practica.Search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
		for (String clave : claves)	queryString+= clave.replaceAll("_", " AND ") + " AND ";
		queryString=queryString.trim().substring(0, queryString.length()-5);
		//System.out.println("QUERY: " + queryString);
		List <String> lista = new ArrayList <String>();
		
		try{
			IndexSearcher is = new IndexSearcher(index_path);
			QueryParser qp = new QueryParser("text", 
				new StandardAnalyzer());
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			qp.setUseOldRangeQuery(true);
			Query query = qp.parse(queryString);
			
			Hits hits = is.search(query, Sort.RELEVANCE);
			for (int i=0; i< hits.length(); i++) {
				Document doc = hits.doc(i);
				String[] phrases = new String(doc.get("text")).split("[.]");

				for(int j=0;j<phrases.length; j++){
					if(containsKeys(phrases[j], claves))
						lista.add(phrases[j].trim());
				}
			}
			hits.doc(0).get("id");
			return lista;
		}
		catch (Exception e) {
			return lista;
		}
	}
	
	private static boolean containsKeys(String phrase, List<String> keys){
		boolean flag = true;
		for (String key : keys) flag=flag&&phrase.contains(key.replaceAll("_", " "));
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

	private static void buscarResultadosParciales(List<String> claves, String phrase, 
			Dictionary tipo, Appearances apariciones){
		
		Translation t = new Translation();
		List<String> lexemas = new ArrayList<String>();
		List<Dictionary> tipos = new ArrayList<Dictionary>();
		t.codeTranslation(phrase, lexemas, tipos);
		
		for (int i = 0; i < lexemas.size(); i++) {
			if(tipos.get(i).equals(tipo)){
					if(!claves.contains(lexemas.get(i))){
						apariciones.addAppearance(lexemas.get(i));
					}
			}
		}
	}
	
	public static void main(String[] args) {
		List<String> l = new ArrayList<String>();
		
		l.add("compañía");
		l.add("Suiza");
		System.out.println("ENCONTRADOS: " +search(l).size());
		for (String string : search(l)) {
			System.out.println(string);
		}
	}

}
