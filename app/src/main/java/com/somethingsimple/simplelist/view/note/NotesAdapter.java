package com.somethingsimple.simplelist.view.note;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.databinding.NoteItemBinding;
import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.swipeinteractions.ItemTouchHelperActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>
        implements ItemTouchHelperActions {

    private List<Note> mNotes;
    private List<Note> mDeletedNotes;
    private int mDeletedPosition;
    private LayoutInflater mInflater;

    public NotesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mDeletedNotes = new ArrayList<>();
    }

    @BindingAdapter("strikethrough")
    public static void strikethrough(EditText editText, boolean strike) {
        if (strike)
            editText.setPaintFlags(editText.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            editText.setPaintFlags(editText.getPaintFlags()
                    & Paint.STRIKE_THRU_TEXT_FLAG);
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

    public String getNotesForSend() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Note note : getNotes()) {
            stringBuilder.append(note.toFormattedString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public List<Note> getDeletedNotes() {
        return mDeletedNotes;
    }

    public void addNoteCheckable(long folderId) {
        mNotes.add(new Note(folderId, mNotes.size(), true));
        notifyDataSetChanged();
    }

    public void addNote(long folderId) {
        mNotes.add(new Note(folderId, mNotes.size(), false));
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mNotes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mNotes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mDeletedNotes.add(mNotes.get(position));
        mDeletedPosition = position;
        mNotes.remove(position);
        notifyItemRemoved(position);
    }

    public void undoDelete() {
        mNotes.add(mDeletedPosition, mDeletedNotes.get(mDeletedNotes.size() - 1));
        mDeletedNotes.remove(mDeletedNotes.size() - 1);
        notifyItemInserted(mDeletedPosition);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        final NoteItemBinding noteBinding;

        NotesViewHolder(NoteItemBinding binding) {
            super(binding.getRoot());
            noteBinding = binding;
        }
    }
}
