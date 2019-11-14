package com.example.chatfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar;
    private TextView cadastraConta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmail  =  findViewById(R.id.edit_email);
        editSenha  =  findViewById(R.id.edit_senha);
        btnEntrar = findViewById(R.id.btn_entrar);
        cadastraConta = findViewById(R.id.txt_criar_conta);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        cadastraConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }


}
