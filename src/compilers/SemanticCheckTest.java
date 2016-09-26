package compilers;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.filechooser.FileNameExtensionFilter;

import compilers.ast.Program;
import compilers.ast.enumerated_types.GenericType;
import compilers.semcheck.BreakContinueASTVisitor;
import compilers.semcheck.CheckTypesASTVisitor;
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
					BufferedReader buffer = new BufferedReader(new FileReader(filePath));
					System.out.println("Compiling " + filePath);
					lexer s = new lexer(buffer);
					@SuppressWarnings("deprecation")
					parser p = new parser(s);
					p.parse();
					
					//obtengo el AST
					Program prog = p.getAST();
					
					//Muestro por pantalla el AST a traves de un visitor
					PrintASTVisitor printAST = new PrintASTVisitor();
					String astString = printAST.visit(prog);
					//System.out.println(astString);
					
					
					//visitors para el chequeo semantico
					CheckTypesASTVisitor checkTypesAST = new CheckTypesASTVisitor();
					GenericType checkType = checkTypesAST.visit(prog);
					
					BreakContinueASTVisitor bcAST = new BreakContinueASTVisitor();
					String breakContinue = bcAST.visit(prog);
					
					
					
					System.out.println("Errores: "+checkTypesAST.getErrorList().toString()+" "+bcAST.getErrorList().toString()+" ");

				} catch (Exception e) {
					e.printStackTrace(System.out);
					System.exit(1);
				}
			//}
			System.out.println("Compilation finished successfully. No errors.");
		//} else {
			//System.out.println("No file selected");
		//}
	}

}
