package br.cotuca.agendaaluno.ui.lista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import br.cotuca.agendaaluno.Aluno;
import br.cotuca.agendaaluno.AlunoAdapter;
import br.cotuca.agendaaluno.R;
import br.cotuca.agendaaluno.RetrofitConfig;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListaFragment extends Fragment {

    private ListaViewModel listaViewModel;
    List<Aluno> listaAluno;
    ListView lvAluno;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listaAluno = new ArrayList<Aluno>();
        listaViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lista, container, false);
        lvAluno = (ListView) root.findViewById(R.id.lvAlunos);
        consultarProdutoTudo();
        return root;
    }

    protected void atualizarView(){
        AlunoAdapter adapter = new AlunoAdapter(getActivity(), R.layout.aluno_item, listaAluno);
        lvAluno.setAdapter(adapter);
    }

    public void consultarProdutoTudo(){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Aluno>> call = new RetrofitConfig().getService().selecionaTudo();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Response<List<Aluno>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaAluno = response.body();
                    atualizarView();
                }
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro ao recuperar os alunos", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }

}