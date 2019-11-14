package com.example.chatfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;


public class CadastroActivity extends AppCompatActivity {

    private EditText editEmail, editSenha, editNome;
    private Button btnCadastra, btnSelecionarFoto;
    private ImageView imageFoto;
    private Uri uriImagemSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        editNome = findViewById(R.id.edit_nome);
        btnCadastra = findViewById(R.id.btn_cadastra);
        btnSelecionarFoto = findViewById(R.id.btn_selecionar_foto);
        imageFoto = findViewById(R.id.img_foto);

        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Teste", editEmail.getText().toString());
                Log.d("Teste", editSenha.getText().toString());
                Log.d("Teste", editNome.getText().toString());
                registrarUsuario();
            }
        });

        btnSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirGaleriaFotos();

            }
        });
    }

    private void abrirGaleriaFotos(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == 0){
            Bitmap bitmap = null;
            try {

                if(data == null)
                    return;

                uriImagemSelecionada = data.getData();



                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImagemSelecionada);

                imageFoto.setImageDrawable(new BitmapDrawable(bitmap));
                btnSelecionarFoto.setAlpha(0);
            }catch (IOException ex){

                Toast.makeText(CadastroActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
            }
        }
    }

    private void registrarUsuario() {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String nome = editNome.getText().toString();

        if(isStringNulaOuVasia(email) || isStringNulaOuVasia(senha) || isStringNulaOuVasia(nome)){
            Toast.makeText(this, "Nome, email e senha não preenchidos!", Toast.LENGTH_LONG);
            return;
        }


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    String token = FirebaseInstanceId.getInstance().getToken();

                    String uId = task.getResult().getUser().getUid();
                    Log.i("Teste", task.getResult().getUser().getUid());
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG);
                    Usuario usuario = new Usuario(uId, editNome.getText().toString(), editSenha.getText().toString(), editEmail.getText().toString(), token);

                    FirebaseFirestore.getInstance().collection("Usuarios")
                            .add(usuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("Erro", documentReference.getId());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.e("Erro", e.getMessage());
                        }
                    });

                    Intent intent =  new Intent(CadastroActivity.this, FCMServico.class);
                    startService(intent);

                    //SalvarImagemFireStorage();
                }

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Erro", e.getMessage());
            }
        });
    }

    private void SalvarImagemFireStorage() {

        String nomeArquivo = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("imagem/" + nomeArquivo);

        ref.putFile(uriImagemSelecionada).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Teste", uri.toString());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e == null)
                    return;

                Log.e("Erro", e.getMessage());
            }
        });
    }

    private boolean isStringNulaOuVasia(String string) {

        return string == null || string.isEmpty();
    }
}
