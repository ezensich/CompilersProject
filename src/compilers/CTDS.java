package compilers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import compilers.lexical_syntactic_analysis.*;
import compilers.ast.Program;
import compilers.int_code_gen.ICGInstruction;
import compilers.int_code_gen.ICG_ASTVisitor;
import compilers.semcheck.BreakContinueASTVisitor;
import compilers.semcheck.CheckTypesASTVisitor;
import compilers.semcheck.PrintASTVisitor;
import compilers.semcheck.SetReferencesASTVisitor;

public class CTDS {

	public static void main(String argv[]) throws IOException {

		if (argv.length != 0) {
			String current = new java.io.File(".").getCanonicalPath();
			String filePath;

			for (int i = 0; i < argv.length; i++) {
				try {
					filePath = current + "/" + argv[i];
					BufferedReader buffer = new BufferedReader(new FileReader(filePath));
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Compiling " + filePath);

					// ------------------------------------------------------------------------
					// Chequeo Sintactico
					lexer s = new lexer(buffer);
					@SuppressWarnings("deprecation")
					parser p = new parser(s);
					p.parse();
					// Obtengo el AST
					Program prog = p.getAST();
					// Muestro por pantalla el AST a traves de un visitor
					PrintASTVisitor printAST = new PrintASTVisitor();
					String astString = printAST.visit(prog);
					// System.out.println(astString);

					// ----------------------------------------------------------------------
					// Chequeo semantico

					/* Seteo las referencias primero */
					SetReferencesASTVisitor setReferencesAST = new SetReferencesASTVisitor();
					setReferencesAST.visit(prog);

					/* compruebo los errores semanticos */
					List<String> errorList = new LinkedList<>();

					CheckTypesASTVisitor checkTypesAST = new CheckTypesASTVisitor();
					checkTypesAST.visit(prog);
					errorList.addAll(checkTypesAST.getErrorList());

					BreakContinueASTVisitor bcAST = new BreakContinueASTVisitor();
					bcAST.visit(prog);
					errorList.addAll(bcAST.getErrorList());
					System.out.print("Errores semanticos: ");
					if (errorList.isEmpty()) {
						System.out.println("- NO SE ENCONTRARON ERRORES -");
					} else {
						System.out.println();
						for (String error : errorList) {
							System.out.println("- " + error);
						}
					}

					// ----------------------------------------------------------
					// Generador de codigo intermedio
					ICG_ASTVisitor icgV = new ICG_ASTVisitor();
					icgV.visit(prog);
					System.out.println("\n ----- CODIGO INTERMEDIO GENERADO -----");
					for (ICGInstruction inst : icgV.getInstructionCodeList()) {
						System.out.println(inst.toString());
					}
					System.out.println();
					
					// -----------------------------------------------------------------------------------------

				} catch (Exception e) {
					e.printStackTrace(System.out);
					System.exit(1);
				}
			}
			System.out.println("Compilation finished successfully. No errors." + '\n');
		} else {
			System.out.println("No file selected");
		}
	}

	private static void writeFile(List<String> code, String folder, String fileName, String extensionFile)
			throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(folder + fileName + extensionFile)));
		for (String instr : code) {
			out.write(instr);
			out.write("\n");
		}
		out.close();
	}

}
