package br.cotuca.agendaaluno.ui.editar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import br.cotuca.agendaaluno.Aluno;
import br.cotuca.agendaaluno.R;
import br.cotuca.agendaaluno.RetrofitConfig;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditarFragment extends Fragment {

    private EditarViewModel editarViewModel;
    EditText edtRa, edtNome, edtEmail;
    Button btnAlterar, btnDeletar, btnBuscar;
    TextView tvResposta;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        editarViewModel =
                ViewModelProviders.of(this).get(EditarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_editar, container, false);

        edtRa = (EditText) root.findViewById(R.id.edt_ra);
        edtNome = (EditText) root.findViewById(R.id.edt_nome);
        edtEmail = (EditText) root.findViewById(R.id.edt_email);
        tvResposta = (TextView) root.findViewById(R.id.tvResposta);

        btnAlterar = (Button) root.findViewById(R.id.btn_editar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alterarAluno(edtRa.getText().toString());
            }
        });

        btnDeletar = (Button) root.findViewById(R.id.btn_excluir);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { deletarAluno(edtRa.getText().toString());
            }
        });

        btnBuscar = (Button)root.findViewById(R.id.btn_buscar);
        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { selecionarByRa(edtRa.getText().toString());
            }
        });

        return root;
    }

    public void selecionarByRa(String ra)
    {
        if (ra == null || ra.equals(""))
        {
            Toast.makeText(getActivity(), "Insira o RA", Toast.LENGTH_LONG).show();
            return;
        }

        Call<Aluno> call = new RetrofitConfig().getService().selecionarByRa(ra);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Aluno aluno = response.body();

                    if(aluno != null)
                    {
                        edtRa.setText(aluno.getRa());
                        edtNome.setText(aluno.getNome());
                        edtEmail.setText(aluno.getEmail());
                        tvResposta.setText("");
                    }
                    else
                        tvResposta.setText("Aluno não encontrado");
                }
                else
                    Toast.makeText(getActivity(), "Erro ao buscar pelo alune", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deletarAluno(String ra)
    {
        if (ra == null || ra.equals(""))
        {
            Toast.makeText(getActivity(), "Insira o RA", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().excluirAluno(ra);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess())
                    if(response.raw().code() == 204)
                        tvResposta.setText("RA " + edtRa.getText() + " inexistente!");
                    else
                    {
                        tvResposta.setText("Aluno excluído!");
                        edtEmail.setText("");
                        edtNome.setText("");
                        edtRa.setText("");
                    }
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro ao deletar", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void alterarAluno(String ra)
    {
        Aluno aluno = null;
        try{
            aluno = new Aluno(ra, edtNome.getText().toString(), edtEmail.getText().toString());
        }
        catch (Exception ex) {}

        if(aluno == null){
            Toast.makeText(getActivity(), "Há campos vazios!", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().alterarAluno(ra, aluno);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess())
                    if(response.raw().code() == 204)
                        tvResposta.setText("RA " + edtRa.getText() + " inexistente!");
                    else
                        tvResposta.setText("Aluno alterado!");
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro ao alterar", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
    }
}