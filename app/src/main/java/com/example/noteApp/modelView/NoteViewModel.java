package com.example.noteApp.modelView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.noteApp.BR;
import com.example.noteApp.dataLocal.DataLocalManager;
import com.example.noteApp.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoteViewModel extends BaseObservable {
    private String title;
    private List<Note> noteList;

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getTitle() {
        return title;
    }
}
