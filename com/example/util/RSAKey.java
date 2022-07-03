package com.example.util;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAKey {

    public static final String ALGORITHM = "RSA";

    public static final String PATH_CHAVE_PRIVADA = "C:/keys/private.key";

    public static final String PATH_CHAVE_PUBLICA = "C:/keys/public.key";

    public static void geraChave() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(2048);
            final KeyPair key = keyGen.generateKeyPair();

            File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
            File chavePublicaFile = new File(PATH_CHAVE_PUBLICA);

            criarArquivoDasChavesSeNaoExistirem(chavePrivadaFile, chavePublicaFile);

            salvarChavePublicaNoArquivo(key, chavePublicaFile);

            salvarChavePrivadaNoArquivo(key, chavePrivadaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void criarArquivoDasChavesSeNaoExistirem(File chavePrivadaFile, File chavePublicaFile) throws IOException {
        if (chavePrivadaFile.getParentFile() != null) {
            chavePrivadaFile.getParentFile().mkdirs();
        }

        chavePrivadaFile.createNewFile();

        if (chavePublicaFile.getParentFile() != null) {
            chavePublicaFile.getParentFile().mkdirs();
        }

        chavePublicaFile.createNewFile();
    }

    private static void salvarChavePublicaNoArquivo(KeyPair key, File chavePublicaFile) throws IOException {
        ObjectOutputStream chavePublicaOS = new ObjectOutputStream(
                new FileOutputStream(chavePublicaFile));
        chavePublicaOS.writeObject(key.getPublic());
        chavePublicaOS.close();
    }

    private static void salvarChavePrivadaNoArquivo(KeyPair key, File chavePrivadaFile) throws IOException {
        ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(
                new FileOutputStream(chavePrivadaFile));
        chavePrivadaOS.writeObject(key.getPrivate());
        chavePrivadaOS.close();
    }

    public static PublicKey getChavePublica() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(RSA.PATH_CHAVE_PUBLICA));
            return (PublicKey) inputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getChavePrivada() {
        try {
            ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream(RSA.PATH_CHAVE_PRIVADA));
            return (PrivateKey) inputStream2.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
