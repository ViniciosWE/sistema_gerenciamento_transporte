package com.example.atividadefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class TelaPedidos extends AppCompatActivity {

    Button acompanhar, registrar;
    EditText codigoRastreio, origem, destino;
    Spinner transportadora, servico, previsao, estatus;

    Pedido pedidoTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pedidos);
        acompanhar = findViewById(R.id.btAcompanha);
        registrar = findViewById(R.id.btRegistraPedido);
        codigoRastreio = findViewById(R.id.edtCodigo);
        origem  = findViewById(R.id.edtOrigem);
        destino = findViewById(R.id.edtDestino);
        transportadora = findViewById(R.id.spsTransportadora);
        servico = findViewById(R.id.spsServico);
        previsao = findViewById(R.id.spsPrevisao);
        estatus = findViewById(R.id.spsEstatus);

        String codigo = gerarCodigoRastreio();
        codigoRastreio.setText(codigo);
        codigoRastreio.setEnabled(false);
        codigoRastreio.setFocusable(false);
        codigoRastreio.setClickable(false);
        codigoRastreio.setLongClickable(false);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String previsaoSelecionada = previsao.getSelectedItem().toString();
                int dias = diasPrevisao(previsaoSelecionada);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, dias);
                Date dataFinal = calendar.getTime();

                pedidoTemp = new Pedido(
                        codigoRastreio.getText().toString(),
                        transportadora.getSelectedItem().toString(),
                        servico.getSelectedItem().toString(),
                        estatus.getSelectedItem().toString(),
                        origem.getText().toString(),
                        destino.getText().toString(),
                        dataFinal
                );

                new Enviajsonpost().execute();
            }
        });

        acompanhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TelaAcompanha.class);
                startActivity(i);
            }
        });
    }

    class Enviajsonpost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "http://192.168.3.73/finalpm/CadastraPedido.php";
                JSONObject jsonValores = new JSONObject();
                jsonValores.put("codRastreio", pedidoTemp.getCodRastreio().toString() );
                jsonValores.put("empresa", pedidoTemp.getEmpresa().toString() );
                jsonValores.put("servico", pedidoTemp.getServico().toString());
                jsonValores.put("estatus", pedidoTemp.getEstatus().toString());
                jsonValores.put("origem", pedidoTemp.getOrigem().toString());
                jsonValores.put("destino", pedidoTemp.getDestino().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataMysql = sdf.format(pedidoTemp.getDataPrevista());

                jsonValores.put("dataPrevista", dataMysql);

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
                Toast.makeText(TelaPedidos.this, "Pedido cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TelaPedidos.this, "Erro ao cadastrar pedido!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private int diasPrevisao(String previsao) {
        switch (previsao) {
            case "Entrega no mesmo dia":
                return 0;
            case "Entrega em 24 horas":
                return 1;
            case "Entrega em até 5 dias úteis":
                return 5;
            case "Entrega em 7 a 15 dias úteis":
                return 10;
            case "Entrega em até 30 dias (internacional/econômica)":
                return 30;
            default:
                return 5;
        }
    }

    private String gerarCodigoRastreio() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        int ultimo = prefs.getInt("ultimoCodigo", 0);
        int novo = ultimo + 1;

        prefs.edit().putInt("ultimoCodigo", novo).apply();

        return "BR" + novo;
    }


}