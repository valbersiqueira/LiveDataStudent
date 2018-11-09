package br.com.valber.myapplication;

import android.annotation.TargetApi;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@Entity(tableName = "pessoa_table")
public class Pessoa implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private String sobrenome;
    private String cpf;
    private int idade;

    public Pessoa(String nome, String sobrenome, String cpf, int idade) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.idade = idade;
    }


    protected Pessoa(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        sobrenome = in.readString();
        cpf = in.readString();
        idade = in.readInt();
    }

    public static final Creator<Pessoa> CREATOR = new Creator<Pessoa>() {
        @Override
        public Pessoa createFromParcel(Parcel in) {
            return new Pessoa(in);
        }

        @Override
        public Pessoa[] newArray(int size) {
            return new Pessoa[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(sobrenome);
        dest.writeString(cpf);
        dest.writeInt(idade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return id == pessoa.id;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
