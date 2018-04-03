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
        		//Subida del Archivo
		if(args.length > 0){	
			//Arreglo de 16 bits para instrucciones
			char [] binArray = new char[16];
			String binValue;
			File newFile = new File(args[0]);
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
			try(Scanner fileScan = new Scanner(newFile);){
				String line = "";
				String hackFile = args[0].substring(0, args[0].indexOf(".asm")) + ".hack";
			    //Archivo  HACK Resultado
				File outFile = new File(hackFile);
				FileOutputStream streamOut = new FileOutputStream(outFile);
				BufferedWriter fileBuff = new BufferedWriter(new OutputStreamWriter(streamOut));	
				while(fileScan.hasNextLine()){
					line = fileScan.nextLine();
					line = line.trim();
					if(line.indexOf('/') != -1){
					line = line.substring(0,line.indexOf('/'));}
					//Instruccion A
					if ((line.length() > 0) && ((line.charAt(0) == '@') || (line.charAt(0)=='('))){
						if (line.charAt(0)=='('){
						}
						else{
						binArray[0] = '0';
						String subLine = line.substring(1);
						String numLine = aDict(subLine);
									
						binValue = decToBin(numLine);
						int count = 0;
						int index = 1;
						while(count < binValue.length()){
							binArray[index] = binValue.charAt(count);
							index++;
							count++;
						}
					 	for(int i=0; i < 16; i++){
							fileBuff.write(binArray[i]);	
						}
						fileBuff.newLine();
					}}
					else if((line.length() > 0) && ((line.indexOf('D')!=-1) || (line.indexOf('A')!=-1) || (line.indexOf('M')!=-1)
						//Instruccion C
						|| (line.indexOf('=')!=-1) || (line.indexOf(';')!=-1)) && (line.charAt(0) != '/') && (line.indexOf('(') == -1)){

						for(int i=0; i < 3; i++){
							binArray[i] = '1';
						}
						if (line.indexOf('=') > -1){
						
							binValue = assignInst(line);
							int count = 0;
							int index = 3;
							while(count < binValue.length()){
								binArray[index] = binValue.charAt(count);
								index++;
								count++;
							}
							for(int i=0; i<16; i++){
								fileBuff.write(binArray[i]);	
							}
							fileBuff.newLine();
						}
						else if(line.indexOf(';') > -1){
						//Instruccion Jump

							binValue = jumpInst(line);
							int count = 0;
							int index = 3;
							while (count < binValue.length()){
								binArray[index] = binValue.charAt(count);
								index++;
								count++;
							}
							for(int i=0; i<16; i++){
								fileBuff.write(binArray[i]);	
							}
							fileBuff.newLine();
						}
					}
					
				}fileBuff.close();
                                System.out.println("el archivo se convirtio con exito, porfavor revise la carpeta donde subio el archivo");
			
			}catch(IOException x){
				System.err.format("IOException: %s%n", x);
			}
		}
		else{
		System.out.println("No se subio ningun archivo");
	}
        
    }
    //Tabla de Simbolos
    public static String aDict(String aLine){
		String numALine = "";
		String R = "";

		switch(aLine.trim()){
		case "R0":
		case "SP": numALine = "0";
		break;
		case "R1":
		case "LCL": numALine = "1";
		break;
		case "R2":
		case "ARG": numALine = "2";
		break;
		case "R3":
		case "THIS": numALine = "3";
		break;
		case "R4":
		case "THAT": numALine = "4";
		break;
		case "R5": numALine = "5";
		break;
		case "R6": numALine = "6";
		break;
		case "R7": numALine = "7";
		break;
		case "R8": numALine = "8";
		break;
		case "R9": numALine = "9";
		break;
		case "R10": numALine = "10";
		break;
		case "R11": numALine = "11";
		break;
		case "R12": numALine = "12";
		break;
		case "R13": numALine = "13";
		break;
		case "R14": numALine = "14";
		break;
		case "R15": numALine = "15";
		break;
		case "SCREEN": numALine = "16384";
		break;
		case "KBD": numALine = "24576";
		break;
		default: numALine = "";
		}
	

	
		if(numALine == ""){
			if (Character.isLetter(aLine.charAt(0))){

	
				if (memMap.containsKey(aLine)){
							
					numALine = memMap.get(aLine);
				}
				else{
				
					memMap.put(aLine, Integer.toString(mem));
					mem++;
					numALine = memMap.get(aLine);
				}
			}
			else{
				numALine = aLine;
			}
		}
		
		return numALine; 
	}

    
}
