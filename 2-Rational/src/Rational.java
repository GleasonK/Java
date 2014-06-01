// File: Rational.java
// Name: Kevin Gleason
// Date: January 23, 2013
// Use: This is the interface ADT for rational numbers
//



public interface Rational {

//  public RationalC(int numerator, int denominator);

    public int getNumerator();
    public int getDenominator();
    public Rational add(Rational other);
    public int compareTo(Rational other);
    public String toString();
}