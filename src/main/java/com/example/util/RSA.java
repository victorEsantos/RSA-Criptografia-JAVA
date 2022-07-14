package com.example.util;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.util.RSAKey.*;


@UtilityClass
public class RSA {

    public static final String PATH_CHAVE_PUBLICA = "src/main/resources/publicKey.txt";

    public static final String PATH_PRIME_LIST = "src/main/resources/primeList.txt";

    public static final String PATH_FILE_ENCRYPT = "src/main/resources/textEncrypt.txt";

    public static final String PATH_FILE_DECRYPT = "src/main/resources/textDecrypt.txt";

    public static void criptografar(String texto) {
        String textFileEncoded = Base64.getEncoder().encodeToString(texto.getBytes());
        int blockSize = TextChunk.blockSize(getModulus());

        List<BigInteger> bigIntegerList = new ArrayList<>();
        for (String chunk : TextChunk.splitChunk(textFileEncoded, blockSize)) {

            BigInteger originalChunk = new BigInteger(chunk.getBytes());
            BigInteger encodedChunk = originalChunk
                    .modPow(new BigInteger(readTextFileToString(PATH_CHAVE_PUBLICA)), getModulus());

            bigIntegerList.add(encodedChunk);
        }

        writeTextFile(bigIntegerList);
    }

    private static void writeTextFile(List<BigInteger> bigIntegerList) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_FILE_ENCRYPT));
            for (BigInteger B : bigIntegerList) {
                bw.write(B + "\n");
            }
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BigInteger getModulus() {

        List<String> numberPrimesList = readTwoPrimeNumbers();

        BigInteger p = new BigInteger(numberPrimesList.get(0));
        BigInteger q = new BigInteger(numberPrimesList.get(1));

        return p.multiply(q);
    }

    public static void descriptografar() {
        List<String> destTextList = readTextFileToList();

        StringBuilder base64 = new StringBuilder();
        for (String textLine : destTextList) {

            BigInteger encodedChunk = new BigInteger(textLine);
            BigInteger originalChunk = encodedChunk.modPow(getPrivatekey(), getModulus());

            TextChunk chunk2 = new TextChunk(originalChunk);

            for (int i = chunk2.toString().length(); i > 0; i--) {
                base64.append(chunk2.toString().substring(i - 1, i));
            }

        }

        byte[] bytes = Base64.getDecoder().decode(base64.toString().getBytes());

        String textDecrypt = new String(bytes);
        writeDecryptTextFile(textDecrypt);
    }

    private static void writeDecryptTextFile(String text) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_FILE_DECRYPT));
            bw.write(text);
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readTextFileToList() {
        List<String> textList = new ArrayList<>();

        try {
            InputStream file = new FileInputStream(PATH_FILE_ENCRYPT);

            Reader isr = new InputStreamReader(file);
            final BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                textList.add(line);
            }

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        return textList;
    }

}
