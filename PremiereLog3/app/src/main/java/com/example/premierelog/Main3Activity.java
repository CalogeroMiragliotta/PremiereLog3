package com.example.premierelog;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.sql.Connection;
import java.sql.Statement;


public class Main3Activity extends AppCompatActivity {

    private EditText info;
    private Button contanti,assegno,noninc;
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
        setContentView(R.layout.activity_main3);
        info =(EditText) findViewById(R.id.editText3);
        contanti= (Button) findViewById(R.id.button5);
        assegno = (Button) findViewById(R.id.button6);
        noninc =(Button) findViewById(R.id.button7);

            contanti.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new MyTask1().execute();
            startActivity(new Intent(Main3Activity.this, Main2Activity.class));
            finish();
        }
    });
        noninc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new non_inc().execute();
                startActivity(new Intent(Main3Activity.this, Main2Activity.class));
                finish();
            }
        });
        assegno.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         new ass().execute();
         startActivity(new Intent(Main3Activity.this, Main2Activity.class));
         finish();
        }
    });
    }
        public class MyTask1 extends AsyncTask<Void, Void, Void> {

            private  String info_inca =info.getText().toString();
            protected Void doInBackground(Void... arg0){

                try {

                    Connection conn=Connessione_Database.avvio_connessione();

                    Statement st= conn.createStatement();
                    String sql = "UPDATE App Set Incasso= 'Contanti' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione);
                    st.executeUpdate(sql);
                    if(info_inca !=null){
                        sql = "UPDATE App Set Note= '" +info_inca +"' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione) ;
                        st.executeUpdate(sql);
                    }
                    sql = "UPDATE App SET Servizio='Effettuato' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione);
                    st.executeUpdate(sql);
                    conn.close();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
    }

    public class ass extends AsyncTask<Void, Void, Void> {
        private  String info_inca =info.getText().toString();
        protected Void doInBackground(Void... arg0){

            try {

                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();
                String sql = "UPDATE App Set Incasso= 'Assegno' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione) ;
                st.executeUpdate(sql);
                if(info_inca !=null){
                sql = "UPDATE App Set Note= '" +info_inca +"' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione) ;
                st.executeUpdate(sql);
                }
                sql = "UPDATE App SET Servizio='Effettuato' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione);
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }
        public class non_inc extends AsyncTask<Void, Void, Void> {
        private  String info_inca =info.getText().toString();
        protected Void doInBackground(Void... arg0){

            try {

                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();
                String sql = "UPDATE App Set Incasso= 'Non Incassato' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione) ;
                st.executeUpdate(sql);
                sql = "UPDATE App Set Note= '" +info_inca +"' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione) ;
                st.executeUpdate(sql);
                sql = "UPDATE App SET Servizio='Effettuato' WHERE ID=" + Integer.parseInt(Progressivo_id.ID_spedizione);
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

