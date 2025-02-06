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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;
    private IClickItemNoteListener iClickItemNoteListener;

    public NoteAdapter(List<Note> notes, IClickItemNoteListener listener) {
        this.notes = notes;
        this.iClickItemNoteListener = listener;
    }
    public void updateNotes(List<Note> updatedNotes) {
        this.notes = updatedNotes;
        notifyDataSetChanged();  // Cập nhật lại RecyclerView
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
        Note note = notes.get(position);
        if (note == null) {
            return;
        }
        holder.number.setText(String.valueOf(position + 1));
        holder.noteTitle.setText(note.getTitle());

        if (iClickItemNoteListener != null) {
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemNoteListener.onClickItemNote(note);
                }
            });
        } else {
            holder.layoutItem.setOnClickListener(null);
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public int getItemCount() {
        if (notes != null) {
            return notes.size();
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
