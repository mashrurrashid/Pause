package com.example.pause;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    EditText fname, email, uname, passwd;
    Button shoot;
    String fullname,address,username,pwd;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = (EditText) findViewById(R.id.Full_name);
        email = (EditText) findViewById(R.id.Email_address);
        uname = (EditText) findViewById(R.id.user_name);
        passwd = (EditText) findViewById(R.id.pass_word);


        shoot= (Button) findViewById(R.id.signup_btn);

        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = fname.getText().toString();
                address = email.getText().toString();
                username = uname.getText().toString();
                pwd = passwd.getText().toString();
                checkInput();
            }
        });


    }

    private void checkInput() {
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "Please enter your fullname.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty( address)){
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your username.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty( pwd)){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
        }
        else{

            //loadingbar.setTitle("Loading");
            //loadingbar.setMessage("Checking credentials, please wait");
            // loadingbar.setCanceledOnTouchOutside(false);
            // loadingbar.show();



            Validate(fullname, address, username, pwd);

        }

    }

    private void Validate(final String fname, final String email, final String uname, final String passwd) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        Toast.makeText(this, "entered validate method", Toast.LENGTH_SHORT).show();



        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(uname).exists()){
                    Toast.makeText(Signup.this, "This username is already registered.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup.this, "Please try again with a new one.", Toast.LENGTH_SHORT).show();
                    //loadingbar.dismiss();
                }

                else{
                    HashMap<String, Object> usermap = new HashMap<>();

                    usermap.put("fname", fname);
                    usermap.put("email", email);
                    usermap.put("passwd", passwd);
                    usermap.put("uname", uname);

                    rootRef.child("Users").child(uname).updateChildren(usermap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                                        //loadingbar.dismiss();

                                        Intent intent = new Intent(Signup.this, Login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Signup.this, "There was a network error.", Toast.LENGTH_SHORT).show();
                                        //loadingbar.dismiss();
                                    }
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}