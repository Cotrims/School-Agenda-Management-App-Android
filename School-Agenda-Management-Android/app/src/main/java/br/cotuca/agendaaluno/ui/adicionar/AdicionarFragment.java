package br.cotuca.agendaaluno.ui.adicionar;

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

import java.security.spec.ECField;

import br.cotuca.agendaaluno.Aluno;
import br.cotuca.agendaaluno.MainActivity;
import br.cotuca.agendaaluno.R;
import br.cotuca.agendaaluno.RetrofitConfig;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AdicionarFragment extends Fragment {

    EditText edtRa, edtNome, edtEmail;
    Button btnIncluir, btnLimpar;
    TextView tvResposta;
    private AdicionarViewModel adicionarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adicionarViewModel =
                ViewModelProviders.of(this).get(AdicionarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionar, container, false);
        edtRa = (EditText) root.findViewById(R.id.edt_ra);
        edtNome = (EditText) root.findViewById(R.id.edt_nome);
        edtEmail = (EditText) root.findViewById(R.id.edt_email);
        tvResposta = (TextView) root.findViewById(R.id.tvResposta);

        btnIncluir = (Button)root.findViewById(R.id.btn_incluir);
        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incluirAluno();
            }
        });

        btnLimpar = (Button)root.findViewById(R.id.btn_limpar);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtEmail.setText("");
                edtNome.setText("");
                edtRa.setText("");
            }
        });

        return root;
    }

    public void incluirAluno(){
        Aluno aluno = null;
        try{
            aluno = new Aluno(edtRa.getText().toString(),
                            edtNome.getText().toString(),
                            edtEmail.getText().toString());
        }
        catch (Exception ex){}

        if(aluno == null){
            Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = new RetrofitConfig().getService().incluirAluno(aluno);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()) //  precisa do isSuccess se a gente der exceção no node
                {
                    if(response.body() == null)
                    {
                        tvResposta.setText("");
                        tvResposta.append("Aluno incluído!");
                        btnLimpar.callOnClick();
                    }
                    else
                        tvResposta.setText("RA " + edtRa.getText() + " já existente");
                }
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro na inclusão", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
    }
}