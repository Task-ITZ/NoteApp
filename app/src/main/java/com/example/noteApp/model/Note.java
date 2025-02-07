package com.example.noteApp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Note implements Parcelable {
    private String title;
    private List<String> listContent;

    protected Note(Parcel in) {
        title = in.readString();
        listContent = in.createStringArrayList();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public String toString() {
        return "Note{" +
                "listContent=" + listContent +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeStringList(listContent);
    }

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
        this.listContent = listContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Note(String title, List<String> listContent) {
        this.listContent = listContent;
        this.title = title;
    }
}
