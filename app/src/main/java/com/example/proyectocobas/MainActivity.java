package com.example.proyectocobas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
            if (!mensaje.isEmpty()) {
                new Lanzar(mensaje).start();

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
            String ip = "10.35.50.32"; // Cambia esto si usas un dispositivo físico
            int puerto = 33333;
            String respuesta = "";

            try (Socket socket = new Socket(ip, puerto);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(mensaje);
                respuesta = in.readLine();

            } catch (IOException e) {
                respuesta = "Error: " + e.getMessage();
            }

            // Actualizar la UI correctamente
            final String finalRespuesta = respuesta;
            runOnUiThread(() -> textView.setText(finalRespuesta));
            if (finalRespuesta.equals("Acceso Exitoso")){
                Intent intent=new Intent(MainActivity.this,Lista.class);
                startActivity(intent);
            }
        }
    }
}