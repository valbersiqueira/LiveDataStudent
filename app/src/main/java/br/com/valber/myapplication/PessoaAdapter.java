package br.com.valber.myapplication;


import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PessoaAdapter extends ListAdapter<Pessoa, PessoaAdapter.PessoaViewHolder> {

    private onClickItemListen onClickItemListen;

    protected PessoaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pessoa> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pessoa>() {
        @Override
        public boolean areItemsTheSame(Pessoa oldItem, Pessoa newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Pessoa oldItem, Pessoa newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public PessoaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_pessoa, viewGroup, false);
        return new PessoaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaViewHolder holder, int i) {
        Pessoa pessoa = getItem(i);
        holder.nome.setText(pessoa.getNome());
        holder.sobrenome.setText(pessoa.getSobrenome());
        holder.cpf.setText(pessoa.getCpf());
        holder.idade.setText(pessoa.getIdade() + "");

    }


    public Pessoa getPessoa(int positon) {
        return getItem(positon);
    }

    public class PessoaViewHolder extends RecyclerView.ViewHolder {
        private TextView nome, sobrenome, cpf, idade;

        public PessoaViewHolder(@NonNull View item) {
            super(item);

            nome = item.findViewById(R.id.nome_text);
            sobrenome = item.findViewById(R.id.sobrenome_text);
            cpf = item.findViewById(R.id.cpf_text);
            idade = item.findViewById(R.id.idade_text);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onClickItemListen != null && position != RecyclerView.NO_POSITION) {
                        onClickItemListen.intemclick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onClickItemListen {

        void intemclick(Pessoa pessoa);
    }

    public void setOnClickListem(onClickItemListen onClickListem) {
        this.onClickItemListen = onClickListem;
    }
}
