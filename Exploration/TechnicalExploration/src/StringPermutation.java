import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by GleasonK on 6/15/14.
 */
public class StringPermutation {
    private int N = 0;

    public void stringPermutations(String s){
        permutation("",s);
        System.out.println(this.N + " Permutations for String \"" + s + "\"");
    }

    public void permutation(String prefix, String input){
        int n = input.length();
        if (n == 0) {
            System.out.println(prefix);
            this.N++;
        }
        else{
            for (int i=0; i < n; i++){
                permutation(prefix + input.charAt(i), input.substring(0,i) + input.substring(i+1, n));
            }
        }
    }

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        StringPermutation sp = new StringPermutation();

        System.out.print("Enter string for permutations: ");
        String str = scan.next();
        sp.stringPermutations(str);
    }
}
