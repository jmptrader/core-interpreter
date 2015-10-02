package cse3341p2;

import java.io.IOException;

public class Parser {
	
	// Parses program to create parse tree object for use in printing and execution
	public static ParseTreeObject parseProg(Tokenizer t) throws IOException {
		ParseTreeObject progTree = new ParseTreeObject(TreeType.PROGRAM);
		if (t.currentToken().token != TokenType.PROGRAM) {
			System.out.println("Error: Token is not program!"); System.exit(0);
		}
		t.getToken();
		progTree.addChild(parseDeclSeq(t));
		if (t.currentToken().token != TokenType.BEGIN) {
			System.out.println("Error: Token is not begin!"); System.exit(0);
		}
		t.getToken();
		progTree.addChild(parseStmtSeq(t));
		if (t.currentToken().token != TokenType.END) {
			System.out.println("Error: Token is not end!"); System.exit(0);
		}
		t.getToken();
		if (t.currentToken().token != TokenType.EOF) {
			System.out.println("Error: Token is not EOF!"); System.exit(0);
		}
		return progTree;
	}
	
	// Parse declaration sequence return parse tree object
	public static ParseTreeObject parseDeclSeq(Tokenizer t) throws IOException {
		ParseTreeObject declseqTree = new ParseTreeObject(TreeType.DECLSEQ);
		if (t.currentToken().token != TokenType.INT) {
			System.out.println("Error: Token is not int!"); System.exit(0);
		}
		declseqTree.addChild(parseDecl(t));
		while (t.currentToken().token != TokenType.BEGIN) {
			declseqTree.addChild(parseDeclSeq(t));
		}
		return declseqTree;
	}
	
	// Parse statement sequence return parse tree object
	public static ParseTreeObject parseStmtSeq(Tokenizer t) throws IOException {
		ParseTreeObject stmtseqTree = new ParseTreeObject(TreeType.STMTSEQ);
		if( t.currentToken().token == TokenType.ID || 
			t.currentToken().token == TokenType.IF ||
			t.currentToken().token == TokenType.WHILE ||
			t.currentToken().token == TokenType.READ || 
			t.currentToken().token == TokenType.WRITE) {
			stmtseqTree.addChild(parseStmt(t));
		} else {
			System.out.println("Error: Token is not a valid statement!"); System.exit(0);
		}
		if( t.currentToken().token == TokenType.ID || 
			t.currentToken().token == TokenType.IF ||
			t.currentToken().token == TokenType.WHILE ||
			t.currentToken().token == TokenType.READ || 
			t.currentToken().token == TokenType.WRITE) {
			stmtseqTree.addChild(parseStmtSeq(t));
		}
		return stmtseqTree;
	}
	
	// Parse declaration return parse tree object
	public static ParseTreeObject parseDecl(Tokenizer t) throws IOException {
		ParseTreeObject declTree = new ParseTreeObject(TreeType.DECL);
		if (t.currentToken().token != TokenType.INT) {
			System.out.println("Error: Token is not int!"); System.exit(0);
		}
		t.getToken();
		declTree.addChild(parseIdList(t));
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return declTree;
	}
	
	// Parse id list return parse tree object
	public static ParseTreeObject parseIdList(Tokenizer t) throws IOException {
		ParseTreeObject idlistTree = new ParseTreeObject(TreeType.IDLIST);
		if (t.currentToken().token != TokenType.ID){
			System.out.println("Error: Token is not id!"); System.exit(0);
		}
		ParseTreeObject idTree = new ParseTreeObject(TreeType.ID, t.currentToken().tokenString);
		idlistTree.addChild(idTree);
		t.getToken();
		if (t.currentToken().token == TokenType.COMMA){
			t.getToken();
			idlistTree.addChild(parseIdList(t));
		}
		return idlistTree;
	}
	
	// Parse statement return parse tree object
	public static ParseTreeObject parseStmt(Tokenizer t) throws IOException {
		ParseTreeObject stmtTree = new ParseTreeObject(TreeType.STMT);
		if (t.currentToken().token==TokenType.ID){ stmtTree.addChild(parseAssign(t)); }
		else if (t.currentToken().token == TokenType.IF){ stmtTree.addChild(parseIf(t)); }
		else if (t.currentToken().token == TokenType.WHILE){ stmtTree.addChild(parseWhile(t)); }
		else if (t.currentToken().token == TokenType.READ){ stmtTree.addChild(parseInput(t)); }
		else if (t.currentToken().token == TokenType.WRITE){ stmtTree.addChild(parseOutput(t)); }
		else { System.out.println("Error: Token is not a valid statement!"); System.exit(0); }
		return stmtTree;
	}
	
