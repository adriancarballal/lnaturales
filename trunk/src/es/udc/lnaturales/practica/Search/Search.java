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
		String clave;
		queryString = claves.get(0);
		for (int i=1; i<claves.size();i++){
			clave = claves.get(i);
			clave = transformarContraccion(clave);
			claves.set(i, clave);
			queryString+=" AND "+clave.replaceAll("_"," AND ");
		}
		queryString=queryString.trim();
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
					if(containsKeys(phrases[j], claves)){
						lista.add(phrases[j].trim());
					}
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
		for (String key : keys){
			flag=flag&&phrase.toLowerCase().contains(key.toLowerCase().replaceAll("_", " "));
		}
		return flag;
	}
	
	public static Appearances calcularRespuesta(Dictionary tipo, List<String> frases, List<String> claves){
		Appearances apariciones = new Appearances();
		String frase;
		for (int i = 0; i < frases.size(); i++) {
			frase = frases.get(i);
			buscarResultadosParciales(claves, frase, tipo, apariciones);
		}
		return apariciones;

	}
	
	private static String transformarContraccion(String cadena){
		cadena=cadena.replaceAll("_de_el_", " del ");
		cadena=cadena.replaceAll("_a_el_", " al ");
		return cadena;
	}

	private static void buscarResultadosParciales(List<String> claves, String frase, 
			Dictionary tipo, Appearances apariciones){
		
		Translation t = new Translation();
		List<String> lexemas = new ArrayList<String>();
		List<Dictionary> tipos = new ArrayList<Dictionary>();
		List<Dictionary> tiposEspecificos = new ArrayList<Dictionary>();
		t.codeTranslation(frase, lexemas, tipos, tiposEspecificos);
		for (int i = 0; i < lexemas.size(); i++) {
			if(tiposEspecificos.get(i).equals(tipo)){
					if(!claves.contains(lexemas.get(i))){
						apariciones.addAppearance(lexemas.get(i));
					}
			}
		}
	}
	
}
