package newlang4;

import java.util.*;
import newlang3.*;
public class ExprNode extends Node{
	
	private Node left;
    private Node right;
    private LexicalType op;
	
	static Set<LexicalType> first = 
		new HashSet<LexicalType>(Arrays.asList(
			LexicalType.SUB,
			LexicalType.LP, 
			LexicalType.NAME, 
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL, 
			LexicalType.LITERAL
		));
	private static final Map<LexicalType,Integer> opmap=new HashMap<>();
	static {
		opmap.put(LexicalType.DIV,1);
		opmap.put(LexicalType.MUL,2);
		opmap.put(LexicalType.SUB,3);
		opmap.put(LexicalType.ADD,4);
	}
	
	private ExprNode(Environment env) {
        super(env,NodeType.EXPR);
    }
	public ExprNode(Node left,Node right,LexicalType op){
		this.left = left;
		this.right = right;
		this.op = op; 
	}
	
	public static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}
	
	public static Node getHandler(Environment env) {
        return new ExprNode(env);
    }
	
	@Override
	public boolean parse() throws Exception{
		List<Node> exprlist = new ArrayList<>();
        List<LexicalType> oplist = new ArrayList<>();
		
        while (true) {
            switch (env.getInput().peek().getType()) {
                case SUB:
                    LexicalType inputType = env.getInput().peek(2).getType();

                    if ((inputType == LexicalType.INTVAL) || 
                    	(inputType == LexicalType.DOUBLEVAL) || 
                    	(inputType == LexicalType.LP)) {

                        env.getInput().get();
                        
                        exprlist.add(ConstNode.getHandler(new ValueImpl("-1", ValueType.INTEGER), env));
                        addOp(exprlist, oplist, LexicalType.MUL);
                        continue;
                    } else {
                        throw new Exception("syntax error.");
                    }

                case LP:
                    env.getInput().get();

                    Node exprHandler = ExprNode.getHandler(env);
                    exprHandler.parse();
                    exprlist.add(exprHandler);

                    if (env.getInput().get().getType() != LexicalType.RP) {
                        throw new Exception("')' is missing");
                    }
                    break;

                case NAME:
                    if (env.getInput().peek(2).getType() == LexicalType.LP) {
                        Node callSub = CallNode.getHandler(env);
                        callSub.parse();
                        exprlist.add(callSub);

                    } else {
                    	exprlist.add(env.getVariable(env.getInput().get().getValue().getSValue()));
                    }
                    break;
                    
                //numbers   
                case INTVAL:
                case DOUBLEVAL:
                case LITERAL:
                	exprlist.add(ConstNode.getHandler(env,env.getInput().get().getValue()));
                    break;
                    
                default:
                    throw new Exception("syntax error.");
            }

            if (opmap.containsKey(env.getInput().peek().getType())) {
            	addOp(exprlist, 
            			oplist, 
            			env.getInput().get().getType());
            } else {
            	break;
            }

        }

        for (int i = oplist.size() - 1; i >= 0; i--) {
            if (i == 0) {
                left = exprlist.get(0);
                right = exprlist.get(1);
                op = oplist.get(0);
                return true;
            }
            exprlist.add(new ExprNode(
            		exprlist.get(exprlist.size() - 2), 
            		exprlist.get(exprlist.size() - 1), 
            		oplist.get(i)));
            exprlist.remove(exprlist.size() - 3);
            exprlist.remove(exprlist.size() - 2);
        }
        left = exprlist.get(0);
		
		
		
		return true;
	}
	private void addOp(List<Node> r_list, List<LexicalType> op_list, LexicalType type) {
        for (int i = op_list.size() - 1; i >= 0; i--){
            boolean flag = false;
            if (opmap.get(op_list.get(i))<opmap.get(type)){
                flag = true;
                r_list.add(new ExprNode(r_list.get(r_list.size() - 2),r_list.get(r_list.size() - 1),op_list.get(i)));
                r_list.remove(r_list.size() - 3);
                r_list.remove(r_list.size() - 2);
                op_list.remove(i);
            } else if (flag && opmap.get(op_list.get(i)) >= opmap.get(type)) {
            	break;
            }
        }
        op_list.add(type);
    }
	public Value getValue() throws Exception {
        if (op == null) return left.getValue();

        Value leftVal = left.getValue();
        Value rightVal = right.getValue();

        if (leftVal == null || rightVal == null) 
        	throw new Exception("null can't be caled");

        if (leftVal.getType() == ValueType.STRING || rightVal.getType() == ValueType.STRING) {
            if (op == LexicalType.ADD) {
            	return new ValueImpl(leftVal.getSValue() + rightVal.getSValue(), ValueType.STRING);
            } else {
            	throw new Exception("invalid operator for string");
            	}
        }

        double result;
        switch (op) {
            case ADD:
                result = leftVal.getDValue() + rightVal.getDValue();
                break;
            case SUB:
                result = leftVal.getDValue() - rightVal.getDValue();
                break;
            case MUL:
                result = leftVal.getDValue() * rightVal.getDValue();
                break;
            case DIV:
                if (rightVal.getDValue() == 0) 
                	throw new Exception("you can't divide by zero.");
                result = leftVal.getDValue() / rightVal.getDValue();
                break;
            default: 
            	throw new Exception("invalid operator");
        }

        if (leftVal.getType() == ValueType.DOUBLE || 
        	rightVal.getType() == ValueType.DOUBLE) {
        	return new ValueImpl(result);
        } else {
        	return new ValueImpl((int)result);
        }
    }
	
	@Override
	public String toString() {
		String str=left.toString();
		if (op != null) {
            str += " " + op.toString() + " " + right.toString() ;
        }
		return str;
    }
	
}
