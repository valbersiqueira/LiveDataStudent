package br.com.valber.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainTwoActivity extends AppCompatActivity {

    private PessoaViewModel viewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;
    public static final Integer ADD_PESSOA_REQUESTE = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        swipeRefreshLayout = findViewById(R.id.swip_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

//        floatingActionButton = findViewById(R.id.float_button);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(MainTwoActivity.this, AddPessoaActivity.class), ADD_PESSOA_REQUESTE);
//            }
//        });

        recyclerView = findViewById(R.id.recy_pessoa);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final PessoaAdapter adapter = new PessoaAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(PessoaViewModel.class);
        viewModel.getAll().observe(this, new Observer<List<Pessoa>>() {
            @Override
            public void onChanged(@Nullable List<Pessoa> pessoas) {
                adapter.submitList(pessoas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getPessoa(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListem(new PessoaAdapter.onClickItemListen() {
            @Override
            public void intemclick(final Pessoa pessoa) {
                Intent data = new Intent(MainTwoActivity.this, AddPessoaActivity.class);
                data.putExtra(AddPessoaActivity.PESSOA_OBJ, pessoa);
                startActivityForResult(data, ADD_PESSOA_REQUESTE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_pessoa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivityForResult(new Intent(MainTwoActivity.this,
                        AddPessoaActivity.class), ADD_PESSOA_REQUESTE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PESSOA_REQUESTE && resultCode == RESULT_OK) {
            Pessoa pessoa = data.getExtras().getParcelable(AddPessoaActivity.PESSOA_OBJ);
            if (pessoa.getId() != 0)
                viewModel.update(pessoa);
            else
                viewModel.insert(pessoa);
        }
    }
}
