package cse3341p2;

public class Printer {
	static int indentLevel = 3;
	
	// Print program return string which is program in pretty print format
	public static String printProg(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		string.append("program\n");
		string.append(printDeclSeq(p.children.get(0), indentLevel));
		string.append("begin\n");
		string.append(printStmtSeq(p.children.get(1), indentLevel));
		string.append("end\n");
		return string.toString();
	}
	
	// Print declaration sequence return string
	public static String printDeclSeq(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		string.append(printDecl(p.children.get(0), indent));
		if (p.children.size() > 1) { string.append(printDeclSeq(p.children.get(1), indent)); }
		return string.toString();
	}
	
	// Print statement sequence return string
	public static String printStmtSeq(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		string.append(printStmt(p.children.get(0), indent));
		if (p.children.size() > 1) { string.append(printStmtSeq(p.children.get(1), indent)); }
		return string.toString();
	}
	
	// Print declaration return string
	public static String printDecl(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("int ");
		string.append(printIdList(p.children.get(0)));
		string.append(";\n");
		return string.toString();
	}
	
	// Print id list return string
	public static String printIdList(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		if (p.children.size() > 0) {
			string.append(p.children.get(0).content);
		}
		for (int i = 1; i < p.children.size(); i++) {
			string.append(", ");
			string.append(printIdList(p.children.get(1)));
		}
		return string.toString();
	}
	
	// Print statement return string
	public static String printStmt(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		if (p.children.get(0).type == TreeType.ASSIGN) { string.append(printAssign(p.children.get(0), indent)); }
		else if (p.children.get(0).type == TreeType.IF) { string.append(printIf(p.children.get(0), indent)); }			
		else if (p.children.get(0).type == TreeType.WHILE) { string.append(printWhile(p.children.get(0), indent)); }		
		else if (p.children.get(0).type == TreeType.INPUT) { string.append(printInput(p.children.get(0), indent)); }			
		else if (p.children.get(0).type == TreeType.OUTPUT) { string.append(printOutput(p.children.get(0), indent)); }
		return string.toString();
	}
	
	// Print assign return string
	public static String printAssign(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append(p.children.get(0).content);
		string.append(" = ");
		string.append(printExp(p.children.get(1)));
		string.append(";\n");
		return string.toString();
	}
	
	// Print if return string
	public static String printIf(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("if");
		string.append(printCond(p.children.get(0)));
		string.append("then\n");
		string.append(printStmtSeq(p.children.get(1), indent + indentLevel));
		if (p.children.size() > 2) {
			for (int i = 0; i < indent; i++){
				string.append(" ");
			}
			string.append("else\n");
			string.append(printStmtSeq(p.children.get(2), indent + indentLevel));
		}
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("end;\n");
		return string.toString();
	}
	
	// Print while return string
	public static String printWhile(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("while");
		string.append(printCond(p.children.get(0)));
		string.append("loop\n");
		string.append(printStmtSeq(p.children.get(1), indent + indentLevel));
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("end;\n");
		return string.toString();
	}
	
	// Print input return string
	public static String printInput(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("read ");
		string.append(printIdList(p.children.get(0)));
		string.append(";\n");
		return string.toString();
	}
	
	// Print output return string
	public static String printOutput(ParseTreeObject p, int indent) {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < indent; i++){
			string.append(" ");
		}
		string.append("write ");
		string.append(printIdList(p.children.get(0)));
		string.append(";\n");
		return string.toString();
	}
	
	// Print condition return string
	public static String printCond(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		if (p.children.size() == 1) {
			string.append(printComp(p.children.get(0)));
		} else if (p.children.size() == 2) {
			string.append(" !");
			string.append(printCond(p.children.get(1)));
		} else if (p.children.size() == 3) {
			string.append(" [ ");
			string.append(printCond(p.children.get(0)));
			if (p.children.get(1).type == TreeType.AND) { string.append("&&"); } 
			else { string.append("||"); }
			string.append(printCond(p.children.get(2)));
			string.append(" ] ");
		}
		return string.toString();
	}
	
	// Print comparison return string
	public static String printComp(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		string.append(" ( ");
		string.append(printOp(p.children.get(0)));
		string.append(" ");
		string.append(p.children.get(1).content);
		string.append(" ");
		string.append(printOp(p.children.get(2)));
		string.append(" ) ");
		return string.toString();
	}
	
	// Print expression return string
	public static String printExp(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		string.append(printFac(p.children.get(0)));
		if (p.children.size() > 1) {
			if (p.children.get(1).type == TreeType.PLUS) { string.append(" + "); } 
			else { string.append(" - "); }
			string.append(printExp(p.children.get(2)));
		}
		return string.toString();
	}
	
	// Print factor return string
	public static String printFac(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		string.append(printOp(p.children.get(0)));
		if (p.children.size() > 1) {
			string.append(" * ");
			string.append(printFac(p.children.get(2)));
		}
		return string.toString();
	}
	
	// Print operation return string
	public static String printOp(ParseTreeObject p) {
		StringBuilder string = new StringBuilder();
		if (p.type == TreeType.VAL) { string.append(p.content); }
		else if (p.type == TreeType.ID) { string.append(p.content); }
		else if (p.children.get(0).type == TreeType.VAL) { string.append(p.children.get(0).content);}
		else if (p.children.get(0).type == TreeType.ID) { string.append(p.children.get(0).content); }
		else {
			string.append(" ( ");
			string.append(printExp(p.children.get(0)));
			string.append(" ) ");
		}
		return string.toString();
	}
}
