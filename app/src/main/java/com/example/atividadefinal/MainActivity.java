package com.example.atividadefinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView linkcriaConta;
    EditText cpf, senha;
    Button login;

    String cpfS = "";
    String senhaS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkcriaConta = findViewById(R.id.linkCriaConta);
        cpf = findViewById(R.id.edtcpfLog);
        senha = findViewById(R.id.edtsenhaLog);
        login = findViewById(R.id.btlogar);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EnviajsonpostLogineSenha().execute();
            }
        });


        linkcriaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadUser.class);
                startActivity(i);
            }
        });


    }
    class EnviajsonpostLogineSenha extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "http://192.168.3.73/finalpm/ConsultaLogin.php";
                JSONObject jsonValores = new JSONObject();
                jsonValores.put("cpf", cpf.getText().toString());
                jsonValores.put("senha", senha.getText().toString());
                conexaouniversal mandar = new conexaouniversal();
                String mensagem = mandar.postJSONObject(url, jsonValores);

                JSONObject jsonobjc = new JSONObject(mensagem);
                JSONArray jsonvet = jsonobjc.getJSONArray("usuario");

                if (jsonvet.length() > 0) {
                    JSONObject jsonitem = jsonvet.getJSONObject(0);
                    cpfS = jsonitem.optString("cpf");
                    senhaS = jsonitem.optString("senha");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String cpfDigitado = cpf.getText().toString();
            String senhaDigitada = senha.getText().toString();

            if (senhaDigitada.equals(senhaS) && cpfDigitado.equals(cpfS)) {
                if (cpfDigitado.equals("04879806030")) {
                    Intent i = new Intent(getApplicationContext(), TelaAlteraEstado.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(getApplicationContext(), TelaPedidos.class);
                    startActivity(i);
                }

            } else {
                Toast.makeText(MainActivity.this,
                        "CPF ou senha incorretos!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}