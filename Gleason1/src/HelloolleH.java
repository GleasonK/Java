// HelloolleH
// Kevin Gleason CSII Muller
//

import java.util.*; //still unsure about imports but lists arent working

public class HelloolleH {
    public static void main(String[] args) {
        String result = "";
        while(!StdIn.isEmpty()) {
            String name = StdIn.readString();
            result = name + " " + result;
        }
        StdOut.println("Hello, " + result);
    }
    
}

//public class HelloolleH {
//    public static void main(String[] args) {
//        boolean isEmpty = StdIn.isEmpty();
//        List<String> names = new List<String>;
//        String name = StdIn.readString();
//        while (!isEmpty) {
//            name = StdIn.readString();
//            names.add(name);
//            i++;
//        }
//        System.out.print("Hello, "); 
//        for(int x = names.length(); x >=0; x--){
//            System.out.print(names[x-1] + " ");
//        }
//    }
//}

// Can do it with args[]

//public class HelloolleH {
//    public static void main(String[] args) {
//        boolean isEmpty = StdIn.isEmpty();
//        int i = 0;
//        System.out.print("Hello, "); 
//        for(int x = args.length-1; x >=0; x--){
//            System.out.print(args[x] + " ");
//        } 
//        
//    }
//}