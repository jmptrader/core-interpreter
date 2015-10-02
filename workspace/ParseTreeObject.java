package cse3341p2;

import java.util.ArrayList;
import java.util.List;

//Enum of each type of token in the Core Grammar
enum TreeType {
	PROGRAM, DECLSEQ, DECL, STMTSEQ, STMT, IDLIST, ID,
	IF, COND, COMP, OP, WHILE, FACTOR, EXP, ASSIGN,
	INPUT, OUTPUT, PLUS, MINUS, MULT, AND, OR, EXCL,
	COMPOP, VAL
}

// Parse tree abstraction for use in the interpreter
public class ParseTreeObject {
	TreeType type;
	List<ParseTreeObject> children;
	String content;
	
	// Default parse tree object constructor
	public ParseTreeObject(TreeType type) {
		this.type = type;
		this.children = new ArrayList<ParseTreeObject>();
		this.content = "";
	}
	
	// Parse tree object constructor for types including values
	public ParseTreeObject(TreeType type, String content) {
		this.type = type;
		this.children = new ArrayList<ParseTreeObject>();
		this.content = content;
	}
	
	// Adds a child to the parse tree object
	public void addChild(ParseTreeObject node) {
		children.add(node);
	}
	
}
