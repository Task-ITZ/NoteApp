package com.example.noteApp.modelView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.noteApp.BR;
import com.example.noteApp.model.Note;

import java.util.List;

public class NoteViewModel extends BaseObservable {
    private String title;
    private List<Note> noteList;
    private List<String> listContent;

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
        this.listContent = listContent;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getTitle() {
        return title;
    }
}
