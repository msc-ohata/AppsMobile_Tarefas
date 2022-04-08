package com.example.aula_2022_03_25.ui.activities;

import static com.example.aula_2022_03_25.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.aula_2022_03_25.R;
import com.example.aula_2022_03_25.dao.PersonagemDAO;
import com.example.aula_2022_03_25.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class FormularioPersonagemActivity extends Activity {

    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final  String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId  = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagens);
        inicializacaoCampos();
        carregaPersonagem();
        //checaPermissoes();
    }

    private void carregaPersonagem(){
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_PERSONAGEM)){
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencherCampos();
        }
        else{
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    private void preencherCampos(){
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    private void finalizarFormulario(){
        preencherPersonagem();
        if(personagem.IdValido()){
            dao.edita(personagem);
            finish();
        }
        else{
            dao.salva(personagem);
        }
        finish();
    }

    private void inicializacaoCampos(){
        campoNome = findViewById(R.id.editText_nome);
        campoAltura = findViewById(R.id.editText_altura);
        campoNascimento = findViewById(R.id.editText_nascimento);

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    private void preencherPersonagem(){
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
    }
}
