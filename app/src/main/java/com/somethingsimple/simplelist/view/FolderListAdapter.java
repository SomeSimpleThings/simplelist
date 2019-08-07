package com.somethingsimple.simplelist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Folder;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class FolderListAdapter extends ListAdapter<Folder, FolderListAdapter.FolderViewHolder> {

    private final FolderClickListener mOnlickListener;

    private static final DiffUtil.ItemCallback<Folder> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Folder>() {
                @Override
                public boolean areItemsTheSame(@NonNull Folder oldItem, @NonNull Folder newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Folder oldItem, @NonNull Folder newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class FolderViewHolder extends RecyclerView.ViewHolder {

        private final CardView folderCard;
        private final TextView folderTitle;
        private final TextView folderText;
        private Folder folder;

        FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderCard = itemView.findViewById(R.id.card_view);
            folderTitle = itemView.findViewById(R.id.folder_title);
            folderText = itemView.findViewById(R.id.folder_text);
        }

        private void bind(Folder folder) {
            this.folder = folder;
            folderTitle.setText(String.format(
                    Locale.getDefault(), "%d %s", folder.getId(), folder.getFolderName()));
            folderText.setText(folder.getFolderName());
            folderTitle.setOnClickListener(v ->
                    mOnlickListener.onFolderClick(this.folder));
            folderText.setOnClickListener(v ->
                    mOnlickListener.onFolderClick(this.folder));
        }

        public Folder getfolder() {
            return folder;
        }
    }

    public FolderListAdapter(FolderClickListener clickListener) {
        super(DIFF_CALLBACK);
        mOnlickListener = clickListener;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
