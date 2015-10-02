package cse3341p2;

import java.io.IOException;
import java.util.Scanner;

public class Interpreter {
	
	public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        String file = reader.nextLine();
        String input = reader.nextLine();
        
        // From 2 input text files, tokenizes from first file, then creates parse tree from tokens
        // which is used for the printer and executor. The second file contains the input data
        // which is used for the executor to read and write values to print the execution of
        // the program
        Tokenizer to = new Tokenizer(file);
        ParseTreeObject pa = Parser.parseProg(to);
        String outputPrettyPrint = Printer.printProg(pa);
        String outputValues = Executor.execProg(pa, input);
        
        System.out.print("Input program of core grammar in pretty print format:\n");
        System.out.print("-------------------------------------------------*\n");
        System.out.print(outputPrettyPrint);
        System.out.print("-------------------------------------------------*\n");
        System.out.print("Execution of program results in values:\n");
        System.out.print(outputValues);
        System.out.print("-------------------------------------------------*\n");
        
        reader.close();
    }
}
