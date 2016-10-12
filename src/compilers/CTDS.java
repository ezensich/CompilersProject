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
import java.util.logging.Level;

import javax.sound.midi.SysexMessage;

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
			String pathFolderICG = current+"/icg";
			String extensionICG = ".icg";
			String canonicalFilePath;
			String filePath;

			CTDS.removeAllFiles(new File(pathFolderICG));
			
			for (int i = 0; i < argv.length; i++) {
				try {
					filePath =  "/" + argv[i];
					canonicalFilePath = current + filePath;
					BufferedReader buffer = new BufferedReader(new FileReader(canonicalFilePath));
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Compiling " + canonicalFilePath);

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
					List<String> codeList = new LinkedList<>();
					for(ICGInstruction inst : icgV.getInstructionCodeList()){
						codeList.add(inst.toString());
					}
					CTDS.writeFile(codeList, pathFolderICG, CTDS.getFileNameFromPath(filePath), extensionICG);
					
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
	/**
	 * 
	 * @param code Lista de instrucciones a guardar en el archivo
	 * @param folder Direccion de la carpeta donde se guardara el archivo
	 * @param filename Nombre del archivo a crear
	 * @param extensionFile Extension del archivo a crear
	 * @throws IOException
	 */
	private static void writeFile(List<String> code, String folder, String fileName, String extensionFile)
			throws IOException {
		//Creo la carpeta donde voy a guardar el archivo
		File f = new File(folder);
		f.mkdirs();
		//Creo el archivo
		File file = new File(f,fileName+extensionFile);
		file.createNewFile();
		 //Creo un objeto para escribir caracteres en el archivo
        FileWriter fw = new FileWriter(file);
        for (String instr : code) {
			fw.write(instr);
			fw.write("\n");
		}
        fw.close();
        
	}
	
	private static String getFileNameFromPath(String path){
		//saco el nombre de un archivo desde su ruta
		return path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
	}
	
	private static boolean removeAllFiles(File path) throws IOException{
		//elimino todo el contenido dentro de una carpeta
		if (path.exists()) {
			if (path.exists()) {
		        File[] files = path.listFiles();
		        for (int i = 0; i < files.length; i++) {
		            if (files[i].isDirectory()) {
		                removeAllFiles(files[i]);
		            } else {
		                files[i].delete();
		            }
		        }
		    }
	        return (path.delete());
		} else {
			System.err.println("El path "+path+" no es un directorio valido.");
			return false;
		}
	}

}
