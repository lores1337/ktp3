package com.company;

public class Primes {
    public static void main(String [] args){
        for(int i = 2;i<100;i++){
            if(isPrime(i)) System.out.print(i + " ");
        }
    }
    public static boolean isPrime(int n){
        for(int z = 2;z<100;z++){
            if(n % z == 0 && z != n) return false;
        }
        return true;
    }
}
