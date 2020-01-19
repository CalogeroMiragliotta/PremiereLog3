package com.example.premierelog;

public class Contesto {
    static String Utente;

    //Invio il valore alla variabile Utente
    public String inposta_utente (String utenza){
        this.Utente=utenza;
        return Utente;
    }

}
