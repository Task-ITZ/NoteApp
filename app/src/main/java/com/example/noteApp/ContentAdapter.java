package com.example.noteApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteApp.model.Note;
import com.example.noteApp.myInterface.IClickItemNoteListener;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<String> contents;

    public ContentAdapter(List<String> contents) {
        this.contents = contents;
    }

    public void updateContents(List<String> updatedContents) {
        this.contents = updatedContents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (contents == null || position < 0 || position >= contents.size()) {
            return;
        }
        String content = contents.get(position);
        if (content == null) {
            return;
        }
        holder.number.setText(String.valueOf(position + 1));
        holder.noteTitle.setText(content);

    }

    public List<String> getContents() {
        return contents;
    }

    @Override
    public int getItemCount() {
        if (contents != null) {
            return contents.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView number;
        private final LinearLayout layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.item_title);
            number = itemView.findViewById(R.id.item_number);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
