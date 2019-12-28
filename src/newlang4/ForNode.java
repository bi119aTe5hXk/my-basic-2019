package newlang4;

import java.util.*;

import newlang3.LexicalType;
import newlang3.*;

public class ForNode extends Node {
	Node subst; 
	Node max; 
	Node op; 
	String update;
	static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(
			LexicalType.FOR
			));

	private ForNode(Environment env) {
		super(env, NodeType.FOR_STMT);
	}

	public static boolean isMatch(LexicalType type) {
		return first.contains(type);
	}

	public static Node getHandler(Environment env) {
		return new ForNode(env);
	}

	@Override
	public boolean parse() throws Exception {
		env.getInput().get();// skip FOR

		LexicalUnit lu = env.getInput().peek();
		if (SubstNode.isFirst(lu)) {
			subst = SubstNode.getHandler(env);
			subst.parse();
        } else {
            throw new Exception("For syntax error.");
        }
		
		check(LexicalType.TO);
		
		//set max
		if (env.getInput().peek().getType() == LexicalType.INTVAL){
            //lu = env.getInput().get();
            max = ConstNode.getHandler(env,env.getInput().get().getValue());
        } else {
            throw new Exception("missing max value");
        }
		
		check(LexicalType.NL);
		
		
		if (StmtListNode.isFirst(lu)){
            op = StmtListNode.getHandler(env);
            op.parse();
        } else {
        	throw new Exception("missing stmt_list.");
        }
		
		check(LexicalType.NL);
		
		check(LexicalType.NEXT);
		
		if (lu.getType() == LexicalType.NAME) {
			update = env.getInput().get().getValue().getSValue();
		}else {
        	throw new Exception("missing name for update.");
		}
		return true;
	}
	
	private void check(LexicalType type) throws Exception{
		if (env.getInput().expect(type)){
			env.getInput().get();
		} else {
			throw new Exception("missing " + type);
		}
	}
	
//	public Value getValue() throws Exception {
//        subst.getValue();
//        while (true) {
//            if (env.getVariable(step).getValue().getIValue() > max.getValue().getIValue()) 
//            	return null;
//            op.getValue();
//            env.getVariable(step).setValue(new ExprNode(
//            		env.getVariable(step), 
//            		ConstNode.getHandler(new ValueImpl("1", ValueType.INTEGER), env), 
//            		LexicalType.ADD).getValue());
//        }
//    }
	@Override
	public String toString() {
        return String.format("FOR(FROM:"+ subst +" TO "+ max+";(update_object)" + update + "){"+op+"} ");
    }
}
