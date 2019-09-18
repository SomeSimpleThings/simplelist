package com.somethingsimple.simplelist.view.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.databinding.NoteItemBinding;
import com.somethingsimple.simplelist.db.entity.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    private List<Note> mNotes;
    private LayoutInflater mInflater;

    public NotesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        NoteItemBinding binding =
                DataBindingUtil.inflate(mInflater, R.layout.note_item, parent, false);
        return new NotesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.noteBinding.setNote(current);
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        NoteItemBinding noteBinding;

        public NotesViewHolder(NoteItemBinding binding) {
            super(binding.getRoot());
            noteBinding = binding;
        }
    }
}
