package es.udc.lnaturales.practica.Transformation.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class ProcessExecuter {
	
	private static final String FILE_FORMAT = "ISO8859-1";
	
	/**
	 * 
	 * @param cmd Comando a realizar en formato String[]
	 * @param logFileName String con la ruta al fichero de log
	 */
	public static void execute(String[] cmd, String logFileName) {
		OutputStreamWriter logWriter;
		try {
			logWriter = new OutputStreamWriter(new FileOutputStream(logFileName), FILE_FORMAT);
			if(cmd==null) throw new NoCommandAvailableException();
			
			Runtime rt = Runtime.getRuntime();
            java.lang.Process proc = rt.exec(cmd);
            
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), logWriter);
            
            StreamGobbler errorGobbler = new 
            StreamGobbler(proc.getErrorStream(), logWriter);
                
            outputGobbler.start();
            errorGobbler.start();
                                    
            if (proc.waitFor()==0)
            	logWriter.write("\n<DONE>\n");
            else{
            	logWriter.write("\n<UNDONE>\n");
            }		
            logWriter.flush();
            logWriter.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoCommandAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
