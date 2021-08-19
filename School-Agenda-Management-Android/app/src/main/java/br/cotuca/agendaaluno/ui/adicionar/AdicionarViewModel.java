package br.cotuca.agendaaluno.ui.adicionar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdicionarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is adicionar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}