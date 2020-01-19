package com.example.premierelog;

import java.util.ArrayList;

public class Info_spedizione {
    static ArrayList<String> Indirizzo=new ArrayList<String>();
    static ArrayList<String> Destinatario=new ArrayList<String>() ;

    public ArrayList<String> impostazioni (String dati){
        this.Indirizzo.add(dati);
        return Indirizzo;
    }
    public ArrayList<String> info (String destinatario){
        this.Destinatario.add(destinatario);
        return Destinatario;
    }
}
