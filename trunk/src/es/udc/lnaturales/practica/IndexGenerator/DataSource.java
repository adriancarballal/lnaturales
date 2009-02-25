package es.udc.lnaturales.practica.IndexGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DataSource {

	private static String efe_path="C:\\efe94_100\\";
	public static String index_path="C:\\mi_indice";
	
	private static List<File> validFiles;
	public static int totalFiles;
	
	NodeList id = null; 
	NodeList title = null;
	NodeList text = null;
	
	static{

		File[] files = (new File(efe_path)).listFiles();
		validFiles = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			String file = files[i].getName();
			if (!file.endsWith(".dtd")){
				validFiles.add(files[i]);
			}
		}
		totalFiles = validFiles.size();
	}
	
	/**
	 * @param fileIndex Indice del fichero que se desea manipular
	 * @param id Conjunto de id's de las noticias EFE
	 * @param titles Conjunto de titulos de las noticias EFE
	 * @param text Conjunto de textos de las noticias EFE
	 * Hay que tener en cuenta que los tres ultimos tienen que 
	 * tener el mismo numero de elementos.
	 */
	private void getInfo(int fileIndex){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		try{
 
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(validFiles.get(fileIndex));
			id=null;title=null;text=null;
			id = document.getElementsByTagName("DOCID");
			title = document.getElementsByTagName("TITLE");
			text = document.getElementsByTagName("TEXT");
			
		}			
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	/**
	 * @param sourcePath Indica la ruta donde guardaremos el indice para nuestra
	 * informacion obtenido mediante Lucene de Apache.
	 */
	public void generateDataSource(){
		
		try {
			IndexWriter writer = new IndexWriter(index_path, new StandardAnalyzer(), true);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<validFiles.size();i++){
			this.getInfo(i);
			LuceneIndex indice = new LuceneIndex(id, title, text);
			indice.generateLuceneIndex(index_path);
			System.out.println("Completado " + (i+1) + " de " + validFiles.size());
		}
		System.out.println("Terminado!");
		
				
	}

}
