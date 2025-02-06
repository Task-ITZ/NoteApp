package com.example.noteApp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteApp.dataLocal.DataLocalManager;
import com.example.noteApp.databinding.LayoutDialogBinding;
import com.example.noteApp.model.Note;
import com.example.noteApp.modelView.NoteViewModel;
import com.example.noteApp.myInterface.IClickItemNoteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton fab ;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Note> noteList = getIntent().getParcelableArrayListExtra("notes");
        if (noteList == null) noteList = new ArrayList<>();

        noteAdapter = new NoteAdapter(noteList, null);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);


    }
    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noteViewModel = new NoteViewModel();

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



        btnSave.setOnClickListener(view -> {
            List<Note> listNotes = DataLocalManager.getListNotes();
            listNotes.add(new Note(noteViewModel.getTitle()));
            DataLocalManager.setListNotes(listNotes);

            noteAdapter.updateNotes(new ArrayList<>(listNotes));
            noteViewModel.setTitle("");
            dialogBinding.invalidateAll();
            dialog.dismiss();

            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("updatedNotes", new ArrayList<>(listNotes));
            setResult(RESULT_OK, resultIntent);
        });
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedNotes", new ArrayList<>(noteAdapter.getNotes()));
        setResult(RESULT_OK, resultIntent);

        super.onBackPressed();
    }

}