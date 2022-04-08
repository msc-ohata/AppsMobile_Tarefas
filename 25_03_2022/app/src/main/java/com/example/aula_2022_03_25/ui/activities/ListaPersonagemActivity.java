package com.example.aula_2022_03_25.ui.activities;

import static com.example.aula_2022_03_25.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aula_2022_03_25.R;
import com.example.aula_2022_03_25.dao.PersonagemDAO;
import com.example.aula_2022_03_25.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagem";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagens);
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }

    private void configuraFabNovoPersonagem(){
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormulario();
            }
        });
    }

    private void abreFormulario(){
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        atualizaPersonagem();
    }

    private void atualizaPersonagem(){
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover);
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_personagem_menu_remover){
            new AlertDialog.Builder(this)
                .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagem){
        listaDePersonagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido){
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    private void configuraAdapter(ListView listaDePersonagens){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}
