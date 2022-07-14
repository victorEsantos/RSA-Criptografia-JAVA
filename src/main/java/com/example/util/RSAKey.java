package com.example.util;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RSAKey {

    public static BigInteger getPrivatekey() {

        BigInteger publicExponent = new BigInteger(readTextFileToString(RSA.PATH_CHAVE_PUBLICA));

        List<String> numberPrimesList = readTwoPrimeNumbers();

        var phi = new BigInteger(numberPrimesList.get(0)).subtract(BigInteger.ONE)
                .multiply(new BigInteger(numberPrimesList.get(1)).subtract(BigInteger.ONE));

        return GetKeys.getPrivateExp(phi, publicExponent);
    }

    public static List<String> readTwoPrimeNumbers() {
        List<String> primeNumberList = new ArrayList<>();

        try {
            InputStream file = new FileInputStream(RSA.PATH_PRIME_LIST);

            Reader isr = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(isr);

            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < 2) {
                primeNumberList.add(line);
                count++;
            }

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return primeNumberList;
    }

    public static String readTextFileToString(String path) {
        StringBuilder text = new StringBuilder();

        try {
            InputStream file = new FileInputStream(path);

            Reader isr = new InputStreamReader(file);
            BufferedReader bffr = new BufferedReader(isr);

            String line;
            while ((line = bffr.readLine()) != null)
                text.append(line);

            bffr.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return text.toString();
    }
}