package com.example.atividadefinal;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TelaAcompanha extends AppCompatActivity {

    Spinner codRastreio;
    Button retorna, busca;
    ListView acompanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_acompanha);

        codRastreio = findViewById(R.id.spsCodAcompanha);
        retorna = findViewById(R.id.btVoltarAcompanha);
        busca = findViewById(R.id.btBuscaAcompanha);
        acompanha = findViewById(R.id.listaPedidosAcompanha);

        new BuscaCodigos().execute();

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido ped = (Pedido) codRastreio.getSelectedItem();
                new BuscaEventos().execute(String.valueOf(ped.getId()));
            }
        });

        retorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TelaPedidos.class);
                startActivity(i);
            }
        });
    }

    class BuscaCodigos extends AsyncTask<Void, Void, ArrayList<Pedido>> {

        @Override
        protected ArrayList<Pedido> doInBackground(Void... voids) {

            ArrayList<Pedido> lista = new ArrayList<>();

            try {
                String url = "http://192.168.3.73/finalpm/ConsultaPedido.php";

                JSONObject json = new JSONObject();
                json.put("acao", "listar");

                String resposta = conexaouniversal.postJSONObject(url, json);

                JSONObject root = new JSONObject(resposta);
                JSONArray array = root.getJSONArray("produto");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    Pedido p = new Pedido();
                    p.setId(obj.getInt("id"));
                    p.setCodRastreio(obj.getString("codigorastreio"));

                    lista.add(p);
                }

            } catch (Exception e) {
            }

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Pedido> lista) {
            ArrayAdapter<Pedido> adapter =
                    new ArrayAdapter<>(TelaAcompanha.this,
                            android.R.layout.simple_spinner_item,
                            lista);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            codRastreio.setAdapter(adapter);
        }
    }

    class BuscaEventos extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... ids) {

            ArrayList<String> eventos = new ArrayList<>();

            try {
                String url = "http://192.168.3.73/finalpm/ConsultaEvento.php";

                JSONObject json = new JSONObject();
                json.put("id", ids[0]);

                String resposta = conexaouniversal.postJSONObject(url, json);

                JSONObject root = new JSONObject(resposta);
                JSONArray array = root.getJSONArray("evento");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject e = array.getJSONObject(i);

                    String linha =
                            "Data: " + e.getString("data") + "\n" +
                                    "Local: " + e.getString("local") + "\n" +
                                    "Status: " + e.getString("status") + "\n" +
                                    "Detalhes: " + e.getString("detalhes") + "\n";

                    eventos.add(linha);
                }

            } catch (Exception e) {
            }

            return eventos;
        }

        @Override
        protected void onPostExecute(ArrayList<String> eventos) {
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(TelaAcompanha.this,
                            android.R.layout.simple_list_item_1,
                            eventos);

            acompanha.setAdapter(adapter);
        }
    }
}
