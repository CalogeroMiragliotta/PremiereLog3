package com.example.premierelog;




import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView info,mitte,dest,loc,contr,colli;
    private Button serv_effettuato,non_effe;
    private ImageView successivo,precedente;
    public static final String EXTRA_TEXT="com.example.premierelog.example.EXTRA_TEXT";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        info =(TextView) findViewById(R.id.textView);
        mitte =(TextView) findViewById(R.id.textView5);
        dest =(TextView) findViewById(R.id.textView9);
        loc =(TextView) findViewById(R.id.textView13);
        colli =(TextView) findViewById(R.id.textView17);
        contr =(TextView) findViewById(R.id.textView14);
        precedente = (ImageView) findViewById(R.id.imageView5);
        successivo = (ImageView) findViewById(R.id.imageView3);
        serv_effettuato=(Button) findViewById(R.id.button2);
        non_effe=(Button) findViewById(R.id.button3);
        progressdialog= new ProgressDialog(Main2Activity.this);
        progressdialog.setMessage("Attendi...");
        progressdialog.setCancelable(false);
        new Avvio().execute();
        dest.setMovementMethod(new ScrollingMovementMethod());
        mitte.setMovementMethod(new ScrollingMovementMethod());
        successivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Succes().execute();
            }
        });
        serv_effettuato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder finestra=new AlertDialog.Builder(Main2Activity.this);
                finestra.setMessage("Hai Effettuato il Servizio?").setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                controllo_contanti();

                            }
                        })
                        .setNegativeButton("NO",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                return;
                            }
                        });
                AlertDialog alt=finestra.create();
                alt.setTitle("Servizio Effettuato");
                alt.show();


            }
        });
        non_effe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Main4Activity.class));
                finish();

            }
        });
        precedente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Precede().execute();
            }
        });

    }
    //menu destra
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mappa,menu);
        getMenuInflater().inflate(R.menu.menu_telefono,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_layout_telefono:
                if (Telefono.Telefono_cl!="" && Telefono.Telefono_cl!=null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+ Telefono.Telefono_cl));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(Main2Activity.this,"Nessun Numero Trovato",Toast.LENGTH_SHORT).show();
                }
            case R.id.menu_layout_mappa:
                new Indirizzi_Clienti().execute();
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                AlertDialog.Builder finestra1=new AlertDialog.Builder(Main2Activity.this);
                finestra1.setMessage("Vuoi Tornare alla pagina Iniziale?").setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("NO",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                return;
                            }
                        });
                AlertDialog alt1=finestra1.create();
                alt1.setTitle("Home");
                alt1.show();
                return true;
            case R.id.ritiro:
                Toast.makeText(this,"Aggiungi Ritiro",Toast.LENGTH_SHORT);

                AlertDialog.Builder finestra=new AlertDialog.Builder(Main2Activity.this);
                finestra.setMessage("Vuoi inserire un Ritiro non programmato?").setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                new ritiro_differito().execute();
                                mDrawerLayout.closeDrawers();
                                Toast.makeText(Main2Activity.this,"Ritiro Inserito",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                return;
                            }
                        });
                AlertDialog alt=finestra.create();
                alt.setTitle("Ritiro non programmato");
                alt.show();
                return true;
            case R.id.aggiornamento:
                Intent intenzione = new Intent(Intent.ACTION_VIEW, Uri.parse("http://188.12.69.230:81/release/Premierelog.apk"));
                startActivity(intenzione);
                return true;

            case R.id.logout:
                AlertDialog.Builder finestra2=new AlertDialog.Builder(Main2Activity.this);
                finestra2.setMessage("Vuoi Uscire dall'APP?").setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                finish();
                            }
                        })
                        .setNegativeButton("NO",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whick){
                                return;
                            }
                        });

                AlertDialog alt2=finestra2.create();
                alt2.setTitle("Logout");
                alt2.show();
                return true;
            default:
                return false;
        }
    }


    public class Avvio extends AsyncTask<Void, Void, Void> {
        private String numer="";
        @Override
       protected void onPreExecute()
        {
            progressdialog.show();
        }
        protected Void doInBackground(Void... arg0){

            try {

                Connection conn=Connessione_Database.avvio_connessione();
                Statement st= conn.createStatement();

                String sql = "SELECT MAX(b.ID_Servizio) FROM (" +
                        " select @c := @c + 1 as ID_SERVIZIO, a.* from app a, (SELECT @c := 0) c "+
                        " where STR_TO_DATE(a.Data_servizio, '%d/%m/%Y')= CURDATE() " +
                        " AND a.Utente='" + Contesto.Utente + "' AND isnull(a.Servizio)) b";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                numer=rs.getString(1);
                conn.close();
                if (numer==null) {
                    startActivity(new Intent(Main2Activity.this, Main5Activity.class));
                    finish();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            new Dati_App().dati_app(numer);
            new Progressivo_id().Progressivo_Spedizione("1");
            progressdialog.dismiss();
            new Precede().execute();
            super.onPostExecute(result);
        }
    }



    private class Precede extends AsyncTask<Void, Void, Void> {
        private  String risultato= "",mi="",de="",lo="",con="",col="",tel="";
        @Override
        protected void onPreExecute()
        {
            progressdialog.show();
        }
        int val,valo;
        @Override
        protected Void doInBackground(Void... arg0){
            try {
                risultato=Progressivo_id.Progressivo;
                val= Integer.parseInt(risultato);
                if (val>1) {
                    val=val-1;
                }
                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();

                String sql = "SELECT b.id FROM (SELECT a.id, @c := @c + 1 as ID_SERVIZIO, a.Data_servizio, a.utente, a.Servizio FROM App a, (SELECT @c := 0) c " +
                        "WHERE STR_TO_DATE(a.Data_servizio, '%d/%m/%Y')= CURDATE()" +
                        " AND a.Utente='"+ Contesto.Utente +"' AND isnull(a.Servizio))b WHERE b.ID_SERVIZIO='" + val +"'";
                final ResultSet rs = st.executeQuery(sql);
                rs.next();

                valo = Integer.parseInt(rs.getString(1));


                sql="SELECT ID_Spedizione,mittente,destinatario,Localita,contrassegno,colli,telefono From APP where id=" + valo+"";
                final ResultSet sr = st.executeQuery(sql);
                sr.next();

                risultato=sr.getString(1) ;
                mi=sr.getString(2);
                de=sr.getString(3);
                lo=sr.getString(4);
                con=sr.getString(5);
                col=sr.getString(6);
                tel=sr.getString(7);
                conn.close();

            }
            catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(Void result) {
            info.setText("");
            mitte.setText("");
            dest.setText("");
            loc.setText("");
            contr.setText("");
            info.setText(risultato.toUpperCase());
            mitte .setText(mi.toUpperCase());
            dest .setText(de.toUpperCase());
            loc. setText(lo.toUpperCase());
            contr.setText(con.toUpperCase());
            colli.setText(col);
            new Telefono().Telefono_cliente(tel);
            new Progressivo_id().ID_sped(String.valueOf(valo));
            new Progressivo_id().Progressivo_Spedizione(String.valueOf(val));
            progressdialog.dismiss();
            super.onPostExecute(result);

        }
    }
    private class Succes extends AsyncTask<Void, Void, Void> {
        private  String risultato= "",mi="",de="",lo="",con="",col="",tel="";
        @Override
        protected void onPreExecute()
        {
            progressdialog.show();
        }
        int val,valo;
        @Override
        protected Void doInBackground(Void... arg0){
            try {
                risultato=Progressivo_id.Progressivo;
                val= Integer.parseInt(risultato);
                if (val<Integer.parseInt(Dati_App.Max_id)) {
                    val=val+1;
                }

                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();

                String sql = "SELECT b.id FROM (SELECT a.id, @c := @c + 1 as ID_SERVIZIO, a.Data_servizio, a.utente, a.Servizio FROM App a, (SELECT @c := 0) c " +
                        "WHERE STR_TO_DATE(a.Data_servizio, '%d/%m/%Y')= CURDATE()" +
                        " AND a.Utente='"+ Contesto.Utente +"' AND isnull(a.Servizio))b WHERE b.ID_SERVIZIO='" + val +"'";
                final ResultSet rs = st.executeQuery(sql);
                rs.next();

                valo = Integer.parseInt(rs.getString(1));

                sql="SELECT ID_Spedizione,mittente,destinatario,Localita,contrassegno,colli,telefono From APP where id=" + valo+"";

                final ResultSet sr = st.executeQuery(sql);
                sr.next();

                risultato=sr.getString(1) ;
                mi=sr.getString(2);
                de=sr.getString(3);
                lo=sr.getString(4);
                con=sr.getString(5);
                col=sr.getString(6);
                tel=sr.getString(7);
                conn.close();

            }
            catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(Void result) {
            info.setText("");
            mitte.setText("");
            dest.setText("");
            loc.setText("");
            contr.setText("");
            info.setText(risultato.toUpperCase());
            mitte .setText(mi.toUpperCase());
            dest .setText(de.toUpperCase());
            loc. setText(lo.toUpperCase());
            contr.setText(con.toUpperCase());
            colli.setText(col);
            new Telefono().Telefono_cliente(tel);
            new Progressivo_id().ID_sped(String.valueOf(valo));
            new Progressivo_id().Progressivo_Spedizione(String.valueOf(val));
            progressdialog.dismiss();
            super.onPostExecute(result);

        }
    }
    private void controllo_contanti(){
        String risultato=contr.getText().toString();
        risultato=risultato.replace(" ","");
        if ( !risultato.equals("€0,00" )) {
            startActivity(new Intent(Main2Activity.this, Main3Activity.class));
            finish();
        }  else{
            new effettuato().execute();
        }
    }


    private class effettuato extends AsyncTask<Void, Void, Void> {
        private  String risultato= "";
        int val;

        @Override

        protected Void doInBackground(Void... arg0){

            try {
                risultato=Progressivo_id.ID_spedizione;
                val= Integer.parseInt(risultato);

                Connection conn=Connessione_Database.avvio_connessione();

                Statement st= conn.createStatement();
                String sql = "UPDATE App SET Servizio='Effettuato' WHERE ID=" + val;
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            new Avvio().execute();
            super.onPostExecute(result);
        }

    }



    public class ritiro_differito extends AsyncTask<Void, Void, Void> {

        private String ute="",dat="",bdx="",mi="",de="",loc="";
        @Override
        protected void onPreExecute()
        {
            progressdialog.setMessage("Attendi inserisco ritiro...");
            progressdialog.show();
        }

        protected Void doInBackground(Void... arg0){

            try {

                Connection conn=Connessione_Database.avvio_connessione();
                Statement st= conn.createStatement();
                String sql = "SELECT Utente,Data_servizio,N_BDX,mittente,destinatario,localita FROM App WHERE ID_Spedizione= TRIM('" + info.getText().toString()+"')" ;
                final ResultSet rs = st.executeQuery(sql);
                rs.next();

                ute=rs.getString(1);
                dat=rs.getString(2);
                bdx=rs.getString(3);
                de=rs.getNString(4);
                mi= rs.getNString(5);
                loc= rs.getNString(6);
                sql = "INSERT INTO App (ID_Spedizione,mittente,destinatario,localita,Note,Utente,Data_servizio,Servizio,N_BDX) VALUES (TRIM('" +info.getText().toString()+"'),'" + de+"','"+mi+"','"+loc+"','Ritiro senza Bolla','"+ ute+"','"+dat+"','Effettuato','"+ bdx +"')"  ;
                st.executeUpdate(sql);
                conn.close();

            }
            catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
            progressdialog.dismiss();
            return null;
        }

    }
    private class Indirizzi_Clienti extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute()
        {
            progressdialog.setMessage("Carico Indirizzi...");
            progressdialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0){
                Info_spedizione.Indirizzo.clear();
                Info_spedizione.Destinatario.clear();
            try {

                Connection conn=Connessione_Database.avvio_connessione();
                Statement st= conn.createStatement();

                String sql = "select if(if(c.destinatario is null, r.mittente, c.destinatario) is null, a.destinatario,  " +
                        "if(c.destinatario is null, r.mittente, c.destinatario)) as destinatario, b.Indirizzo " +
                        "from app a " +
                        "inner join info_cliente b on a.ID_spedizione=b.ID_spedizione " +
                        "left join consegne c on a.ID_spedizione=c.ID_spedizione "+
                        "left join ritiri r on a.ID_spedizione=r.ID_spedizione" +
                        " where STR_TO_DATE(a.Data_servizio, '%d/%m/%Y')= CURDATE() and a.Utente='"+ Contesto.Utente +"' and a.Servizio is null and  b.Indirizzo is not null group by b.Indirizzo";

                final ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    new Info_spedizione().info(rs.getString(1));
                    new Info_spedizione().impostazioni(rs.getString(2));
                }
                conn.close();

            }
            catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            if (Info_spedizione.Indirizzo.size()>0) {
                startActivity(new Intent(Main2Activity.this, MapsActivity.class));
                try {
                    Thread.sleep(700);
                }
                catch (Exception e) {}
            } else {
                Toast.makeText(Main2Activity.this,"Nessun Numero Trovato",Toast.LENGTH_SHORT).show();
            }
             progressdialog.dismiss();
        }
    }

}
