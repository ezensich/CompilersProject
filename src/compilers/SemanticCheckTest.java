package compilers;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.filechooser.FileNameExtensionFilter;

import ast.Program;
import compilers.semcheck.PrintASTVisitor;

public class SemanticCheckTest {

	public static void main(String argv[]) throws IOException {

		//if (argv.length != 0) {
			String current = new java.io.File(".").getCanonicalPath();
			String filePath;

			//for (int i = 0; i < argv.length; i++) {
				try {
					//filePath = current + "/" + argv[i];
					filePath = current + "/code_examples/ejemplo.ctds";
					System.out.println("path: "+filePath);
					BufferedReader buffer = new BufferedReader(new FileReader(filePath));
					//System.out.println("Parsing " + argv[i]);
					lexer s = new lexer(buffer);
					@SuppressWarnings("deprecation")
					parser p = new parser(s);
					p.parse();
					
					
					Program prog = p.getAST();
					
					PrintASTVisitor printAST = new PrintASTVisitor();
					String astString = printAST.visit(prog);
					System.out.println(astString);
					

				} catch (Exception e) {
					e.printStackTrace(System.out);
					System.exit(1);
				}
			//}
			System.out.println("Compilation finished successfully. No errors.");
		//} else {
			System.out.println("No file selected");
		//}
	}

}
