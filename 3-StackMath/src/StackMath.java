// File: StackMath.java
// Name: Kevin Gleason
// Date: Feb 2, 2014
// Use: The evaluator class for doing math on Stacks

public class StackMath {
    // Instance Variables

    private static int getPrecedence(String op){
        int prec = 0;

        if      (op.equals("("))    return 1;
        else if (op.equals(")"))    return 2;
        else if (op.equals("+"))    return 3;
        else if (op.equals("-"))    return 3;
        else if (op.equals("*"))    return 4;
        else if (op.equals("/"))    return 4;
        else if (op.equals("^"))    return 5;
        else return 0;
    }

    private static double doMath(double v1, double v2, String op) {
        if (op.equals("^")) {
            if (v1 == 0 && v2 == 0)
                throw new ArithmeticException("Can't raise 0 to 0th power.");
            else
                return Math.pow(v2, v1);
        }
        else if (op.equals("/")) {
            if (v1 == 0)
                throw new ArithmeticException("Can't divide by 0.");
            else
                return v2 / v1;
        }
        else if (op.equals("*")) return v2 * v1;
        else if (op.equals("+")) return v2 + v1;
        else return v2 - v1;
    }
    

    public static void main(String args[]){
        Stack<String> ops = new ResizingArrayStack<String>();
        Stack<Double> vals = new ResizingArrayStack<Double>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            int r = getPrecedence(s);
            
            
            if (r == 0) {
                
                vals.push(Double.parseDouble(s));
                
            }
            
            
            
            else if (r == 1) ops.push(s);
            else if (r == 2) {
                while (!ops.peek().equals("(")) {
                    String op = ops.pop();
                    double v1 = vals.pop();
                    double v2 = vals.pop();
                    double newV = doMath(v1, v2, op);
                    vals.push(newV);
                    }
                ops.pop();
            }
            else if (r <= 5 && r >= 3){
                String previous = ops.peek();
                if (getPrecedence(previous) >= r){
                    String op = ops.pop();
                    double v1 = vals.pop();
                    double v2 = vals.pop();
                    double newV = doMath(v1, v2, op);
                    vals.push(newV);
                }
                ops.push(s);
            }
        }
        double result = vals.pop();
        System.out.println("The result is: " + result);
    }
}
