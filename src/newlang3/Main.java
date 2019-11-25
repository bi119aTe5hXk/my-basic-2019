
package newlang3;

public class Main{

    public static void main(String[] args) throws Exception {

        String fname = "src/newlang3/sample.bas";
        if (args.length > 0)
            fname = args[0];

        LexicalAnalyzer la = new LexicalAnalyzerImpl(fname);

        while (true) {
            try {
                LexicalUnit lu = la.get();
                
                if (lu.getType() == LexicalType.EOF) {
                    break;
                }
                System.out.println(lu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        
        
        
  
//        System.out.println("basic parser");
//        Environment		env = new Environment(la);
//        LexicalUnit		first = la.get();
//        
//        Node program = Program.isMatch(env, first);
//        if (program != null && program.Parse()) {
//        	System.out.println(program);
//        	System.out.println("value = " + program.getValue());
//        }
//        else System.out.println("syntax error");

    }

}
