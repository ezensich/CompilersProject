package compilers;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ParserTest {

	public static void main(String argv[]) throws IOException {

		if (argv.length != 0) {
			String current = new java.io.File(".").getCanonicalPath();
			String filePath;

			for (int i = 0; i < argv.length; i++) {
				try {
					filePath = current + "/" + argv[i];
					BufferedReader buffer = new BufferedReader(new FileReader(filePath));
					System.out.println("Parsing " + argv[i]);
					lexer s = new lexer(buffer);
					@SuppressWarnings("deprecation")
					parser p = new parser(s);
					p.parse();

				} catch (Exception e) {
					e.printStackTrace(System.out);
					System.exit(1);
				}
			}
			System.out.println("Compilation finished successfully. No errors.");
		} else {
			System.out.println("No file selected");
		}
	}

}
