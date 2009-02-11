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

public class Search {
	
	//private String[] keys;
	private String index_path = DataSource.index_path;
	private String queryString;
	
	public Search(String[] keys){
		//this.keys = keys;		
		this.queryString = "capital AND croacia";
		//this.queryString = "kuwait AND 1990";
		//this.queryString = "habitantes AND Rusia";
	}
	
	public String[] execute(){		
		try {

			List <String> lista = new ArrayList <String>();
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
						lista.add(phrases[j]);
						System.out.println(phrases[j].trim());
					}
				}
			}
			System.out.println("RESULTS: " + lista.size());
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
		return null;
	}
	
	public static void main(String[] args) {
		
		Search s = new Search(null);
		s.execute();
	
	}

}
