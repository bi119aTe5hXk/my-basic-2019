import newlang3.*;
import newlang4.*;
import newlang5.*;
public class Main{

    public static void main(String[] args) throws Exception {

        String fname = "src/newlang3/sample.bas";
        if (args.length > 0)
            fname = args[0];

        LexicalAnalyzer la = new LexicalAnalyzerImpl(fname);

        
        /* newlang3 */
//        while (true) {
//            try {
//                LexicalUnit lu = la.get();
//                
//                if (lu.getType() == LexicalType.EOF) {
//                    break;
//                }
//                System.out.println(lu);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        
        
        
        
        
        
        //System.out.println("basic parser");
        Environment		env = new Environment(la);
        LexicalUnit		first = la.get();
        la.unget(first);
        
        /* newlang4 */
//        if (ProgramNode.isFirst(first)) {
//        	Node handler = ProgramNode.getHandler(first, env);
//        	handler.parse();
//        	System.out.println(handler);
//        }
//        else System.out.println("syntax error");
        
        
        
        /* newlang5 */
        if (ProgramNode.isFirst(first)) {
        	Node handler = ProgramNode.getHandler(first, env);
        	handler.parse();
        	System.out.println(handler.getValue());
        }
        else System.out.println("syntax error");
    }

}
