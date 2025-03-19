package com.example.proyectocobas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Iniciar la conexión en un hilo separado
        new ClientTask().execute();
    }

    private class ClientTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String ip = "10.0.2.2"; // Cambia esto si usas un dispositivo físico
            int puerto = 12345;
            String respuesta = "Sin respuesta";

            try (Socket socket = new Socket(ip, puerto);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("Ishak");
                respuesta = in.readLine();
            } catch (IOException e) {
                respuesta = "Error: " + e.getMessage();
            }

            return respuesta;

        }

        @Override
        protected void onPostExecute(String resultado) {
            textView.setText(resultado);
        }
    }
}
