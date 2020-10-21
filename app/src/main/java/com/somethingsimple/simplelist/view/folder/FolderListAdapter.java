package com.somethingsimple.simplelist.view.folder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.databinding.RecyclerviewItemBinding;
import com.somethingsimple.simplelist.db.entity.Folder;
import com.somethingsimple.simplelist.swipeinteractions.ItemTouchHelperActions;

import java.util.Collections;
import java.util.List;

public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.FolderViewHolder>
        implements ItemTouchHelperActions {

    private List<Folder> folders;
    private int mDeletedPosition;
    private LayoutInflater mInflater;
    private Folder currentFolder;
    private FolderViewModel mFolderViewModel;

    public interface FolderClickListener {
        void onFolderClick(Folder folder);
    }

    public FolderListAdapter(Context context, FolderViewModel folderViewModel) {
        mInflater = LayoutInflater.from(context);
        mFolderViewModel = folderViewModel;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(folders, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(folders, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDismiss(int position) {
        mDeletedPosition = position;
        currentFolder = folders.get(position);
        folders.remove(position);
        notifyItemRemoved(position);
    }

    public Folder getDeletedFolder() {
        return currentFolder;
    }

    public void undoDelete() {
        folders.add(mDeletedPosition, currentFolder);
        notifyItemInserted(mDeletedPosition);
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        RecyclerviewItemBinding binding =
                DataBindingUtil.inflate(mInflater, R.layout.recyclerview_item, parent, false);
        binding.setHandler(folder ->
                //// TODO: 2019-09-30 remove this ugly code
                mFolderViewModel.openFolder(folder));

        return new FolderListAdapter.FolderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        if (folders != null) {
            Folder current = folders.get(position);
            holder.folderBinding.setFolder(current);
        }
    }

    @Override
    public int getItemCount() {
        if (folders != null)
            return folders.size();
        else return 0;
    }

    void setFolders(List<Folder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerviewItemBinding folderBinding;

        public FolderViewHolder(RecyclerviewItemBinding binding) {
            super(binding.getRoot());
            folderBinding = binding;
        }
    }
}
