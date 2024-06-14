package souza.guilherme.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import souza.guilherme.lista.R;
import souza.guilherme.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    // Declaração da constante para identificar a solicitação de seleção de foto da galeria
    static int PHOTO_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilita a compatibilidade com bordas estendidas (Edge-to-Edge)
        EdgeToEdge.enable(this);

        // Define o layout da atividade como activity_new_item
        setContentView(R.layout.activity_new_item);

        // Configura o comportamento das janelas de sistema (systemBars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if(selectPhotoLocation != null){
            ImageView imvphotoPreview = findViewById(R.id.imvPhotoPreview);
            imvphotoPreview.setImageURI(selectPhotoLocation);
        }

        // Obtém o botão imgCl, responsável pela seleção de fotos
        ImageButton imgCl = findViewById(R.id.imbCl);

        // Define o comportamento do botão de seleção de fotos ao ser clicado
        imgCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um Intent para abrir a galeria de fotos do dispositivo
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Define o tipo de arquivo a ser selecionado como imagem
                photoPickerIntent.setType("image/*");
                // Inicia a atividade esperando um resultado
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        // Obtém o botão de adicionar item
        Button btnAddItem = findViewById(R.id.btnAddItem);
        // Define o comportamento do botão de adicionar item ao ser clicado
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri photoSelected = vm.getSelectPhotoLocation();
                // Verifica se uma foto foi selecionada
                if(photoSelected == null){
                    // Se não, exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtém o título digitado pelo usuário
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                // Verifica se o título está vazio
                if(title.isEmpty()){
                    // Se estiver, exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtém a descrição digitada pelo usuário
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                // Verifica se a descrição está vazia
                if(description.isEmpty()){
                    // Se estiver, exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria um Intent para retornar os dados do novo item para a MainActivity
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                // Define o resultado como "OK" e inclui o Intent com os dados
                setResult(Activity.RESULT_OK, i);
                // Finaliza a atividade
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica se o resultado é da solicitação de seleção de foto
        if(requestCode == PHOTO_PICKER_REQUEST){
            // Verifica se a operação foi bem sucedida
            if(resultCode == Activity.RESULT_OK){
                // Obtém o URI da foto selecionada
                Uri photoSelected = data.getData();
                // Exibe a foto selecionada no ImageView de pré-visualização
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
                imvfotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectPhotoLocation(photoSelected);
            }
        }
    }
}