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

public class NotesListAdapter extends ListAdapter<Note, NotesListAdapter.NotesViewHolder> {

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

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final CardView noteItemCard;
        private final TextView noteItemView;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemCard = itemView.findViewById(R.id.card_view);
            noteItemView = itemView.findViewById(R.id.note_text);
        }

        private void bind(Note note) {
            noteItemView.setText(note.toString());
            noteItemView.setOnClickListener(v ->
                    mOnlickListener.onNoteClick(note));
        }
    }

    public NotesListAdapter(NoteClickListener clickListener) {
        super(DIFF_CALLBACK);
        mOnlickListener = clickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
