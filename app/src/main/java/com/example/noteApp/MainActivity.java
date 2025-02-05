package com.example.noteApp;

import android.app.Dialog;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        noteViewModel = new NoteViewModel();
        activityMainBinding.setNoteViewModel(noteViewModel);
        activityMainBinding.setLifecycleOwner(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });

        DataLocalManager.init(getApplicationContext());
        Set<String> note = DataLocalManager.getListNotes();
//        Set<String> note = new HashSet<>();
//        note.add("aaa");
//        note.add("bbb");


        DataLocalManager.init(getApplicationContext());
        DataLocalManager.setListNotes(note);
        Set<String> listNotes = DataLocalManager.getListNotes();

        recyclerView = findViewById(R.id.recyclerView);

        List<Note> notes = new ArrayList<>();
        for (String title : listNotes) {
            notes.add(new Note(title));
        }

        noteAdapter = new NoteAdapter(notes, new IClickItemNoteListener() {
            @Override
            public void onClickItemNote(Note note) {
                openDialog(Gravity.CENTER);
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

                Set<String> notes = DataLocalManager.getListNotes();
                notes.add(noteViewModel.getTitle());
                DataLocalManager.setListNotes(notes);

                List<Note> updatedNotes = new ArrayList<>();
                for (String title : notes) {
                    updatedNotes.add(new Note(title));
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

    private void onClickGoToDetail(Note note) {
        openDialog(Gravity.CENTER);
    }
}