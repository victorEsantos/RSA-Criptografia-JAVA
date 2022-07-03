package com.example;


import com.example.util.RSA;
import com.example.util.RSAKey;

public class Main {

    public static void main(String[] args) {

        if (!RSA.hasChavesNoSO())
            RSAKey.geraChave();

        final String mensagemOriginal = "Será criptografado !@#$%¨&*()";

        final byte[] mensagemCriptografada = RSA.criptografar(mensagemOriginal);

        final String mensagemDescriptografada = RSA.descriptografar(mensagemCriptografada);

        printarMensagens(mensagemOriginal, mensagemCriptografada, mensagemDescriptografada);
    }

    private static void printarMensagens(String mensagemOriginal, byte[] mensagemCriptografada, String mensagemDescriptografada) {
        System.out.println("Mensagem Original: " + mensagemOriginal);
        System.out.println("Mensagem Criptografada: " + mensagemCriptografada.toString());
        System.out.println("Mensagem Decriptografada: " + mensagemDescriptografada);
    }
}
