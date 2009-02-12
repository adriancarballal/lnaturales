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
	
	private String index_path = DataSource.index_path;
	private String queryString;
	
	public Search(String[] keys){
		this.queryString = "capital AND croacia";
		//this.queryString = "kuwait AND 1990";
		//this.queryString = "habitantes AND Rusia";
	}
	
	public List<String> execute(){
		List <String> lista = new ArrayList <String>();
		try {
			IndexSearcher is = new IndexSearcher(this.index_path);
			QueryParser qp = new QueryParser("text", 
					new StandardAnalyzer());
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(queryString);
			
			Hits hits = is.search(query, Sort.RELEVANCE);
			for (int i=0; i< hits.length(); i++) {
				Document doc = hits.doc(i);
				String s = new String(doc.get("text"));

				String[] phrases = s.split("[.]");
				for(int j=0;j<phrases.length; j++){
					if(phrases[j].contains("capital") && phrases[j].contains("Croacia")){
						lista.add(phrases[j].trim());
					}
				}
			}
			System.out.println("DOCUMENTOS RELACIONADOS: " + lista.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	public static List<String> mock(){
		Search s = new Search(null);
		return s.execute();
	}
	
	public static void main(String[] args) {

		long tiempoInicio = System.currentTimeMillis();
		System.out.println("INICIANDO...");
		
		List<String> results = mock();
		Appearances apariciones = new Appearances();
		String phrase;

		List<String> claves = new ArrayList<String>();
		claves.add("capital");
		claves.add("Croacia");

		for (int i = 0; i < results.size(); i++) {
			
			phrase = results.get(i);
			Dictionary tipo = Dictionary.NOMBRE;
			
			buscarResultadosParciales(claves, phrase, tipo, apariciones);

		}
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("El tiempo de demora es :" + totalTiempo/1000 + " seg");
		System.out.println("El tiempo de demora es :" + totalTiempo/60000 + " min");
		System.out.println();
		System.out.println("TABLA Apariciones: ");
		System.out.println(apariciones.toString());
		
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
	

}
