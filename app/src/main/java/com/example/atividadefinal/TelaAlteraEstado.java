package com.example.atividadefinal;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaAlteraEstado extends AppCompatActivity {

    Spinner codigoRastreio, estatus;
    Button busca, altera;
    EditText local, detalhes, data;
    ListView lista;

    Evento eventoTemp = new Evento();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_altera_estado);

        codigoRastreio = findViewById(R.id.spsCodEstado);
        estatus = findViewById(R.id.spsEstatusAlterar);
        busca = findViewById(R.id.btBuscaEstado);
        altera = findViewById(R.id.btAlteraEstado);
        local = findViewById(R.id.edtLocal);
        detalhes = findViewById(R.id.edtDetalhes);
        lista = findViewById(R.id.listaPedidoEstado);
        data = findViewById(R.id.edtData);

        String agora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        data.setText(agora);
        new BuscaCodigos().execute();

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Pedido p = (Pedido) codigoRastreio.getSelectedItem();
                    new BuscaEventos().execute(String.valueOf(p.getId()));
            }
        });

        altera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Pedido ped = (Pedido) codigoRastreio.getSelectedItem();

                    String dataFinal = data.getText().toString();


                    eventoTemp.setProduto_id(ped.getId());
                    eventoTemp.setData(dataFinal);
                    eventoTemp.setLocal(local.getText().toString());
                    eventoTemp.setStatus(estatus.getSelectedItem().toString());
                    eventoTemp.setDetalhes(detalhes.getText().toString());

                    new Enviajsonpost().execute();
            }
        });
    }

    class Enviajsonpost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "http://192.168.3.73/finalpm/CadastraEvento.php";
                JSONObject jsonValores = new JSONObject();

                jsonValores.put("produto_id", eventoTemp.getProduto_id());
                jsonValores.put("data", eventoTemp.getData());
                jsonValores.put("local", eventoTemp.getLocal());
                jsonValores.put("status", eventoTemp.getStatus());
                jsonValores.put("detalhes", eventoTemp.getDetalhes());

                conexaouniversal mandar = new conexaouniversal();
                return mandar.postJSONObject(url, jsonValores);

            } catch (Exception e) {
                e.printStackTrace();
                return "erro";
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("sucesso")) {
                Toast.makeText(TelaAlteraEstado.this, "Evento atualizado com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TelaAlteraEstado.this, "Erro ao atualizar evento!", Toast.LENGTH_LONG).show();
            }
        }
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

            } catch (Exception ignored) {}

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Pedido> lista) {
            ArrayAdapter<Pedido> adapter =
                    new ArrayAdapter<>(TelaAlteraEstado.this,
                            android.R.layout.simple_spinner_item,
                            lista);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            codigoRastreio.setAdapter(adapter);
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
                    new ArrayAdapter<>(TelaAlteraEstado.this,
                            android.R.layout.simple_list_item_1,
                            eventos);

            lista.setAdapter(adapter);
        }
    }
}
