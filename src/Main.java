import java.util.*;
import java.io.*;

class BigVigenere {
    int[] key;
    char[][] alphabet;

    public BigVigenere() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una clave numerica: ");
        String clave = sc.nextLine();
        key = new int[clave.length()];
        for (int i = 0; i < clave.length(); i++) {
            key[i] = Character.getNumericValue(clave.charAt(i));
        }
        System.out.println("Clave almacenada correctamente.");

        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int tamaño = caracteres.length();

        alphabet = new char[8][8];
        for (int i = 0; i < tamaño; i++) {
            int fila = i / 8;
            int columna = i % 8;
            alphabet[fila][columna] = caracteres.charAt(i);
        }

        System.out.println("Matriz del alfabeto creada.");
    }

    public BigVigenere(String numericKey) {
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = Character.getNumericValue(numericKey.charAt(i));
        }

        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int tamaño = caracteres.length();
        alphabet = new char[8][8];
        for (int i = 0; i < tamaño; i++) {
            int fila = i / 8;
            int columna = i % 8;
            alphabet[fila][columna] = caracteres.charAt(i);
        }
    }

    public String encrypt(String message) {
        StringBuilder mensajeCifrado = new StringBuilder();
        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int tamaño = caracteres.length();

        for (int i = 0; i < message.length(); i++) {
            char actual = message.charAt(i);
            int posicion = caracteres.indexOf(actual);
            if (posicion == -1) {
                mensajeCifrado.append(actual);
                continue;
            }

            int desplazamiento = key[i % key.length];
            int nuevaPosicion = (posicion + desplazamiento) % tamaño;
            int fila = nuevaPosicion / 8;
            int columna = nuevaPosicion % 8;
            mensajeCifrado.append(alphabet[fila][columna]);
        }
        return mensajeCifrado.toString();
    }

    public String decrypt(String encryptedMessage) {
        StringBuilder mensajeDescifrado = new StringBuilder();
        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int tamaño = caracteres.length();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char actual = encryptedMessage.charAt(i);
            int posicion = caracteres.indexOf(actual);
            if (posicion == -1) {
                mensajeDescifrado.append(actual);
                continue;
            }

            int desplazamiento = key[i % key.length];
            int nuevaPosicion = (posicion - desplazamiento + tamaño) % tamaño;
            int fila = nuevaPosicion / 8;
            int columna = nuevaPosicion % 8;
            mensajeDescifrado.append(alphabet[fila][columna]);
        }
        return mensajeDescifrado.toString();
    }

    public void reEncrypt() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el mensaje cifrado: ");
        String mensajeCifrado = sc.nextLine();

        String mensajeDescifrado = decrypt(mensajeCifrado);
        System.out.println("Mensaje descifrado: " + mensajeDescifrado);

        System.out.print("Ingrese la nueva clave numerica: ");
        String nuevaClave = sc.nextLine();
        key = new int[nuevaClave.length()];
        for (int i = 0; i < nuevaClave.length(); i++) {
            key[i] = Character.getNumericValue(nuevaClave.charAt(i));
        }

        String nuevoMensajeCifrado = encrypt(mensajeDescifrado);
        System.out.println("Nuevo mensaje cifrado: " + nuevoMensajeCifrado);
    }

    public char search(int position) {
        int contador = 0;
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 0; j < alphabet[i].length; j++) {
                if (contador == position) {
                    return alphabet[i][j];
                }
                contador++;
            }
        }
        return '?';
    }

    public char optimalSearch(int position) {
        int fila = position / 8;
        int columna = position % 8;
        return alphabet[fila][columna];
    }
}

public class Main {
    public static void main(String[] args) {
        BigVigenere cifrador = new BigVigenere();

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el mensaje a cifrar: ");
        String mensaje = sc.nextLine();

        String mensajeCifrado = cifrador.encrypt(mensaje);
        System.out.println("Mensaje cifrado: " + mensajeCifrado);

        String mensajeDescifrado = cifrador.decrypt(mensajeCifrado);
        System.out.println("Mensaje descifrado: " + mensajeDescifrado);

        cifrador.reEncrypt();

        System.out.print("Ingrese una posicion (0 a 63) para buscar con search: ");
        int posicion = sc.nextInt();
        char resultado = cifrador.search(posicion);
        System.out.println("Caracter encontrado: " + resultado);

        System.out.print("Ingrese una posicion (0 a 63) para buscar con optimalSearch: ");
        posicion = sc.nextInt();
        char resultadoOptimo = cifrador.optimalSearch(posicion);
        System.out.println("Caracter encontrado (optimo): " + resultadoOptimo);
    }
}