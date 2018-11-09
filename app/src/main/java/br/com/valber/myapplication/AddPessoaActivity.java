package br.com.valber.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

public class AddPessoaActivity extends AppCompatActivity {


    private EditText nomeEdt;
    private EditText sobrenomeEdt;
    private EditText cpfEdt;
    private EditText idadeEdt;
    public static final String PESSOA_OBJ = "PESSOA_OBJ";
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pessoa);

        nomeEdt = findViewById(R.id.nome_edt);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        sobrenomeEdt = findViewById(R.id.sobrenome_edt);
        cpfEdt = findViewById(R.id.cpf_edt);
        idadeEdt = findViewById(R.id.idade_edt);

        Bundle data = getIntent().getExtras();
        if (data != null && data.getParcelable(PESSOA_OBJ) != null) {
            pessoa = data.getParcelable(PESSOA_OBJ);
            nomeEdt.setText(pessoa.getNome());
            sobrenomeEdt.setText(pessoa.getSobrenome());
            cpfEdt.setText(pessoa.getCpf());
            idadeEdt.setText(String.valueOf(pessoa.getIdade()));
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (pessoa != null)
            setTitle("Editar Pessoa");
        else
            setTitle("Adicionar Pessoa");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_salvar:
                salvarPessoa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvarPessoa() {

        String nome = String.valueOf(nomeEdt.getText());
        String sobrenome = String.valueOf(sobrenomeEdt.getText());
        String cpf = String.valueOf(cpfEdt.getText());
        int idade = Integer.parseInt(String.valueOf(idadeEdt.getText()));
        Intent intent = new Intent();
        Pessoa pessoaPut = new Pessoa(nome, sobrenome, cpf, idade);
        pessoaPut.setId(pessoa != null ? pessoa.getId() : 0);
        intent.putExtra(PESSOA_OBJ, pessoaPut);
        setResult(RESULT_OK, intent);
        finish();
    }

}