	// Parse assign return parse tree object
	public static ParseTreeObject parseAssign(Tokenizer t) throws IOException {
		ParseTreeObject assignTree = new ParseTreeObject(TreeType.ASSIGN);
		if (t.currentToken().token != TokenType.ID){
			System.out.println("Error: Token is not id!"); System.exit(0);
		}
		ParseTreeObject idTree = new ParseTreeObject(TreeType.ID, t.newWord.toString());
		assignTree.addChild(idTree);
		t.getToken();
		if (t.currentToken().token != TokenType.EQ){
			System.out.println("Error: Token is not equal!"); System.exit(0);
		}
		t.getToken();
		assignTree.addChild(parseExp(t));
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return assignTree;
	}
	
	// Parse if return parse tree object
	public static ParseTreeObject parseIf(Tokenizer t) throws IOException {
		ParseTreeObject ifTree = new ParseTreeObject(TreeType.IF);
		if (t.currentToken().token != TokenType.IF){
			System.out.println("Error: Token is not if!"); System.exit(0);
		}
		t.getToken();
		ifTree.addChild(parseCond(t));
		if (t.currentToken().token != TokenType.THEN){
			System.out.println("Error: Token is not then!"); System.exit(0);
		}
		t.getToken();
		ifTree.addChild(parseStmtSeq(t));
		if (t.currentToken().token == TokenType.ELSE){
			t.getToken();
			ifTree.addChild(parseStmtSeq(t));
		}
		if (t.currentToken().token != TokenType.END){
			System.out.println("Error: Token is not end!"); System.exit(0);
		}
		t.getToken();
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return ifTree;
		
	}
	
	// Parse while return parse tree object
	public static ParseTreeObject parseWhile(Tokenizer t) throws IOException {
		ParseTreeObject whileTree = new ParseTreeObject(TreeType.WHILE);
		if (t.currentToken().token != TokenType.WHILE){
			System.out.println("Error: Token is not while!"); System.exit(0);
		}
		t.getToken();
		whileTree.addChild(parseCond(t));
		if (t.currentToken().token != TokenType.LOOP){
			System.out.println("Error: Token is not loop!"); System.exit(0);
		}
		t.getToken();
		whileTree.addChild(parseStmtSeq(t));
		if (t.currentToken().token != TokenType.END){
			System.out.println("Error: Token is not end!"); System.exit(0);
		}
		t.getToken();
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return whileTree;
	}
	
	// Parse input return parse tree object
	public static ParseTreeObject parseInput(Tokenizer t) throws IOException {
		ParseTreeObject inputTree = new ParseTreeObject(TreeType.INPUT);
		if (t.currentToken().token != TokenType.READ){
			System.out.println("Error: Token is not read!"); System.exit(0);
		}
		t.getToken();
		inputTree.addChild(parseIdList(t));
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return inputTree;
	}
	
	// Parse output return parse tree object
	public static ParseTreeObject parseOutput(Tokenizer t) throws IOException {
		ParseTreeObject outputTree = new ParseTreeObject(TreeType.OUTPUT);
		if (t.currentToken().token != TokenType.WRITE){
			System.out.println("Error: Token is not write!"); System.exit(0);
		}
		t.getToken();
		outputTree.addChild(parseIdList(t));
		if (t.currentToken().token != TokenType.SEMICOLON){
			System.out.println("Error: Token is not semicolon!"); System.exit(0);
		}
		t.getToken();
		return outputTree;
	}
	
