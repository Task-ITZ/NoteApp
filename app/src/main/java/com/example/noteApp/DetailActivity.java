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
import android.widget.EditText;
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
    private ContentAdapter contentAdapter;
    private NoteAdapter noteAdapter;
    private Note selectedNote;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.title);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        selectedNote = getIntent().getParcelableExtra("note");
        title.setText(selectedNote.getTitle());
        contentAdapter = new ContentAdapter(selectedNote.getListContent());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });
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

        EditText editTextContent = dialog.findViewById(R.id.edt_content);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(view -> {
            String newContent = editTextContent.getText().toString().trim();

            if (!newContent.isEmpty()) {
                List<String> contents = selectedNote.getListContent();
                if (contents == null) {
                    contents = new ArrayList<>();
                }
                contents.add(newContent);
                selectedNote.setListContent(contents);

                List<Note> listNotes = DataLocalManager.getListNotes();
                if (listNotes == null) {
                    listNotes = new ArrayList<>();
                }
                for (int i = 0; i < listNotes.size(); i++) {
                    if (listNotes.get(i).getTitle().equals(selectedNote.getTitle())) {
                        listNotes.set(i, selectedNote);
                        break;
                    }
                }
                DataLocalManager.setListNotes(listNotes);

                contentAdapter.notifyDataSetChanged();
            }

            dialog.dismiss();
        });

        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}