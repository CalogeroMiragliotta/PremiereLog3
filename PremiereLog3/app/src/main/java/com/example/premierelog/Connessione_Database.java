package com.example.premierelog;

import java.sql.Connection;


import static java.sql.DriverManager.getConnection;

public class Connessione_Database {
    private static final String url= "jdbc:mysql://188.12.69.230:3306/Magazzino";
    private static final String user="root";
    private static final String pass="S0cap!!!";
    private static Connection con;
    public static Connection avvio_connessione(){
        try{

            Class.forName("org.gjt.mm.mysql.Driver");

            con = getConnection(url, user, pass);

    }
         catch (Exception e){
             e.printStackTrace();
         }

        return con;
    }
}
