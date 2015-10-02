package cse3341p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Executor {
	static List<ID> idList;
	static Scanner reader;
	
	//Class of ID/identifier to read and write values in the input data so that the executor can properly execute
	static class ID {
		String name;
		int value;
		
		// Constructor for ID class
		public ID(String content) {
			this.name = content;
		}
		
		// Changes value of ID
		public void changeTo(int value) {
			this.value = value;
		}
		
		// Returns value of chosen ID
		public int valueOf() {
			return this.value;
		}
		
		// Compares ID names
		public boolean equals(ID comparator) {
			return this.name.equals(comparator.name);
		}
		
	}
	
	// Executes program given input data file and parse tree returning string of executed values
	public static String execProg(ParseTreeObject p, String content) throws IOException {
		StringBuilder string = new StringBuilder();
		reader = new Scanner(new BufferedReader(new FileReader(content)));
		idList = new ArrayList<ID>();
		execDeclSeq(p.children.get(0));
		string.append(execStmtSeq(p.children.get(1)));
		return string.toString();
	}
	
	// Executes declaration sequence
	public static void execDeclSeq(ParseTreeObject p) {
		execDecl(p.children.get(0));
		if (p.children.size() > 1) {
			execDeclSeq(p.children.get(1));
		}
	}
	
	// Executes statement sequence return string
	public static String execStmtSeq(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		string.append(execStmt(p.children.get(0)));
		if (p.children.size() > 1) {
			string.append(execStmtSeq(p.children.get(1)));
		}
		return string.toString();
	}
	
	// Executes declaration
	public static void execDecl(ParseTreeObject p) {
		execIdList(p.children.get(0));
	}
	
	// Executes id list
	public static void execIdList(ParseTreeObject p) {
		execId(p.children.get(0));
		if (p.children.size() > 1) {
			execIdList(p.children.get(1));
		}
	}
	
	// Executes statement return string
	public static String execStmt(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		if (p.children.get(0).type == TreeType.ASSIGN) { execAssign(p.children.get(0)); }
		else if (p.children.get(0).type == TreeType.IF) { string.append(execIf(p.children.get(0))); }			
		else if (p.children.get(0).type == TreeType.WHILE) { string.append(execWhile(p.children.get(0))); }		
		else if (p.children.get(0).type == TreeType.INPUT) { execInput(p.children.get(0)); }			
		else if (p.children.get(0).type == TreeType.OUTPUT) { string.append(execOutput(p.children.get(0))); }
		return string.toString();
	}
	
	// Exectues assign for id to values using ID class
	public static void execAssign(ParseTreeObject p) {
		String content = p.children.get(0).content;
		boolean check = false;
		for (ID id : idList){
			if (content.equals(id.name)){
				check = true;
				id.changeTo(execExp(p.children.get(1)));
			}
		}
		if (!check) {
			System.out.println("Error: ID not assigned."); System.exit(0);
		}
	}
	
	// Executes if return string
	public static String execIf(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		if (execCond(p.children.get(0))) {
			string.append(execStmtSeq(p.children.get(1)));
		} else if (p.children.size() > 2) {
			string.append(execStmtSeq(p.children.get(2)));
		}
		return string.toString();
	}
	
	// Executes while return string
	public static String execWhile(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		while (execCond(p.children.get(0))) {
			string.append(execStmtSeq(p.children.get(1)));
		}
		return string.toString();
	}
	
	// Executes input to read values and assign to ids from list
	public static void execInput(ParseTreeObject p) {
		if (!reader.hasNext()) {
			System.out.println("Error: No input."); System.exit(0);
		}
		String nextVal = reader.next();
		boolean check = false;
		for(ID id : idList){
			if(id.name.equals(p.children.get(0).children.get(0).content)){
				check = true;
				id.changeTo(Integer.parseInt(nextVal));
			}
		}
		if (!check) {
			System.out.println("Error: ID not found."); System.exit(0);
		}
	}
	
	// Executes output to print id values from list to see how they were altered during execution return string
	public static String execOutput(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		boolean check = false;
		for(ID id : idList){	
			if(id.name.equals(p.children.get(0).children.get(0).content)){
				check = true;
				string.append(id.name + " = " + id.valueOf() + "\n");
			}
		}
		if (!check) {
			System.out.println("Error: ID not found."); System.exit(0);
		}
		return string.toString();
	}
	
	// Executes condition return boolean
	public static boolean execCond(ParseTreeObject p) {
		boolean check = false;
		if (p.children.get(0).type == TreeType.COMP) {
			check = execComp(p.children.get(0));
		} else if (p.children.get(0).type == TreeType.EXCL) {
			check = !execComp(p.children.get(1));
		} else {
			if (p.children.get(1).type == TreeType.AND) { check = execCond(p.children.get(0)) && execCond(p.children.get(2)); } 
			else { check = execCond(p.children.get(0)) || execCond(p.children.get(2)); }
		}
		return check;
	}
	
	// Executes comparison return boolean
	public static boolean execComp(ParseTreeObject p) {
		boolean check = false;
		String comparator = p.children.get(1).content;
		int firstOp = execOp(p.children.get(0));
		int secondOp = execOp(p.children.get(2));
		if (comparator.equals("!=")) { check = (firstOp != secondOp); }
		else if (comparator.equals("==")) { check = (firstOp == secondOp); }
		else if (comparator.equals("<")) { check = (firstOp < secondOp); }
		else if (comparator.equals(">")) { check = (firstOp > secondOp); }
		else if (comparator.equals("<=")) { check = (firstOp <= secondOp); }
		else if (comparator.equals(">=")) { check = (firstOp >= secondOp); }
		return check;
	}
	
	// Executes expression return int
	public static int execExp(ParseTreeObject p) {
		int value = 0;
		if (p.children.size() == 1) {
			value = execFac(p.children.get(0));
		} else {
			value = execFac(p.children.get(0));
			if (p.children.get(1).type == TreeType.PLUS){ value = value + execExp(p.children.get(2)); }
			else { value = value - execExp(p.children.get(2)); }
		}
		return value;
	}
	
	// Exectues factor return int
	public static int execFac(ParseTreeObject p) {
		int value = 0;
		if (p.children.size() == 1) {
			value = execOp(p.children.get(0));
		} else {
			value = execOp(p.children.get(0));
			if (p.children.size() > 1) {
				value = value * execFac(p.children.get(2));
			}
		}
		return value;
	}
	
	// Executes operation return int
	public static int execOp(ParseTreeObject p) {
		int value = 0;
		boolean check = false;
		if (p.children.get(0).type == TreeType.VAL){
			value = Integer.parseInt(p.children.get(0).content);
		} else if (p.children.get(0).type == TreeType.ID){
			for (ID id : idList){
				if (id.name.equals(p.children.get(0).content)){
					check = true;
					value = id.value;
				}
			}
			if (!check) {
				System.out.println("Error: ID not found."); System.exit(0);
			}
		} else {
			value = execExp(p.children.get(0));
		}
		return value;
	}
	
	// Executes Id which creates id from ID class and adds it to list
	public static void execId(ParseTreeObject p) {
		ID ID = new ID(p.content);
		idList.add(ID);
	}
}
