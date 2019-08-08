package com.somethingsimple.simplelist.view.note;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.entity.Note;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteViewHolder> {

    private final NoteClickListener mOnlickListener;
    private final NoteEditorActionListener mActionListener;

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
        private final CheckBox checkBox;
        private Note note;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteCard = itemView.findViewById(R.id.card_view);
            noteText = itemView.findViewById(R.id.note_text_edit);
            checkBox = itemView.findViewById(R.id.note_checkBox);
        }

        private void bind(Note note) {
            this.note = note;
            noteText.setText(note.getNoteText());
            noteText.setOnClickListener(v ->
                    mOnlickListener.onNoteClick(this.note));
            noteText.setOnEditorActionListener((v, actionId, event)
                    -> {
                note.setNoteText(noteText.getText().toString());
                return mActionListener.onNoteEditorAction(actionId, this.note);

            });
            if (note.isCheckable()) checkBox.setVisibility(View.VISIBLE);
            else checkBox.setVisibility(View.GONE);
        }

        public Note getNote() {
            return note;
        }
    }

    public NoteListAdapter(NoteClickListener clickListener, NoteEditorActionListener actionListener) {
        super(DIFF_CALLBACK);
        mOnlickListener = clickListener;
        mActionListener = actionListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.checkable_recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
