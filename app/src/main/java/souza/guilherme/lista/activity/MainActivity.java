package souza.guilherme.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import souza.guilherme.lista.R;
import souza.guilherme.lista.adapter.MyAdapter;
import souza.guilherme.lista.model.MainActivityViewModel;
import souza.guilherme.lista.model.MyItem;
import souza.guilherme.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    // Declaração da constante para identificar a solicitação de um novo item
    static int NEW_ITEM_REQUEST = 1;

    // Lista para armazenar os itens
    List<MyItem> itens = new ArrayList<>();

    // Adaptador para o RecyclerView
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilita a compatibilidade com bordas estendidas (Edge-to-Edge)
        EdgeToEdge.enable(this);

        // Define o layout da atividade como activity_main
        setContentView(R.layout.activity_main);

        // Configura o comportamento das janelas de sistema (systemBars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtém o botão (ícone) responsável pela adição de novos itens
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);

        // Define o comportamento do FloatingActionButton ao ser clicado
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent para abrir a atividade de criação de um novo item
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                // Inicia a atividade esperando um resultado
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        // Obtém a referência para o RecyclerView responsável por exibir os itens
        RecyclerView rvItens = findViewById(R.id.rvItens);

        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        // Cria e configura o adaptador para o RecyclerView
        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);
        rvItens.setHasFixedSize(true);

        // Define o layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        // Adiciona um divisor entre os itens na lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica se o resultado é da solicitação de um novo item
        if(requestCode == NEW_ITEM_REQUEST){
            // Verifica se a operação foi bem sucedida
            if(resultCode == Activity.RESULT_OK){
                // Cria um novo item com os dados fornecidos pela atividade de criação
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoUri = data.getData();

                try{
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoUri, 100, 100);
                    myItem.photo = photo;
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();

                // Adiciona o novo item à lista
                itens.add(myItem);
                // Notifica o adaptador que um novo item foi inserido
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}
