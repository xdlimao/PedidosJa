package com.jalimpeza.pedidosja.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jalimpeza.pedidosja.R;

public class PasswordActivity extends AppCompatActivity {


    //Botão do teclado de confirmar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Botão do teclado de confirmar
        EditText editTextPassword = findViewById(R.id.editTextNumberPassword);
        Button validadeButton = findViewById(R.id.validadebutton);


        editTextPassword.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                validadeButton.performClick();
                return true;
            }
            return false;
        });

        validadeButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("password")
                    .document("passwordfield")
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) { //não existe else aqui porque o documento sempre vai ser passwordfield
                            // Obter os campos do documento
                            String value = documentSnapshot.getString("value");
                            if (value.equals(editTextPassword.getText().toString())){
                                Intent intent = new Intent(PasswordActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(this, "Senha errada!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Erro: " + e.getMessage());
                    });
        });
    }
}