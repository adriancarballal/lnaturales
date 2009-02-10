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
	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//String a[] = {"mesa","Burgos","cosa"};
		List<String> a = new ArrayList();
		a.add("mesa");
		a.add("Burgos");
		a.add("cosa");
		/********************************************************************/
		try {
			OutputStreamWriter fichero = new OutputStreamWriter(new FileOutputStream("c:\\naturales.txt"), FILE_FORMAT);
			
			for (int i=0;i<a.size();i++) 
				try {
					fichero.write(a.get(i)+"\n");
					System.out.println(i+"." + a.get(i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				fichero.flush();
				fichero.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessExecuter execute = new ProcessExecuter();
		execute.execute(EXECUTE_PATH.split(" "), LOGFILE_PATH);
		/*********************************************************************/
		
        String resultado[] = new String[1000];
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
					   String codigo[] = sCadena.split(" ");
					   resultado[k]=codigo[2].substring(0, 4);
					   System.out.println(resultado[k]+"->"+k);
					   k++;
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/******************************************************************/
		
		List<String> lista = new ArrayList();
		
		for (int j=0; j<k;j++) {
		    lista.add(resultado[j]);
		    System.out.println("Lista"+j+" "+lista.get(j));
		}
			
		

	}

}
