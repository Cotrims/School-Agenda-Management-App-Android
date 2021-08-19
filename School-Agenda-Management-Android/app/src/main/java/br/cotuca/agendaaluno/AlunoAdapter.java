package br.cotuca.agendaaluno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AlunoAdapter extends ArrayAdapter<Aluno> {

    Context context;
    int layoutResourceId;
    List<Aluno> dados;

    public AlunoAdapter(@NonNull Context context, int resource, @NonNull List<Aluno> dados) {
        super(context, resource, dados);

        //Informação global do ambiente. Define o resource usado
        this.context = context;

        //Id do layout que queremos usar de referência
        this.layoutResourceId = resource;

        //Lista de dados
        this.dados = dados;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            view = layoutinflater.inflate(layoutResourceId,parent,false);
        }


        TextView ra = (TextView) view.findViewById(R.id.tvRa);
        TextView nome = (TextView) view.findViewById(R.id.tvNome);
        TextView email = (TextView) view.findViewById(R.id.tvEmail);

        Aluno aluno = dados.get(position);
        ra.setText(aluno.getRa());
        nome.setText(aluno.getNome());
        email.setText(aluno.getEmail());

        return view;
    }
}
