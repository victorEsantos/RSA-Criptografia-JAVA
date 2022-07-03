package com.example.util;

import javax.crypto.Cipher;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA {

    public static final String ALGORITHM = "RSA";

    public static final String PATH_CHAVE_PRIVADA = "C:/keys/private.key";

    public static final String PATH_CHAVE_PUBLICA = "C:/keys/public.key";

    public static boolean hasChavesNoSO() {

        File chavePrivada = new File(PATH_CHAVE_PRIVADA);
        File chavePublica = new File(PATH_CHAVE_PUBLICA);

        return chavePrivada.exists() && chavePublica.exists();
    }

    public static byte[] criptografar(String texto) {
        PublicKey chave = RSAKey.getChavePublica();

        byte[] cipherText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    public static String descriptografar(byte[] texto) {
        PrivateKey chave = RSAKey.getChavePrivada();

        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

}
