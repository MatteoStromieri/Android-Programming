package it.uniroma2.pjdm.androidstudio.kyf.login;

import android.util.Log;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidazioneCampiAccessoRegistrazione {

    public static boolean isEmailValida(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNomeValido(String input) {
        // Verifica che la stringa non contenga numeri o spazi
        return contieneSoloLettereEAlmenoUnElemento(input);
    }

    public static boolean isCognomeValido(String input) {
        // Verifica che la stringa non contenga numeri o spazi
        return contieneSoloLettereEAlmenoUnElemento(input);
    }

    public static boolean isPasswordValida(String input){
        return contieneAlmenoUnElemento(input);
    }

    public static boolean isDataValida(String input) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // Imposta il parsing della data come non tollerante

        try {
            sdf.parse(input);
            return true; // La stringa corrisponde al formato data
        } catch (ParseException e) {
            return false; // La stringa non corrisponde al formato data
        }
    }

    public static boolean isTuttoValido(String nome, String cognome, String email, String password, String data) {
        if (isDataValida(data) && isPasswordValida(password) && isNomeValido(nome) && isCognomeValido(cognome) && isEmailValida(email)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTuttoValido(String email, String password) {
        if (isPasswordValida(password) && isEmailValida(email)){
            return true;
        } else {
            return false;
        }
    }

    private static boolean contieneSoloLettereEAlmenoUnElemento(String str) {
        // Verifica se la stringa è nulla o vuota
        Log.d("str", "contieneSoloLettereEAlmenoUnElemento: " + str);
        if (str == null || str.isEmpty()) {
            return false;
        }

        // Verifica se la stringa contiene solo lettere e almeno un elemento
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private static boolean contieneAlmenoUnElemento(String str) {
        // Verifica se la stringa è nulla o vuota
        Log.d("str", "contieneSoloLettereEAlmenoUnElemento: " + str);
        if (str == null || str.isEmpty()) {
            return false;
        }
        return true;
    }
}
