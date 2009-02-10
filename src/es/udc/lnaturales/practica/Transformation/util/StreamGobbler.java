package es.udc.lnaturales.practica.Transformation.util;

import java.io.*;

public class StreamGobbler extends Thread
{
    InputStream is;
    OutputStreamWriter writer;
    
    
    public StreamGobbler(InputStream is, OutputStreamWriter writer)
    {
        this.is = is;
        this.writer = writer;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
            	writer.write(line + "\n");    
            } catch (IOException ioe){}
    }
}
