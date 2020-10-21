package com.somethingsimple.simplelist.view.folder;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.swipeinteractions.SwipeCallback;
import com.somethingsimple.simplelist.swipeinteractions.SwipeCallbackListener;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class FolderListFragment extends Fragment
        implements SwipeCallbackListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FolderViewModel folderViewModel;
    private FolderListAdapter adapter;

    public FolderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        folderViewModel = new ViewModelProvider(this, viewModelFactory).get(FolderViewModel.class);
        adapter = new FolderListAdapter(getContext(), folderViewModel);
        folderViewModel.getFolders().observe(getViewLifecycleOwner(), adapter::setFolders);
        folderViewModel.getOpenNoteEvent().observe(getViewLifecycleOwner(), folder -> {
            Bundle bundle = new Bundle();
            bundle.putLong("uid", folder.getId());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_folderListFragment_to_noteDetailsFragment, bundle);
        });

        setupRecyclerView(view);
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

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_24dp);
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
        bar.setNavigationIcon(R.drawable.ic_menu_24dp);
        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        bar.setHideOnScroll(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipe() {
        Snackbar.make(getView(), R.string.folder_removed_message, LENGTH_SHORT)
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
