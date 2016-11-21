package com.example.dalbo.lazychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dalbo on 11/11/2016.
 */

public class LoginActivity extends AppCompatActivity {
    TextView registerLink;
    Button signIn;
    EditText email, password;
    FirebaseAuth auth;
    ProgressDialog pd;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview_layout);
        registerLink = (TextView) findViewById(R.id.oregisterLink);
        signIn = (Button) findViewById(R.id.blogin);
        email = (EditText) findViewById(R.id.iemail);
        password = (EditText) findViewById(R.id.ipassword);
        pd = new ProgressDialog(this);
        pd.setTitle("Tunggu sebentar...");
        auth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())) {
                    pd.show();
                    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();

                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this,"Isi data dengan benar",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // register link handling
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
    }
}