package cse3341p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tokenizer {
	BufferedReader input;
	StringBuffer newWord;
	TokenObject token;
	String end = ";|,|\\[|\\]|\\(|\\)|\\+|-|\\*|=|<|>|\\|";
	int check;
	
	// Constructor for tokenizing
	public Tokenizer(String file) throws IOException {
		input = new BufferedReader(new FileReader(file));
		this.getToken();
	}
	
	// Gets the next token in the text
	public void getToken() throws IOException {
		int nextChar = input.read();
		while (Character.isWhitespace(nextChar) && nextChar != -1) {
			nextChar = input.read();
		}
		
		if (nextChar == -1) {
			token = new TokenObject(TokenType.EOF, "");
		} else {
			Character symbol = (char) nextChar;
			if (symbol == ';') { token = new TokenObject(TokenType.SEMICOLON, ";"); } 
			else if (symbol == ',') { token = new TokenObject(TokenType.COMMA, ","); }
			else if (symbol == '[') { token = new TokenObject(TokenType.LBRACKET, "["); }
			else if (symbol == ']') { token = new TokenObject(TokenType.RBRACKET, "]"); }
			else if (symbol == '(') { token = new TokenObject(TokenType.LPARENTHESES, "("); }
			else if (symbol == ')') { token = new TokenObject(TokenType.RPARENTHESES, ")"); }
			else if (symbol == '+') { token = new TokenObject(TokenType.PLUS, "+"); }
			else if (symbol == '-') { token = new TokenObject(TokenType.MINUS, "-"); }
			else if (symbol == '*') { token = new TokenObject(TokenType.MULT, "*"); }
			
			// Check for multiple symbols or a word
			else if (symbol == '=') {
				input.mark(1);
				check = input.read();
				if ((char) check == '=') {
					token = new TokenObject(TokenType.EQEQ, "==");
				} else {
					token = new TokenObject(TokenType.EQ, "=");
					input.reset();
				} }
			else if (symbol == '!') {
				input.mark(1);
				check = input.read();
				if ((char) check == '=') {
					token = new TokenObject(TokenType.NOTEQ, "!=");
				} else {
					token = new TokenObject(TokenType.EXCL, "!");
					input.reset();
				} }
			else if (symbol == '<') {
				input.mark(1);
				check = input.read();
				if ((char) check == '=') {
					token = new TokenObject(TokenType.LESSEQ, "<=");
				} else {
					token = new TokenObject(TokenType.LESS, "<");
					input.reset();
				} }
			else if (symbol == '>') {
				input.mark(1);
				check = input.read();
				if ((char) check == '=') {
					token = new TokenObject(TokenType.GREATEREQ, ">=");
				} else {
					token = new TokenObject(TokenType.GREATER, ">");
					input.reset();
				} }
			else if (symbol == '&') {
				input.mark(1);
				check = input.read();
				if ((char) check == '&') {
					token = new TokenObject(TokenType.AND, "&&");
				} }
			else if (symbol == '|') {
				input.mark(1);
				check = input.read();
				if ((char) check == '|') {
					token = new TokenObject(TokenType.OR, "||");
				} }
			else {
				boolean word = true;
				newWord = new StringBuffer();
				while (word) {
					newWord.append((char) nextChar);
					input.mark(1);
					nextChar = input.read();
					if (Character.isWhitespace(nextChar) || nextChar == -1 || Character.toString((char) nextChar).matches(end)) {
						word = !word;
					}
					if (!word) {
						input.reset();
					}
				}
				token = new TokenObject(newWord.toString());
			}
		}
	}
	
	// Uses the getToken method to grab the next token and ignore it
	public void skipToken() throws IOException {
		input.mark(1);
		int nextChar = input.read();
		if (nextChar == -1) { System.out.println("Error: Cannot skip EOF token!"); System.exit(0); }
		else {
			input.reset();
			this.getToken();
		}
	}
	
	// Returns the current token from the tokenizer
		public TokenObject currentToken() throws IOException {
			return token;
		}
	
}