	// Parse conditon return parse tree object
	public static ParseTreeObject parseCond(Tokenizer t) throws IOException {
		ParseTreeObject condTree = new ParseTreeObject(TreeType.COND);
		if(t.currentToken().token == TokenType.EXCL){
			ParseTreeObject noteeqTree = new ParseTreeObject(TreeType.EXCL);
			condTree.addChild(noteeqTree);
			t.getToken();
			condTree.addChild(parseCond(t));
		}else if(t.currentToken().token == TokenType.LBRACKET){
			t.getToken();
			condTree.addChild(parseCond(t));
			if(t.currentToken().token == TokenType.AND){
				ParseTreeObject andTree = new ParseTreeObject(TreeType.AND);
				condTree.addChild(andTree);
			} else if(t.currentToken().token == TokenType.OR){
				ParseTreeObject orTree = new ParseTreeObject(TreeType.OR);
				condTree.addChild(orTree);
			} else{
				System.out.println("Error: Token is not and/or!"); System.exit(0);
			}
			t.getToken();
			condTree.addChild(parseCond(t));
			if (t.currentToken().token != TokenType.RBRACKET){
				System.out.println("Error: Token is not right bracket!"); System.exit(0);
			}
			t.getToken();
		} else{
			condTree.addChild(parseComp(t));
		}
		return condTree;
	}	
	
	// Parse comparison return parse tree object
	public static ParseTreeObject parseComp(Tokenizer t) throws IOException {
		ParseTreeObject compTree = new ParseTreeObject(TreeType.COMP);			
		if (t.currentToken().token != TokenType.LPARENTHESES){
			System.out.println("Error: Token is not left parentheses!"); System.exit(0);
		}
		t.getToken();
		compTree.addChild(parseOp(t));
		if (t.currentToken().token != TokenType.NOTEQ && 
			t.currentToken().token != TokenType.EQEQ &&
			t.currentToken().token != TokenType.LESS &&
			t.currentToken().token != TokenType.GREATER &&
			t.currentToken().token != TokenType.LESSEQ &&
			t.currentToken().token != TokenType.GREATEREQ) {
			System.out.println("Error: Token is not valid expression!"); System.exit(0);
		}
		ParseTreeObject compopTree = new ParseTreeObject(TreeType.COMPOP, t.currentToken().tokenString);
		compTree.addChild(compopTree);
		t.getToken();
		compTree.addChild(parseOp(t));		
		if (t.currentToken().token != TokenType.RPARENTHESES){
			System.out.println("Error: Token is not right parentheses!"); System.exit(0);
		}
		t.getToken();
		return compTree;
	}
	
	// Parse expression return parse tree object
	public static ParseTreeObject parseExp(Tokenizer t) throws IOException {
		ParseTreeObject expTree = new ParseTreeObject(TreeType.EXP);
		expTree.addChild(parseFac(t));
		if (t.currentToken().token == TokenType.PLUS){
			ParseTreeObject plusTree = new ParseTreeObject(TreeType.PLUS);
			expTree.addChild(plusTree);
			t.getToken();
			expTree.addChild(parseExp(t));
		}else if (t.currentToken().token == TokenType.MINUS){
			ParseTreeObject minusTree = new ParseTreeObject(TreeType.MINUS);
			expTree.addChild(minusTree);
			t.getToken();
			expTree.addChild(parseExp(t));
		}
		return expTree;
	}
	
	// Parse factor return parse tree object
	public static ParseTreeObject parseFac(Tokenizer t) throws IOException {
		ParseTreeObject facTree = new ParseTreeObject(TreeType.FACTOR);
		facTree.addChild(parseOp(t));
		if (t.currentToken().token == TokenType.MULT){
			ParseTreeObject multTree = new ParseTreeObject(TreeType.MULT);
			facTree.addChild(multTree);
			t.getToken();
			facTree.addChild(parseFac(t));
		}
		return facTree;
	}
	
	// Parse operation return parse tree object
	public static ParseTreeObject parseOp(Tokenizer t) throws IOException {
		ParseTreeObject opTree = new ParseTreeObject(TreeType.OP);
		if (t.currentToken().token == TokenType.VAL){
			ParseTreeObject valTree = new ParseTreeObject(TreeType.VAL, t.currentToken().tokenString);
			opTree.addChild(valTree);
			t.getToken();
		} else if (t.currentToken().token == TokenType.ID){
			ParseTreeObject idTree = new ParseTreeObject(TreeType.ID, t.currentToken().tokenString);
			opTree.addChild(idTree);
			t.getToken();
		} else if (t.currentToken().token == TokenType.LPARENTHESES){
			t.getToken();
			opTree.addChild(parseExp(t));
			if(t.currentToken().token != TokenType.RPARENTHESES){
				System.out.println("Error: Token is not right parentheses!"); System.exit(0);
			}
			t.getToken();
		} else {
			System.out.println("Error: Token is not factor!"); System.exit(0);
		}
		return opTree;
	}
	
}
