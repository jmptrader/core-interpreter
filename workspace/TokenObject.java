package cse3341p2;

// Enum of each type of token in the Core Grammar
enum TokenType {
	PROGRAM, BEGIN, END, INT, IF, THEN, ELSE, WHILE, 
	LOOP, READ, WRITE, ID, VAL, AND, OR, LBRACKET, RBRACKET,
	LPARENTHESES, RPARENTHESES, PLUS, MINUS, MULT, NOTEQ, EQEQ,
	LESS, GREATER, LESSEQ, GREATEREQ, EXCL, EQ, COMMA, SEMICOLON, EOF
}

// Class of TokenObject that creates a token of a given string
public class TokenObject {
	public TokenType token;
	public String tokenString;
	
	// Constructor
	public TokenObject(TokenType token, String tokenString) {
		this.token = token;
		this.tokenString = tokenString;
	}
	
	// Creates a TokenObject given a word rather than a character
	public TokenObject(String tokenString) {
		String validIdentifier = "[A-Z][A-Z0-9]*";
		String validNumber = "-?[0-9]+";
		this.tokenString = tokenString;
		if (tokenString.equals("program")) { this.token = TokenType.PROGRAM; }
		else if (tokenString.equals("begin")) { this.token = TokenType.BEGIN; }
		else if (tokenString.equals("end")) { this.token = TokenType.END; }
		else if (tokenString.equals("int")) { this.token = TokenType.INT; }
		else if (tokenString.equals("if")) { this.token = TokenType.IF; }
		else if (tokenString.equals("then")) { this.token = TokenType.THEN; }
		else if (tokenString.equals("else")) { this.token = TokenType.ELSE; }
		else if (tokenString.equals("while")) { this.token = TokenType.WHILE; }
		else if (tokenString.equals("loop")) { this.token = TokenType.LOOP; }
		else if (tokenString.equals("read")) { this.token = TokenType.READ; }
		else if (tokenString.equals("write")) { this.token = TokenType.WRITE; }
		else if (tokenString.matches(validIdentifier)) { this.token = TokenType.ID; }
		else if (tokenString.matches(validNumber)) { this.token = TokenType.VAL; }
		else if (tokenString.equals("")) { this.token = TokenType.EOF; }
		else { System.out.println("Error: Not a valid token! Exiting program."); System.exit(0); }
	}
	
	// Given a token, gives a number based on the Core Grammar
	public int tokenPrintNum() {
		if (token.equals(TokenType.PROGRAM)) { return 1; }
		else if (token.equals(TokenType.BEGIN)) { return 2; }
		else if (token.equals(TokenType.END)) { return 3; }
		else if (token.equals(TokenType.INT)) { return 4; }
		else if (token.equals(TokenType.IF)) { return 5; }
		else if (token.equals(TokenType.THEN)) { return 6; }
		else if (token.equals(TokenType.ELSE)) { return 7; }
		else if (token.equals(TokenType.WHILE)) { return 8; }
		else if (token.equals(TokenType.LOOP)) { return 9; }
		else if (token.equals(TokenType.READ)) { return 10; }
		else if (token.equals(TokenType.WRITE)) { return 11; }
		else if (token.equals(TokenType.SEMICOLON)) { return 12; }
		else if (token.equals(TokenType.COMMA)) { return 13; }
		else if (token.equals(TokenType.EQ)) { return 14; }
		else if (token.equals(TokenType.EXCL)) { return 15; }
		else if (token.equals(TokenType.LBRACKET)) { return 16; }
		else if (token.equals(TokenType.RBRACKET)) { return 17; }
		else if (token.equals(TokenType.AND)) { return 18; }
		else if (token.equals(TokenType.OR)) { return 19; }
		else if (token.equals(TokenType.LPARENTHESES)) { return 20; }
		else if (token.equals(TokenType.RPARENTHESES)) { return 21; }
		else if (token.equals(TokenType.PLUS)) { return 22; }
		else if (token.equals(TokenType.MINUS)) { return 23; }
		else if (token.equals(TokenType.MULT)) { return 24; }
		else if (token.equals(TokenType.NOTEQ)) { return 25; }
		else if (token.equals(TokenType.EQEQ)) { return 26; }
		else if (token.equals(TokenType.LESS)) { return 27; }
		else if (token.equals(TokenType.GREATER)) { return 28; }
		else if (token.equals(TokenType.LESSEQ)) { return 29; }
		else if (token.equals(TokenType.GREATEREQ)) { return 30; }
		else if (token.equals(TokenType.VAL)) { return 31; }
		else if (token.equals(TokenType.ID)) { return 32; }
		else if (token.equals(TokenType.EOF)) { return 33; }
		else { return 0; }
	}
	
	// TokenObject as string
	public String toString () {
		return this.token.toString();
	}
}
