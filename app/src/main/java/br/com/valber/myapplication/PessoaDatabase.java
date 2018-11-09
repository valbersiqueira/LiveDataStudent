package br.com.valber.myapplication;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Pessoa.class} , version = 2)
public abstract class PessoaDatabase extends RoomDatabase {

    private static PessoaDatabase instance;

    public abstract PessoaDao pessoaDao();

    public static synchronized PessoaDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PessoaDatabase.class,
                    "pessoa_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCalback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsetAsynck(instance).execute();
        }
    };

    public static class InsetAsynck extends AsyncTask<Void, Void, Void>{

        private PessoaDao db;

        public InsetAsynck(PessoaDatabase db) {
            this.db = db.pessoaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.insert(new Pessoa("Teste 1", "Teste sobrenome 1", "123", 2));
            db.insert(new Pessoa("Teste 2", "Teste sobrenome 2", "123", 3));
            db.insert(new Pessoa("Teste 3", "Teste sobrenome 3", "123", 4));
            return null;
        }
    }
}
