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
	
	public static List<String> search(List<String> claves, List<String> documentos){
		
		documentos.clear();
		String queryString = new String();
		String clave;
		claves = transformarContraccion(claves,0);
		queryString = claves.get(0);
		for (int i=1; i<claves.size();i++){
			clave = claves.get(i);
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
			//System.out.println("TAMANO: "+hits.length());
			for (int i=0; i< hits.length(); i++) {
				Document doc = hits.doc(i);
				String[] phrases = new String(doc.get("text")).split("[.]");
				for(int j=0;j<phrases.length; j++){
					if(containsKeys(phrases[j], claves)){
						//System.out.println(phrases[j].trim() +"/"+hits.doc(i).get("id"));
						lista.add(phrases[j].trim());
						documentos.add(hits.doc(i).get("id"));
					}
				}
			}			
			
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
	
	public static Appearances calcularRespuesta(Dictionary tipo, List<String> frases, List<String> claves, List<String> documentos){
		Appearances apariciones = new Appearances();
		String frase;
		String documento;
		for (int i = 0; i < frases.size(); i++) {
			frase = frases.get(i);
			documento = documentos.get(i);
			buscarResultadosParciales(claves, frase, tipo, apariciones, documento);
		}
		return apariciones;

	}
	
	private static List<String> transformarContraccion(List<String> claves,int k){
		String cadena;
		for (int i=0; i<claves.size();i++){
			cadena = claves.get(i);
			if (k==0){
				cadena=cadena.replaceAll("_de_el_", " del ");
				cadena=cadena.replaceAll("_a_el_", " al ");
			}
			else{
				cadena=cadena.replaceAll(" del ", "_de_el_");
				cadena=cadena.replaceAll(" al ", "_a_el_");
			}
			claves.set(i, cadena); 
		}
		return claves;
	}

	private static void buscarResultadosParciales(List<String> claves, String frase, 
			Dictionary tipo, Appearances apariciones,String documento){
		
		Translation t = new Translation();
		List<String> lexemas = new ArrayList<String>();
		List<Dictionary> tipos = new ArrayList<Dictionary>();
		List<Dictionary> tiposEspecificos = new ArrayList<Dictionary>();
		t.codeTranslation(frase, lexemas, tipos, tiposEspecificos);
		for (int i = 0; i < lexemas.size(); i++) {
			if(tiposEspecificos.get(i).equals(tipo)){
				claves = transformarContraccion(claves,1);	
				if(!claves.contains(lexemas.get(i))){
						apariciones.addAppearance(lexemas.get(i),documento);
					}
			}
		}
	}
	
}
