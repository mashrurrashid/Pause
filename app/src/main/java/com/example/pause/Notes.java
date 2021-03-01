package com.example.pause;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo1.ViewHandler.NoteViewHandler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Notes extends AppCompatActivity {
    RecyclerView noteRecyclerView;
    DatabaseReference NoteRef;
    DatabaseReference DelNoteRef;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab;
    String message;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteRecyclerView = findViewById(R.id.recycler_note);
        fab = findViewById(R.id.newpostbtn);
        Paper.init(this);
        message = getIntent().getStringExtra("message");
        NoteRef = FirebaseDatabase.getInstance().getReference().child("Note").child("mashrur").child(message);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        noteRecyclerView.setHasFixedSize(false);
        noteRecyclerView.setLayoutManager(layoutManager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNote.class);
                intent.putExtra("message",message);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Note> options = new
                FirebaseRecyclerOptions.Builder<Note>().setQuery(NoteRef, Note.class)
                .build();

        FirebaseRecyclerAdapter<Note, NoteViewHandler> adapter = new
                FirebaseRecyclerAdapter<Note, NoteViewHandler>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NoteViewHandler holder, int position, @NonNull final Note model) {

                        holder.noteTitle.setText(model.getTitle());
                        holder.noteDescription.setText(model.getDescription());


                        holder.clearBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(Notes.this, model.toString(), Toast.LENGTH_SHORT).show();
                                DelNoteRef = FirebaseDatabase.getInstance().getReference();
                                DelNoteRef.child("Note").child("mashrur").child(model.getDateOfTime()).child(model.getDatetime()).removeValue();
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public NoteViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_handler, parent ,false);
                        NoteViewHandler holder = new NoteViewHandler(view);
                        return holder;
                    }
                };

        noteRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
