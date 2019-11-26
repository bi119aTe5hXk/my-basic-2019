package newlang3;

import java.io.*;
import java.io.FileReader;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	private PushbackReader reader;
	private static int nextchar;

	private static String LITERAL = "^\".*\"$";
	private static String NUM = "^[0-9]+\\.?[0-9]*$";
	private static String INT = "^[0-9]+$";
	private static String NAME = "^[a-zA-Z_][a-zA-Z0-9_]*$";
	private static String SPACE = "^[ \t]+$";

	private static Map<String, LexicalUnit> op = new HashMap<String, LexicalUnit>();
	private static Map<String, LexicalUnit> special = new HashMap<String, LexicalUnit>();

	static {
		op.put("DO", new LexicalUnit(LexicalType.DO));// not work
		op.put("WHILE", new LexicalUnit(LexicalType.WHILE));
		op.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
		op.put("LOOP", new LexicalUnit(LexicalType.LOOP));
		op.put("IF", new LexicalUnit(LexicalType.IF));
		op.put("THEN", new LexicalUnit(LexicalType.THEN));
		op.put("ELSE", new LexicalUnit(LexicalType.ELSE));
		op.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
		op.put("ENDIF", new LexicalUnit(LexicalType.ENDIF));
		op.put("FOR", new LexicalUnit(LexicalType.FOR));
		op.put("FORALL", new LexicalUnit(LexicalType.FORALL));
		op.put("NEXT", new LexicalUnit(LexicalType.NEXT));
		op.put("SUB", new LexicalUnit(LexicalType.SUB));
		op.put("FUNC", new LexicalUnit(LexicalType.FUNC));
		op.put("DIM", new LexicalUnit(LexicalType.DIM));
		op.put("AS", new LexicalUnit(LexicalType.AS));
		op.put("TO", new LexicalUnit(LexicalType.TO));
		op.put("WEND", new LexicalUnit(LexicalType.WEND));
		op.put("END", new LexicalUnit(LexicalType.END));
		op.put("DOT", new LexicalUnit(LexicalType.DOT));
		

		special.put("\n", new LexicalUnit(LexicalType.NL));
		special.put("\r", new LexicalUnit(LexicalType.NL));
		special.put("\r\n", new LexicalUnit(LexicalType.NL));
		special.put("+", new LexicalUnit(LexicalType.ADD));
		special.put("-", new LexicalUnit(LexicalType.SUB));
		special.put("*", new LexicalUnit(LexicalType.MUL));
		special.put("/", new LexicalUnit(LexicalType.DIV));
		special.put("=", new LexicalUnit(LexicalType.EQ));
		special.put("<", new LexicalUnit(LexicalType.LT));
		special.put(">", new LexicalUnit(LexicalType.GT));
		special.put("<=", new LexicalUnit(LexicalType.LE));
		special.put("=<", new LexicalUnit(LexicalType.LE));
		special.put(">=", new LexicalUnit(LexicalType.GE));
		special.put("=>", new LexicalUnit(LexicalType.GE));
		special.put("<>", new LexicalUnit(LexicalType.NE));
		special.put(")", new LexicalUnit(LexicalType.LP));
		special.put("(", new LexicalUnit(LexicalType.RP));
		special.put(",", new LexicalUnit(LexicalType.COMMA));
	}

	public LexicalAnalyzerImpl(String fname) throws Exception {
		FileReader fr = new FileReader(fname);
		reader = new PushbackReader(fr);
	}

	@Override
	public LexicalUnit get() throws IOException {
		int c = -1;
		do {
			c = reader.read();
		} while (String.valueOf((char) c).matches(SPACE));

		if (c == -1 || nextchar == -1) {
			return new LexicalUnit(LexicalType.EOF);
		}

		String target = String.valueOf((char) c);
		if (target.matches(NAME)) {
			return getName(target);
		} else if (target.matches("\"")) {
			return getLiteral(target);
		} else if (target.matches(INT)) {
			return getNumber(target);
		} else if (special.containsKey(target)) {
			return getSpecial(target);
		} else {
			System.err.println("Error: unknow char:" + (char) c);
			return null;
		}
	}

	private LexicalUnit getName(String target) throws IOException {
		while (true) {
			nextchar = reader.read();
			String str = String.valueOf((char) nextchar);
			if ((target + str).matches(NAME)) {
				target += str;
			} else {
				reader.unread(nextchar);
				break;
			}
		}
		LexicalUnit lu = op.get(target);
		//System.out.println("target is:" + target);
		//System.out.println("lu is:" + lu);
		if (lu == null) {
			return new LexicalUnit(LexicalType.NAME, new ValueImpl(target));
		}
		return lu;
	}

	private LexicalUnit getNumber(String target) throws IOException {
		while (true) {
			nextchar = reader.read();
			String str = String.valueOf((char) nextchar);
			if ((target + str).matches(NUM)) {
				target += str;
			} else {
				reader.unread(nextchar);
				break;
			}
		}
		if (target.matches(INT))
			return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(target, ValueType.INTEGER));
		else
			return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(target, ValueType.DOUBLE));

	}

	private LexicalUnit getLiteral(String target) throws IOException {
		while (true) {
			nextchar = reader.read();
			String str = String.valueOf((char) nextchar);
			if (nextchar == -1 || special.containsKey(str)) {
				System.err.println("Error: \"" + target + "\" not closed.");
				return null;
			}
			target += str;
			if (target.matches(LITERAL)) {
				target = target.substring(1, target.length() - 1);
				return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(target, ValueType.STRING));
			}
		}
	}

	private LexicalUnit getSpecial(String target) throws IOException {
		while (true) {
			nextchar = reader.read();
			String str = String.valueOf((char) nextchar);
			
			if (special.containsKey(target + str)) {
				
	            target += str;
	        } else {
	        	
	            reader.unread(nextchar);
	            //System.out.println("target is:" + target + "\tstr is:" + str);
	            return special.get(target);
	        }
		}
		
	}

	@Override
	public boolean expect(LexicalType type) throws Exception {
		return false;
	}

	@Override
	public void unget(LexicalUnit token) throws Exception {
	}

}
