package com.somethingsimple.simplelist.view.folder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.model.FolderViewModel;
import com.somethingsimple.simplelist.swipeInteractions.SwipeCallback;
import com.somethingsimple.simplelist.swipeInteractions.SwipeCallbackListener;
import com.somethingsimple.simplelist.view.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FolderListFragment extends Fragment
        implements SwipeCallbackListener {

    private FolderViewModel folderViewModel;
    private FolderListAdapter adapter;

    public FolderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        folderViewModel = MainActivity.obtainFolderViewModel(getActivity());
        adapter = new FolderListAdapter(getContext(), folderViewModel);
        folderViewModel.getFolders().observe(this, adapter::setFolders);

        setupRecyclerView(view);
        checkUpdateFolderName();
        setupFab();
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeCallback(this, adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void checkUpdateFolderName() {
        if (this.getArguments() != null) {
            String foldername = this.getArguments().getString(
                    getActivity().getString(R.string.foldername_key));
            folderViewModel.update(foldername);
        }
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setOnClickListener(v -> folderViewModel.addFolder());
        fab.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
        BottomAppBar bar = getActivity().findViewById(R.id.bar);
        bar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        bar.setHideOnScroll(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_all:
                folderViewModel.deleteAll();
                return true;
            case R.id.menu_sort:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSwipe() {
        Snackbar.make(getView(), R.string.folder_removed_message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo, v ->
                        adapter.undoDelete())
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            folderViewModel.delete(adapter.getDeletedFolder());
                        }
                    }
                })
                .show();
    }
}
