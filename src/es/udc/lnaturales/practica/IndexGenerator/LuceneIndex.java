package es.udc.lnaturales.practica.IndexGenerator;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.NodeList;

public class LuceneIndex {
	
	
	private List<IndexInfo> info = new ArrayList<IndexInfo>();	
	
	public LuceneIndex(NodeList id, NodeList titles, NodeList text){
		
		for (int i = 0; i < id.getLength(); i++) {
			info.add(new IndexInfo(id.item(i).getTextContent(),
								   Filter.titleFilter(titles.item(i).getTextContent()) + 
							       Filter.textFilter(text.item(i).getTextContent())));
		}
	}

	public void generateLuceneIndex(String path){
		
		try {
			IndexWriter writer = new IndexWriter(path, new StandardAnalyzer(), false);
			Document doc;
			for(int i=0;i<info.size();i++){
				doc = new Document();
				doc.add(new Field("id", info.get(i).getId(), Field.Store.YES, Field.Index.NO));
				doc.add(new Field("text", info.get(i).getText(), Field.Store.YES, Field.Index.TOKENIZED));;
				writer.addDocument(doc);
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
