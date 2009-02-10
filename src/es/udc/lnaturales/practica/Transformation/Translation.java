package es.udc.lnaturales.practica.Transformation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Translation {

	private static final String FILE_FORMAT = "ISO8859-1";
	private static final String EXECUTE_PATH = "c:\\FreeLing\\bin\\analyzer.exe -f c:\\FreeLing\\share\\config\\es.cfg <c:\\naturales.txt";
	private static final String LOGFILE_PATH = "c:\\logFreeLing.txt";
	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		String a[] = {"mesa","Burgos","cosa"};
		/********************************************************************/
		try {
			OutputStreamWriter fichero = new OutputStreamWriter(new FileOutputStream("c:\\naturales.txt"), FILE_FORMAT);
			
			for (int i=0;i<a.length;i++) 
				try {
					fichero.write(a[i]+"\n");
					System.out.println(i+"." + a[i]);
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
		System.out.println(EXECUTE_PATH);
		execute.execute(EXECUTE_PATH.split(" "), LOGFILE_PATH);
		/*********************************************************************/
		
		
	}

}
