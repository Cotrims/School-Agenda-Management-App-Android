package br.cotuca.agendaaluno;

import br.cotuca.agendaaluno.Service;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public  RetrofitConfig(){

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.92.1:3000/api/alunos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Service getService(){
        return this.retrofit.create(Service.class);
    }
}