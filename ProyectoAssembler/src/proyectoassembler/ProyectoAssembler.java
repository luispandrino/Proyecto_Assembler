/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoassembler;

/**
 *
 * @author Luis Andrino
 */
import java.util.*;
import java.io.*;
public class ProyectoAssembler {

    /**
     * @param args the command line arguments
     */
    static HashMap <String, String> memMap = new HashMap <String, String>();
	static	int mem = 16;
	static int PCROM = 0;
	static String LineaPrevia = "";
    public static void main(String[] args) {
        // TODO code application logic here
        static HashMap <String, String> memMap = new HashMap <String, String>();
	static	int mem = 16;
	static int PCROM = 0;
	static String LineaPrevia = "";
	System.out.println("Ingrese la direccion del archivo");
	public static void main(String[] argv) {
		//Subida del Archivo
		if(argv.length > 0){	
			//Arreglo de 16 bits para instrucciones
			char [] binArray = new char[16];
			String binValue;
			File newFile = new File(argv[0]);
			try(Scanner bananaScan = new Scanner(newFile);){
				String baLine = "";
				String subLine = "";
				while(bananaScan.hasNextLine()){
					baLine = bananaScan.nextLine().trim();
					if ((baLine.length() > 0) && (baLine.charAt(0)=='(')){
							subLine = baLine.substring(1, baLine.length()-1);
							//Revisa si la sublinea es una palabra reservada
							memMap.put(subLine, Integer.toString(PCROM));
					}
	
					baLine = baLine.trim();
					if((baLine.length() > 0) && (baLine.charAt(0) != '/')&& (baLine.charAt(0) != '(')){
						PCROM++;
						LineaPrevia = baLine;
					}
				}	
			}catch(IOException x){
				System.err.format("IOException: %s%n", x);
			}
        
    }
    
}
}
