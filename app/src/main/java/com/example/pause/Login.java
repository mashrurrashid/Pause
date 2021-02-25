package com.example.pause;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    String user,passwd;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.loginpage_username);
        password = (EditText) findViewById(R.id.loginpage_passwd);
        login = (Button) findViewById(R.id.login_next);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

    }

    private void Validate() {
        user = username.getText().toString();
        passwd = password.getText().toString();

        if(TextUtils.isEmpty(user)){
            Toast.makeText(this, "Enter A Valid Username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passwd)){
            Toast.makeText(this, "Enter A Valid Password", Toast.LENGTH_SHORT).show();
        }

        CheckCredentials(user, passwd);
    }

    private void CheckCredentials(final String user, final String passswd) {

        //Toast.makeText(this, user+"  " +passswd, Toast.LENGTH_SHORT).show();
        DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(user).exists()){


                    String passval = (String) snapshot.child("Users").child(user).child("passwd").getValue();

                    if (passval.equals(passswd)){
                        Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Home.class));
                    }
                    else{
                        Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Login.this, "No Data found by this username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}