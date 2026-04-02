package com.example.atividadefinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

public class CadUser extends AppCompatActivity {

    EditText cpf, nome, senha;
    Button cadastra, retorna;
    Usuario usrtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_user);
        cpf = findViewById(R.id.edtcpfCad);
        nome = findViewById(R.id.edtNomeCad);
        senha = findViewById(R.id.edtsenhaCad);
        cadastra = findViewById(R.id.btregregistrar);
        retorna = findViewById(R.id.btretornar);

        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usrtemp = new Usuario(nome.getText().toString(),cpf.getText().toString(),senha.getText().toString());
                new Enviajsonpost().execute();
            }
        });

        retorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    class Enviajsonpost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "http://192.168.3.73/finalpm/CadastraUser.php";
                JSONObject jsonValores = new JSONObject();
                jsonValores.put("nome", usrtemp.getNome().toString() );
                jsonValores.put("cpf", usrtemp.getCpf().toString() );
                jsonValores.put("senha", usrtemp.getSenha().toString());

                conexaouniversal mandar = new conexaouniversal();
                String mensagem = mandar.postJSONObject(url, jsonValores);
                return mensagem;

            } catch (Exception e) {
                e.printStackTrace();
                return "erro";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("sucesso")) {
                Toast.makeText(CadUser.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CadUser.this, "Erro ao cadastrar usuário!", Toast.LENGTH_LONG).show();
            }
        }

    }

}