package com.example.premierelog;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class MainActivity extends AppCompatActivity {
    private static final String url= "jdbc:mysql://188.12.69.230:3306/Progressivo_BDX";
    private static final String user="root";
    private static final String pass="S0cap!!!";
    private EditText utente, password;
    private TextView errore;
    private CheckBox cred;
    static final int READ_BLOCK_SIZE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utente =(EditText) findViewById(R.id.editText);
        password =(EditText) findViewById(R.id.editText2);
        Button login= (Button) findViewById(R.id.button);
        errore=(TextView) findViewById(R.id.textView4);
        cred=(CheckBox) findViewById(R.id.checkBox);

        carica();
        utente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utente.getText().clear();
            }
        });
        utente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errore.setText("");
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errore.setText("");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new verifica_utente().execute();

            }
        });
        utente.setOnEditorActionListener(editorListener);
        password.setOnEditorActionListener(editorListener);
    }
    private TextView.OnEditorActionListener editorListener= new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo.IME_ACTION_NEXT:
                    break;
                    case EditorInfo.IME_ACTION_SEND:
                        new verifica_utente().execute();
                        break;
            }
            return false;
        }
    };

    private class verifica_utente extends AsyncTask<Void, Void, Void> {
        private String fname,pasw;
        String ute=utente.getText().toString();
        String psw=password.getText().toString();
        @Override

        protected Void doInBackground(Void... arg0){


            try {
                Class.forName("org.gjt.mm.mysql.Driver");

                Connection conn = getConnection(url, user, pass);

                Statement st= conn.createStatement();
                String sql = "SELECT Utente,Password FROM Credenziali WHERE Utente='" + ute + "' AND Password ='" + psw + "'";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                fname=rs.getString(1);
                pasw=rs.getString(2);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {

            if (ute.equals(fname) && psw.equals(pasw)) {
                if (cred.isChecked()){
                    salva();
                }
               new Contesto().inposta_utente(ute);
               new Avvio().execute();
               finish();

            } else {

                errore.setText("Nome Utente o Password Errati");
            }
            super.onPostExecute(result);
        }

    }
    public void salva (){
        String str = utente.getText().toString() + "@"+ password.getText().toString();

        try
        {

            deleteFile("Premierelog.txt");

            FileOutputStream fOut = openFileOutput("Premierelog.txt",MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(str);
            osw.flush();
            osw.close();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
    public void carica() {
    try
    {
        FileInputStream fIn = openFileInput("Premierelog.txt");

        InputStreamReader isr = new InputStreamReader(fIn);

        char[] inputBuffer = new char[READ_BLOCK_SIZE];
        String s = "";

        int charRead;

        while ((charRead = isr.read(inputBuffer))>0)
        {
            String readString = String.copyValueOf(inputBuffer, 0,charRead);
            s = readString;
            inputBuffer = new char[READ_BLOCK_SIZE];
        }


        utente.setText(s.substring(0,s.indexOf("@")));
        String ute="";
        ute=utente.getText().toString();

        password.setText(s.substring(ute.length()+1,s.length()));

    }
        catch (IOException ioe) {
        ioe.printStackTrace();
    }
}


    private class Avvio extends AsyncTask<Void, Void, Void> {
        private String numer="";

        protected Void doInBackground(Void... arg0){

            try {

                Connection conn=Connessione_Database.avvio_connessione();
                Statement st= conn.createStatement();
                numer=utente.getText().toString();
                String sql = "SELECT MAX(b.ID_Servizio) FROM (" +
                        " select @c := @c + 1 as ID_SERVIZIO, a.* from app a, (SELECT @c := 0) c "+
                        " where STR_TO_DATE(a.Data_servizio, '%d/%m/%Y')= CURDATE() " +
                        " AND UPPER(a.Utente) LIKE UPPER('%" + numer+ "%') AND isnull(a.Servizio)) b";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                numer=rs.getString(1);
                conn.close();
                if (numer!=null) {
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                } else{
                    startActivity(new Intent(MainActivity.this, Main5Activity.class));
                                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}