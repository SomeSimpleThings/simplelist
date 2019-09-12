package com.somethingsimple.simplelist.view.note;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.model.NotesViewModel;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteViewHolder> {

    private final NotesViewModel mNotesViewModel;

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getNoteId() == newItem.getNoteId()
                            && oldItem.isChecked() == newItem.isChecked();
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
            if (note.isCheckable()) {
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(note.isChecked());
                checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                    note.setChecked(b);
                    mNotesViewModel.update(note);
                    Log.d("note ",note.getNoteId()+"updated");
                });
            } else checkBox.setVisibility(View.GONE);
        }

        public Note getNote() {
            return note;
        }
    }

    public NoteListAdapter(NotesViewModel notesViewModel) {
        super(DIFF_CALLBACK);
        this.mNotesViewModel = notesViewModel;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

}
