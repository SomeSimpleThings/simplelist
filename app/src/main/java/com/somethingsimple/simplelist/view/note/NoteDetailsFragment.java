package com.somethingsimple.simplelist.view.note;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.model.NotesViewModel;
import com.somethingsimple.simplelist.view.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {

    private NotesViewModel noteViewModel;
    NotesAdapter adapter;
    EditText toolbarEditText;


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


        adapter = new NotesAdapter(getContext());

        noteViewModel = MainActivity.obtainNoteViewModel(getActivity());
        noteViewModel.getNotes().observe(this, adapter::setNotes);

        toolbarEditText = view.findViewById(R.id.edit_foldername);
        toolbarEditText.setText(noteViewModel.getCurrentFolder().getFolderName());

        setupToolbar(view);
        setupFab(view);
        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupFab(View view) {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done_black_24dp);
        fab.setOnClickListener(v -> updateAndNavigateBack(view));
    }


    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> updateAndNavigateBack(view));
    }

    private void updateAndNavigateBack(View view) {
        noteViewModel.update(adapter.getNotes(), adapter.getDeletedNotes());
        Bundle bundle = new Bundle();
        String text = toolbarEditText.getText().toString();
        bundle.putString(getActivity().getString(R.string.foldername_key), text);
        Navigation.findNavController(view).navigate(
                R.id.action_noteDetailsFragment_to_noteListFragment, bundle);
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
                //noteViewModel.addNoteCheckable();
                adapter.addNoteCheckable(noteViewModel.getCurrentFolder().getId());
                return true;
            case R.id.menu_item_add_text:
                //noteViewModel.addNote();
                adapter.addNote(noteViewModel.getCurrentFolder().getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
