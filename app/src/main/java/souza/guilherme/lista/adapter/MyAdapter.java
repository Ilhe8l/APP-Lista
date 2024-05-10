package souza.guilherme.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import souza.guilherme.lista.R;
import souza.guilherme.lista.activity.MainActivity;
import souza.guilherme.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // Referência à atividade principal
    MainActivity mainActivity;

    // Lista de itens a serem exibidos
    List<MyItem> itens;

    // Construtor da classe MyAdapter
    public MyAdapter(MainActivity mainActivity, List<MyItem> itens){
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    // Método chamado quando o RecyclerView precisa de um novo ViewHolder para representar um item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da lista
        // Usado para ler o arquivo xml de layout do item e então criar os elementos de interface propriamente ditos
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v);
    }

    // Método chamado para atualizar a exibição de um item na posição fornecida
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Obtém o item na posição especificada
        MyItem myItem = itens.get(position);
        // Obtém a referência para a visualização do item
        // Guarda os itens de interface criados na execução de onCreateViewHolder;
        View v = holder.itemView;

        // Obtém e Define imagem do item
        ImageView imvfoto = v.findViewById(R.id.imvPhoto);
        imvfoto.setImageBitmap(myItem.photo);

        // Obtém e Define o título do item
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        // Obtém e Define a descrição do item
        TextView tvdesc = v.findViewById(R.id.tvDesc);
        tvdesc.setText(myItem.description);
    }

    // Método chamado para obter o número total de itens na lista
    @Override
    public int getItemCount() {
        return itens.size();
    }

    // Classe interna ViewHolder para representar cada item da lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // Construtor da classe MyViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
