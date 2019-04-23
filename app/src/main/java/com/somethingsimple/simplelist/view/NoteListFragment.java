package com.somethingsimple.simplelist.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NoteViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {

    private NoteViewModel noteViewModel;

    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NotesListAdapter adapter = new NotesListAdapter(this::onNoteClick);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, adapter::submitList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        NotesListAdapter.NotesViewHolder holder = (NotesListAdapter.NotesViewHolder) viewHolder;
                        if (holder.getNote() != null) {
                            noteViewModel.delete(holder.getNote());
                        }
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_noteListFragment_to_noteDetailsFragment));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void onNoteClick(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("note", note);
        Navigation.findNavController(getView()).navigate(
                R.id.action_noteListFragment_to_noteDetailsFragment,bundle);
    }
}
