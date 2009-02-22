package es.udc.lnaturales.practica.Transformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import es.udc.lnaturales.practica.util.Dictionary;

public class Translation {

	private static final String FILE_FORMAT = "ISO8859-1";
	private static final String EXECUTE_PATH = "cmd.exe /C C:\\FreeLing\\bin\\analyzer.exe -f c:\\FreeLing\\share\\config\\es.cfg <d:\\naturales.txt";
	private static final String LOGFILE_PATH = "d:\\logFreeLing.txt";

	
	// Crea un fichero a partir de un string que se le pasa y usa dicho fichero
	// para ejecutar el analizador de FreeLing creando un fichero de log con los resultados del analisis
	@SuppressWarnings("static-access")
	private void executeAnalizer(String phrase) {
		try {
			OutputStreamWriter fichero = new OutputStreamWriter(new FileOutputStream("d:\\naturales.txt"), FILE_FORMAT);
			 
			try {
				fichero.write(phrase+"\n");
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
	
	// Codifica las palabras contenidas en el string, en codigos procedentes
	// del fichero de log generado por FreeLing rellenando wordList con las palabras
	// y codeList con los codigos correspondientes
	public void codeTranslation(String sentence, List<String> wordList, 
			List<Dictionary> codeList, List<Dictionary> specific) {
		wordList.clear();
		codeList.clear();
		this.executeAnalizer(sentence);
		// se crea la lista de codigos...
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
					   wordList.add(code[0]);
					   if (code[2].charAt(0)=='N'){
					     codeList.add(Dictionary.NOMBRE);
					     if (code[2].charAt(1)=='P'){
					    	 specific.add(Dictionary.NOMBRE_PROPIO);
					     }
					     else specific.add(Dictionary.NOMBRE_COMUN);
					   }
					   else if (code[2].charAt(0)=='Z'){
						 codeList.add(Dictionary.NUMERAL);
						 specific.add(Dictionary.NUMERAL);
					   }
					   else if (code[2].charAt(0)=='W'){
						 codeList.add(Dictionary.FECHA);
						 specific.add(Dictionary.FECHA);
					   }
					   else if (code[2].charAt(0)=='A'){
							 codeList.add(Dictionary.ADJETIVO);
							 specific.add(Dictionary.ADJETIVO);
						   }
					   else{
						 codeList.add(Dictionary.DESCONOCIDO);
						 specific.add(Dictionary.DESCONOCIDO);
					   }
					  
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//String a[] = {"mesa","Burgos","cosa"};
		List<String> fuente = new ArrayList();
		List<Dictionary> codigo = new ArrayList();
		String frase="es la causa más frecuente de los accidentes de coche";
		int j = frase.split(" ").length;
		Translation t = new Translation();
		t.codeTranslation(frase,fuente,codigo);
		for (int i=0;i<j;i++)
		 if(codigo.get(i).equals(Dictionary.NOMBRE))
			 System.out.println(fuente.get(i).toString()+" "+codigo.get(i).toString());
	}*/

}
