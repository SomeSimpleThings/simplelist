package com.somethingsimple.simplelist.view;


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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NotesViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {

    private NotesViewModel noteViewModel;

    private EditText editNoteTitle;
    private EditText editNoteText;
    private FloatingActionButton fab;
    private Note mNote;
    private NoteListAdapter adapter;


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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        long id = getArguments().getLong("folderId");
        adapter = new NoteListAdapter(note -> {

        });
        noteViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        noteViewModel.init(-1,id);
        noteViewModel.getNotes(false, id).observe(this, adapter::submitList);

        setupToolbar(view);

        fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done_black_24dp);
        fab.setOnClickListener(v -> {
//            String text = editNoteTitle.getText().toString() + editNoteText.getText().toString();
//            editNoteText.onEditorAction(EditorInfo.IME_ACTION_DONE);
//            editNoteTitle.onEditorAction(EditorInfo.IME_ACTION_DONE);
//            if (!text.replaceAll(" ", "").equals("")) {
//                mNote.setNoteText(editNoteText.getText().toString());
//                mNote.setNoteTitle(editNoteTitle.getText().toString());
//                noteViewModel.insert(mNote);
//            }
            Navigation.findNavController(view).navigate(
                    R.id.action_noteDetailsFragment_to_noteListFragment);
        });
        return view;
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
}
