package com.example.proyectocobas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class Lista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);  // Asumiendo que este es tu archivo XML

        // Encuentra el RecyclerView en el layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Configura el LayoutManager para mostrar los elementos en lista
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de elementos que quieres mostrar
        List<String> listaComidas = Arrays.asList("Tarta", "Zanahoria", "Almeja", "Patata");

        // Crea el Adapter y asígnalo al RecyclerView
        MyAdapter adapter = new MyAdapter(listaComidas);
        recyclerView.setAdapter(adapter);
    }
}
