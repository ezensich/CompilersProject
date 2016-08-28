import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParserTest {
	
	public static void main(String argv[]) throws IOException {

		String current = new java.io.File( "." ).getCanonicalPath();
		
		String archivo = current+"/prueba.txt";
	      try {
	    	  BufferedReader buffer = new BufferedReader(new FileReader(archivo));
	    	  
	        System.out.println("Parsing "+archivo);
	        lexer s = new lexer(buffer);
	        @SuppressWarnings("deprecation")
			parser p = new parser(s);
	        p.parse();
	        
	        System.out.println("No errors.");
	      }
	      catch (Exception e) {
	        e.printStackTrace(System.out);
	        System.exit(1);
	      }
	    }
	  
	
}
