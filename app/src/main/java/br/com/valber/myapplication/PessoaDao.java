package br.com.valber.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PessoaDao {

    @Insert
    void insert(Pessoa pessoa);

    @Update
    void update(Pessoa pessoa);

    @Delete
    void delete(Pessoa pessoa);

    @Query("DELETE FROM pessoa_table")
    void deleteAll();

    @Query("SELECT * FROM pessoa_table ORDER BY nome")
    LiveData<List<Pessoa>> getAll();
}
