package com.somethingsimple.simplelist.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NotesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {

    private NotesViewModel notesViewModel;
    private NotesListAdapter adapter;
    private boolean ordered;

    public NoteListFragment() {
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

        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotesListAdapter(this::onNoteClick);
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getNotes(ordered).observe(this, adapter::submitList);

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
                            notesViewModel.delete(holder.getNote());
                            Snackbar.make(view, R.string.note_removed_message, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.undo, v ->
                                            notesViewModel.insert(holder.getNote()))
                                    .show();
//                            CoordinatorLayout.LayoutParams params =
//                                    ((CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams());
//                            params.setMargins(
//                                    params.leftMargin,
//                                    params.topMargin,
//                                    params.rightMargin,
//                                    params.bottomMargin + 125
//                            );
                        }
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_noteListFragment_to_noteDetailsFragment));
        fab.show();
        return view;
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
                notesViewModel.deleteAll();
                return true;
            case R.id.menu_sort:
                ordered = !ordered;
                notesViewModel.getNotes(ordered).observe(this, adapter::submitList);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onNoteClick(Note note) {
        Bundle bundle = new Bundle();
        bundle.putLong("noteId", note.getNoteId());
        Navigation.findNavController(getView()).navigate(
                R.id.action_noteListFragment_to_noteDetailsFragment, bundle);
    }
}
