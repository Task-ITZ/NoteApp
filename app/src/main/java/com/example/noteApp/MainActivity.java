package com.example.noteApp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteApp.dataLocal.DataLocalManager;
import com.example.noteApp.databinding.ActivityMainBinding;
import com.example.noteApp.databinding.LayoutDialogBinding;
import com.example.noteApp.model.Note;
import com.example.noteApp.modelView.NoteViewModel;
import com.example.noteApp.myInterface.IClickItemNoteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab ;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
    private static final int REQUEST_CODE_DETAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        noteViewModel = new NoteViewModel();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });

//        DataLocalManager.init(getApplicationContext());
//        List<Note> note = DataLocalManager.getListNotes();
//        Set<String> note = new HashSet<>();
//        note.add("aaa");
//        note.add("bbb");


        DataLocalManager.init(getApplicationContext());

        List<Note> listNotes = DataLocalManager.getListNotes();
        DataLocalManager.setListNotes(listNotes);

        recyclerView = findViewById(R.id.recyclerView);



        noteAdapter = new NoteAdapter(listNotes, new IClickItemNoteListener() {
            @Override
            public void onClickItemNote(Note note) {
                onClickGoToDetail(note);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

    }

    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutDialogBinding dialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.layout_dialog, null, false);

        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.setNoteViewModel(noteViewModel);
        dialogBinding.setLifecycleOwner(this);

        Window window = dialog.getWindow();
        if(window == null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        Button btnSave = dialog.findViewById(R.id.btn_save);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Note> listNotes = DataLocalManager.getListNotes();
                listNotes.add(new Note(noteViewModel.getTitle(), noteViewModel.getListContent()));
                DataLocalManager.setListNotes(listNotes);

                List<Note> updatedNotes = new ArrayList<>();
                for (Note note : listNotes) {
                    updatedNotes.add(note);
                }
                noteAdapter.updateNotes(updatedNotes);
                noteViewModel.setTitle("");
                dialogBinding.invalidateAll();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> updatedNotes = DataLocalManager.getListNotes();
        if (updatedNotes != null) {
            noteAdapter.updateNotes(updatedNotes);
        }
    }

    private void onClickGoToDetail(Note note) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DETAIL && resultCode == RESULT_OK && data != null) {
            ArrayList<Note> updatedNotes = data.getParcelableArrayListExtra("updatedNotes");
            if (updatedNotes != null) {
                noteAdapter.updateNotes(updatedNotes);
            }
        }
    }

}