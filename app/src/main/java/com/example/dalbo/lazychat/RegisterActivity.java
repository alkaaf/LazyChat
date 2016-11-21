package com.example.dalbo.lazychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class RegisterActivity extends AppCompatActivity {
    TextView loginLink;
    EditText iEmail, iPassword;
    Button bRegister;
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview_layout);
        loginLink = (TextView) findViewById(R.id.ologinlink);
        iEmail = (EditText) findViewById(R.id.iemail);
        iPassword = (EditText) findViewById(R.id.ipassword);
        bRegister = (Button) findViewById(R.id.bregister);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setTitle("Tunggu sebentar");
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(iEmail.getText().toString().isEmpty() || iPassword.getText().toString().isEmpty())) {
                    pd.show();
                    auth.createUserWithEmailAndPassword(iEmail.getText().toString(), iPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this,"Isi data dengan benar",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
