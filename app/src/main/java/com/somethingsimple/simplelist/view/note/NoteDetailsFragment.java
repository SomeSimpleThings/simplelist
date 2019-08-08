package com.somethingsimple.simplelist.view.note;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.entity.Note;
import com.somethingsimple.simplelist.model.FolderViewModel;
import com.somethingsimple.simplelist.model.NotesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {

    private NotesViewModel noteViewModel;
    private FolderViewModel folderViewModel;

    private FloatingActionButton fab;
    private Note mNote;
    private long mFolderid;
    private NoteListAdapter adapter;
    RecyclerView recyclerView;


    public NoteDetailsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_note_details,
                container, false);


        mFolderid = getArguments().getLong("folderId");
        adapter = new NoteListAdapter(note -> {

        }, (actionId, note) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                noteViewModel.update(note);
                return true;
            }
            return false;
        });
        noteViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        folderViewModel = ViewModelProviders.of(this).get(FolderViewModel.class);
        noteViewModel.getNotes(false, mFolderid).observe(this, adapter::submitList);

        setupToolbar(view);
        setupFab(view);
        recyclerView = view.findViewById(R.id.recyclerview_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setupFab(View view) {
        fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done_black_24dp);
        fab.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(
                    R.id.action_noteDetailsFragment_to_noteListFragment);
        });
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).navigate(
                R.id.action_noteDetailsFragment_to_noteListFragment));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item, menu);
        BottomAppBar bar = getActivity().findViewById(R.id.bar);
        bar.setNavigationIcon(null);
        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bar.setHideOnScroll(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_check:
                noteViewModel.insert(new Note("", mFolderid, true));
                return true;
            case R.id.menu_item_add_text:
                noteViewModel.insert(new Note("", mFolderid, false));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
