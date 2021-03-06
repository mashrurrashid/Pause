package com.example.pause;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.validation.Validator;

import io.paperdb.Paper;


public class CreateNote extends AppCompatActivity {

    EditText createNoteTitle;
    EditText createNoteDescription;
    Button postBtn;
    DatabaseReference NoteRef;
    ProgressDialog loadingbar;
    SimpleDateFormat dateFormat;
    String dateOfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        createNoteTitle = findViewById(R.id.createnote_title);
        createNoteDescription = findViewById(R.id.createnote_description);
        postBtn = findViewById(R.id.createnote_save);


        Paper.init(this);
        dateOfTime = getIntent().getStringExtra("message");
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

    }

    private void Validate() {
        String title = createNoteTitle.getText().toString();
        String description = createNoteDescription.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Please give a title.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please give a description.", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar = new ProgressDialog(this);
            loadingbar.setTitle("Posting");
            loadingbar.setMessage("Creating your post. Please wait.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            CreatePost(title, description);


        }
    }

    private void CreatePost(final String title, final String description) {
        NoteRef = FirebaseDatabase.getInstance().getReference();

        NoteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Note").child("mashrur").child(title).exists()) {
                    loadingbar.dismiss();
                    Toast.makeText(CreateNote.this, "There is already a post created with same title. Try again with a new one.", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> notemap = new HashMap<String, Object>();
                    Date date = new Date();
                    String datetime = String.valueOf(date.getTime());


                    notemap.put("title", title);
                    notemap.put("description", description);
                    notemap.put("datetime", datetime);
                    notemap.put("dateOfTime", dateOfTime);

                    NoteRef.child("Note").child("mashrur").child(dateOfTime).child(datetime).setValue(notemap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismiss();
                                        createNoteTitle.clearComposingText();
                                        createNoteDescription.clearComposingText();
                                        Toast.makeText(CreateNote.this, "Note created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CreateNote.this, Notes.class);
                                        intent.putExtra("message",dateOfTime);
                                        startActivity(intent);
                                    } else {
                                        loadingbar.dismiss();
                                        Toast.makeText(CreateNote.this, "Network error occured.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}