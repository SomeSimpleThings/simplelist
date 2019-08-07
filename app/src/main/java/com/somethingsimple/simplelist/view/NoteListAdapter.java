package com.somethingsimple.simplelist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteViewHolder> {

    private final NoteClickListener mOnlickListener;

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getNoteId() == newItem.getNoteId();
                }

                @Override
                    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final CardView noteCard;
        private final TextView noteText;
        private Note note;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteCard = itemView.findViewById(R.id.card_view);
            noteText = itemView.findViewById(R.id.note_text_edit);
        }

        private void bind(Note note) {
            this.note = note;
            noteText.setText(String.format(
                    Locale.getDefault(), "%d %s", note.getNoteId(), note.getNoteText()));
            noteText.setText(note.getNoteText());
            noteText.setOnClickListener(v ->
                    mOnlickListener.onNoteClick(this.note));
        }

        public Note getNote() {
            return note;
        }
    }

    public NoteListAdapter(NoteClickListener clickListener) {
        super(DIFF_CALLBACK);
        mOnlickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_checkable_recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
