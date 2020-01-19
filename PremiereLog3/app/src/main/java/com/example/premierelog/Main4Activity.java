package com.example.premierelog;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class Main4Activity extends AppCompatActivity {
    private Button rifiutato,assente,indirizzo,chiusura;
    private EditText note;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        rifiutato=(Button)   findViewById(R.id.button4);
        assente=(Button)   findViewById(R.id.button8);
        indirizzo=(Button)   findViewById(R.id.button11);
        note=(EditText) findViewById(R.id.editText4);
        chiusura=(Button) findViewById(R.id.button9);


        rifiutato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new rifiu().execute();
                startActivity(new Intent(Main4Activity.this, Main2Activity.class));
                finish();
            }
        });
        assente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new cliente().execute();
                startActivity(new Intent(Main4Activity.this, Main2Activity.class));
                finish();
            }
        });
        indirizzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new indiri().execute();
                startActivity(new Intent(Main4Activity.this, Main2Activity.class));
                finish();
            }
        });
           chiusura.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(Main4Activity.this, Main2Activity.class));
                   finish();
               }
           });
    }
    private class rifiu extends AsyncTask<Void, Void, Void> {
        int val;

        @Override

        protected Void doInBackground(Void... arg0){

            try {

                val= Integer.parseInt(Progressivo_id.ID_spedizione);
                String note_rif=note.getText().toString();
                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();
                String sql = "UPDATE App SET Servizio='Non Effettuato'  WHERE ID=" + val ;
                st.executeUpdate(sql);
                note_rif=note_rif.replace("'","");
                sql = "UPDATE App SET Note ='" + note_rif +"'  WHERE ID=" + val ;
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    }
    private class cliente extends AsyncTask<Void, Void, Void> {
        int val;

        @Override

        protected Void doInBackground(Void... arg0){

            try {

                val= Integer.parseInt(Progressivo_id.ID_spedizione);
                String note_rif=note.getText().toString();
                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();
                String sql = "UPDATE App SET Servizio='Non Effettuato: Cliente assente' WHERE ID=" + val ;
                st.executeUpdate(sql);
                note_rif=note_rif.replace("'","");
                sql = "UPDATE App SET Note ='" + note_rif +"'  WHERE ID=" + val ;
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    }

    private class indiri extends AsyncTask<Void, Void, Void> {
        int val;

        @Override

        protected Void doInBackground(Void... arg0){

            try {
                val= Integer.parseInt(Progressivo_id.ID_spedizione);
                String note_rif=note.getText().toString();
                Connection conn=Connessione_Database.avvio_connessione();
                Statement st= conn.createStatement();
                String sql = "UPDATE App SET Servizio='Non Effettuato: Indirizzo errato' WHERE ID=" + val ;
                st.executeUpdate(sql);
                note_rif=note_rif.replace("'","");
                sql = "UPDATE App SET Note ='" + note_rif +"'  WHERE ID=" + val ;
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    }

}
