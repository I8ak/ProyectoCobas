package com.example.proyectocobas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        Button miBoton = findViewById(R.id.miBoton);
        miBoton.setOnClickListener(v ->{
            String mensaje = editText.getText().toString().trim();
            JSONObject json=new JSONObject();
            char VT = 11;   // \x0B
            char FS = 28;   // \x1C (File Separator)
            char CR = 13;
            String mensjaeNuevo=mensaje;
            try {

                json.put("name",mensjaeNuevo);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            if (!mensaje.isEmpty()) {

                new Lanzar(VT+json.toString()+FS+CR).start();
            }else {
                textView.setText("Escribe algo");
            }
        });
    }

    private class Lanzar extends Thread {
        private String mensaje;
        public Lanzar(String mensaje) {
            this.mensaje = mensaje;
        }
        @Override
        public void run() {
            String ip = "10.35.50.32";
            int puerto = 33333;
            String respuesta = "";
            String valorRespuesta="";
            try (Socket socket = new Socket(ip, puerto);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(mensaje);
                respuesta = in.readLine();
                respuesta = respuesta.replaceAll("[\\u000B\\u001C\\u000D]", "").trim();

                // Verificar si es JSON
                if (respuesta.startsWith("{") && respuesta.endsWith("}")) {
                    JSONObject jsonRespuesta = new JSONObject(respuesta);
                    valorRespuesta = jsonRespuesta.getString("repuesta");
                } else {
                    valorRespuesta = respuesta;
                }


            } catch (IOException e) {
                valorRespuesta = "Error: " + e.getMessage();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            final String finalRespuesta =valorRespuesta ;
            runOnUiThread(() -> textView.setText(finalRespuesta));
            if (finalRespuesta.equals("esta es mi respuesta")){
                Intent intent=new Intent(MainActivity.this,Lista.class);
                startActivity(intent);
            }
        }
    }
}
