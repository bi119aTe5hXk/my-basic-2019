package newlang4;

import java.util.*;
import newlang3.*;

public class CondNode extends Node {
	LexicalType op;
	Node left;
	Node right;

	static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(
			LexicalType.NAME, 
			LexicalType.SUB,
			LexicalType.LP, 
			LexicalType.INTVAL, 
			LexicalType.DOUBLEVAL, 
			LexicalType.LITERAL
			));

	static Set<LexicalType> op_list = new HashSet<>(Arrays.asList(
			LexicalType.EQ, 
			LexicalType.LT, 
			LexicalType.LE,
			LexicalType.GT, 
			LexicalType.GE, 
			LexicalType.NE
			));

	public static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}

	private CondNode(Environment env) {
		super(env, NodeType.COND);
	}

	public static Node getHandler(Environment env) {
		return new CondNode(env);
	}
	@Override
	public boolean parse() throws Exception {
		if (ExprNode.isFirst(env.getInput().peek())) {
			left = ExprNode.getHandler(env);
			left.parse();
		} else {
			throw new Exception("syntax error in left expr.");
		}

		if (op_list.contains(env.getInput().peek().getType())) {
			op = env.getInput().get().getType();
		} else {
			throw new Exception("syntax error in operator." );
		}

		if (ExprNode.isFirst(env.getInput().peek())) {
			right = ExprNode.getHandler(env);
			right.parse();
		} else {
			throw new Exception("syntax error in right expr.");
		}
		return true;
	}

	public Value getValue() throws Exception {
		Value lv = left.getValue();
		Value rv = right.getValue();
		if (lv == null || rv == null) {
			throw new Exception("null is not cal-able");
		}
		if (lv.getType() == ValueType.STRING || 
				rv.getType() == ValueType.STRING) {
			switch(op) {
			case EQ:
				return new ValueImpl(lv.getSValue().equals(rv.getSValue()));
			case NE:
				return new ValueImpl(!lv.getSValue().equals(rv.getSValue()));
				default:
					throw new Exception("invalid operator.");
			}
		}
		
		switch(op) {
		case LT:
			return new ValueImpl(lv.getDValue() < rv.getDValue());
		case LE:
			return new ValueImpl(lv.getDValue() <= rv.getDValue());
		case GT:
			return new ValueImpl(lv.getDValue() > rv.getDValue());
		case GE:
			return new ValueImpl(lv.getDValue() >= rv.getDValue());
		case EQ:
			return new ValueImpl(lv.getDValue() == rv.getDValue());
		case NE:
			return new ValueImpl(lv.getDValue() != rv.getDValue());
		default:
			throw new Exception("invalid condition.");
		}
		
	}

	@Override
	public String toString() {
		String opstr = "";
		switch(op) {
		case LT:
			opstr = "LESS THAN";
			break;
		case LE:
			opstr = "LESS OR EQUAL";
			break;
		case GT:
			opstr = "GREATER THAN";
			break;
		case GE:
			opstr = "GREATER OR EQUAL";
			break;
		case EQ:
			opstr = "EQUAL";
			break;
		case NE:
			opstr = "NOT EQUAL";
			break;
		default:
			opstr = "NULL";
			break;
		}
		return "condition:(" + left + " " + opstr + " " + right + ")";
	}

}
