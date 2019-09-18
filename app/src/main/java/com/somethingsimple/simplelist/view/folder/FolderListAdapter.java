package com.somethingsimple.simplelist.view.folder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.model.FolderViewModel;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class FolderListAdapter extends ListAdapter<Folder, FolderListAdapter.FolderViewHolder> {

    private FolderViewModel mFolderViewModel;

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
            folderTitle.setOnClickListener(v -> onFolderCLick(folder));
            folderText.setOnClickListener(v -> onFolderCLick(folder));
        }

        private void onFolderCLick(Folder folder) {
            mFolderViewModel.getOpenNoteEvent().setValue(folder);
            mFolderViewModel.setFolder(folder);
        }

        Folder getfolder() {
            return folder;
        }
    }

    public FolderListAdapter(FolderViewModel folderViewModel) {
        super(DIFF_CALLBACK);
        mFolderViewModel = folderViewModel;
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
