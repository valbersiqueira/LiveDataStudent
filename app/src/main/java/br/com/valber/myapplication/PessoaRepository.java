package br.com.valber.myapplication;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PessoaRepository {

    private PessoaDao pessoaDao;

    private LiveData<List<Pessoa>> getAll;

    public PessoaRepository(Application application) {
        PessoaDatabase pessoaDatabase = PessoaDatabase.getInstance(application);
        pessoaDao = pessoaDatabase.pessoaDao();
        getAll = pessoaDao.getAll();
    }

    public void insert(Pessoa pessoa) {
        new InsetPessoaAsynck(pessoaDao).execute(pessoa);
    }

    public void update(Pessoa pessoa) {
        new UpdatePessoaAsynck(pessoaDao).execute(pessoa);
    }

    public void delete(Pessoa pessoa) {
        new DeletePessoaAsynck(pessoaDao).execute(pessoa);
    }

    public void deleteAll() {
        new DeleteAllPessoaAsynck(pessoaDao).execute();
    }

    public LiveData<List<Pessoa>> getGetAll() {
        return this.getAll;
    }

    public static class InsetPessoaAsynck extends AsyncTask<Pessoa, Void, Void> {
        private PessoaDao pessoaDao;

        public InsetPessoaAsynck(PessoaDao pessoaDao) {
            this.pessoaDao = pessoaDao;
        }

        @Override
        protected Void doInBackground(Pessoa... pessoas) {
            this.pessoaDao.insert(pessoas[0]);
            return null;
        }
    }

    public static class UpdatePessoaAsynck extends AsyncTask<Pessoa, Void, Void> {
        private PessoaDao pessoaDao;

        public UpdatePessoaAsynck(PessoaDao pessoaDao) {
            this.pessoaDao = pessoaDao;
        }

        @Override
        protected Void doInBackground(Pessoa... pessoas) {
            this.pessoaDao.update(pessoas[0]);
            return null;
        }
    }

    public static class DeletePessoaAsynck extends AsyncTask<Pessoa, Void, Void> {
        private PessoaDao pessoaDao;

        public DeletePessoaAsynck(PessoaDao pessoaDao) {
            this.pessoaDao = pessoaDao;
        }

        @Override
        protected Void doInBackground(Pessoa... pessoas) {
            this.pessoaDao.delete(pessoas[0]);
            return null;
        }
    }

    public static class DeleteAllPessoaAsynck extends AsyncTask<Void, Void, Void> {
        private PessoaDao pessoaDao;

        public DeleteAllPessoaAsynck(PessoaDao pessoaDao) {
            this.pessoaDao = pessoaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.pessoaDao.deleteAll();
            return null;
        }
    }
}
