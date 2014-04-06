// File: Rational.java
// Name: Kevin Gleason
// Date: January 23, 2013
// Use: This is the implementatior for the rational numbers ADT
//



public class RationalC implements Rational {

    // Instance Variables
    private int numerator;
    private int denominator;
    
    // Constructor
    public RationalC(int numerator, int denominator) {
        int min = Math.min(numerator, denominator);
        int gcf = min;
        while (gcf > 0) {
            if (numerator % gcf == 0 && denominator % gcf == 0) {
                numerator /= gcf;
                denominator /= gcf;
            }
            gcf -= 1;
        }
        this.numerator = numerator;
        this.denominator = denominator; 
    }
    
    public int getNumerator() {
        return this.numerator;
    }
    
    public int getDenominator() {
        return this.denominator;
    }
    
    public Rational add(Rational other) {
        int commonDenominator = other.getDenominator() * this.getDenominator();
        int newNumerator = this.getNumerator() * other.getDenominator() + this.getDenominator() * other.getNumerator();
        return new RationalC(newNumerator, commonDenominator);
    }
    
    public int compareTo(Rational other) {
        return this.getNumerator() * other.getDenominator() - other.getNumerator() * this.getDenominator(); 
    }
    
    public String toString() {
        return this.getNumerator() + "/" + this.getDenominator();
    }
    
    public static void main(String[] args) {
        Rational rNumber1 = new RationalC(4,2);
        System.out.println("Rational number rNumber1: " + rNumber1);
        System.out.println("rNumber1 numerator = " + rNumber1.getNumerator() + "; Denominator = " + rNumber1.getDenominator());
        Rational rNumber2 = new RationalC(100, 1000);
        System.out.println("Rational number rNumber2: " + rNumber2);
        System.out.println("rNumber1 + rNumber 2 = " + rNumber2.add(rNumber1));
        System.out.println("rNumber1 compared to rNumber2: " + rNumber1.compareTo(rNumber2));
        System.out.println("rNumber2 compared to rNumber1: " + rNumber2.compareTo(rNumber1));
    }
}