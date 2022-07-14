package com.example.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

@UtilityClass
public class GetKeys {
    // return private exponent for modulus P*Q and public exponent E,
    // where P and Q are distinct primes and Phi = (P-1)*(Q-1).
    // Side effect: set goodkey true if private key was computed
    // successfully, otherwise set it to false

    public static BigInteger getPrivateExp(BigInteger phi, BigInteger e) {
        BigInteger big0 = BigInteger.ZERO;

        BigInteger r1 = e;
        BigInteger d = BigInteger.ONE;
        BigInteger[] quotrem = phi.divideAndRemainder(e);
        BigInteger quot = quotrem[0];
        BigInteger r2 = quotrem[1];
        BigInteger x = phi.subtract(quot);

        while (r2.compareTo(big0) != 0) {
            quotrem = r1.divideAndRemainder(r2);
            quot = quotrem[0];
            BigInteger rem = quotrem[1];
            r1 = r2;
            r2 = rem;
            BigInteger temp = x;
            x = ( (phi.add(d)).subtract( quot.multiply(x).mod(phi) ) ).mod(phi);
            d = temp;
        }
        return d;
    }
}