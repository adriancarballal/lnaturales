package es.udc.lnaturales.practica.Transformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Translation {

	private static final String FILE_FORMAT = "ISO8859-1";
	private static final String EXECUTE_PATH = "cmd.exe /C c:\\FreeLing\\bin\\analyzer.exe -f c:\\FreeLing\\share\\config\\es.cfg <c:\\naturales.txt";
	private static final String LOGFILE_PATH = "c:\\logFreeLing.txt";

	
	// Crea un fichero a partir de la lista de strings que se le pasa y usa dicho fichero
	// para ejecutar el analizador de FreeLing creando un fichero de log con los resultados del analisis
	private void executeAnalizer(List<String> list) {
		try {
			OutputStreamWriter fichero = new OutputStreamWriter(new FileOutputStream("c:\\naturales.txt"), FILE_FORMAT);
			
			for (int i=0;i<list.size();i++) 
				try {
					fichero.write(list.get(i)+"\n");
					//System.out.println(i+"." + list.get(i));
				} catch (IOException e) {
					e.printStackTrace();
				}
			try {
				fichero.flush();
				fichero.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ProcessExecuter execute = new ProcessExecuter();
		execute.execute(EXECUTE_PATH.split(" "), LOGFILE_PATH);
	} // fin executeAnalizer()
	
	// Codifica las palabras contenidas en la lista de strings en codigos procedentes
	// del fichero de log generado por FreeLing
	public List<String> codeTranslation(List<String> list) {
		this.executeAnalizer(list);
		String result[] = new String[1000];
		int k=0;
		try {
			FileReader fr;
			fr = new FileReader(LOGFILE_PATH);
			BufferedReader bf = new BufferedReader(fr);
			String sCadena;
			try {
				while ((sCadena = bf.readLine())!=null) {
					   if (sCadena.isEmpty()) {
						   break;
					   }
					   String code[] = sCadena.split(" ");
					   result[k]=code[2];
					   //System.out.println(result[k]+"->"+k);
					   k++;
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// se crea la lista de codigos...
		List<String> listCode = new ArrayList();
		
		for (int j=0; j<k;j++) {
		    listCode.add(result[j]);
		    //System.out.println("Lista"+j+" "+listCode.get(j));
		}
		return listCode;
	}

//	@SuppressWarnings("static-access")
//	public static void main(String[] args) {
//		
//		//String a[] = {"mesa","Burgos","cosa"};
//		List<String> fuente = new ArrayList();
//		List<String> codigo = new ArrayList();
//		fuente.add("mesa de barco");
//		fuente.add("Burgos es bonita");
//		fuente.add("cosa buena");
//		
//		Translation t = new Translation();
//		
//		codigo = t.codeTranslation(fuente);
//		for (int i=0;i<8;i++)
//		 System.out.println(codigo.get(i).toString());
//	}
}
