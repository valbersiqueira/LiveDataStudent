package br.com.valber.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PessoaViewModel extends AndroidViewModel {

    private PessoaRepository repository;
    private LiveData<List<Pessoa>> allPessoas;

    public PessoaViewModel(@NonNull Application application) {
        super(application);

        repository = new PessoaRepository(application);
        allPessoas = repository.getGetAll();
    }

    public void insert(Pessoa pessoa){
        repository.insert(pessoa);
    }
    public void update(Pessoa pessoa){
        repository.update(pessoa);
    }
    public void delete(Pessoa pessoa){
        repository.delete(pessoa);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Pessoa>> getAll(){
        return repository.getGetAll();
    }

}
