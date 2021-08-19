package br.cotuca.agendaaluno;

import com.squareup.okhttp.ResponseBody;
import java.util.List;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface Service {

    @GET("get")
    Call<List<Aluno>> selecionaTudo();

    @POST("post")
    Call<Aluno> incluirAluno(@Body Aluno aluno);

    @GET("get/{ra}")
    Call<Aluno> selecionarByRa(@Path("ra") String ra);

    @PUT("put/{ra}")
    Call<Aluno> alterarAluno(@Path("ra") String ra, @Body Aluno aluno);

    @DELETE("delete/{ra}")
    Call<Aluno> excluirAluno(@Path("ra") String ra);
}