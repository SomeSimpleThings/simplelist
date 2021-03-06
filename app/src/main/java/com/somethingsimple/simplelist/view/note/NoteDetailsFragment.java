package com.somethingsimple.simplelist.view.note;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
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
import com.somethingsimple.simplelist.databinding.FragmentNoteDetailsBinding;
import com.somethingsimple.simplelist.swipeinteractions.SwipeCallback;
import com.somethingsimple.simplelist.swipeinteractions.SwipeCallbackListener;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment
        implements SwipeCallbackListener {

    private static final String KEY = "uid";
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NotesViewModel noteViewModel;
    private NotesAdapter adapter;
    private FragmentNoteDetailsBinding binding;

    public NoteDetailsFragment() {
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
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_note_details,
                container,
                false);

        adapter = new NotesAdapter(getContext());
        noteViewModel = new ViewModelProvider(this, viewModelFactory).get(NotesViewModel.class);
        long uid = this.getArguments().getLong(KEY, 0);
        noteViewModel.getNotes(uid).observe(getViewLifecycleOwner(), adapter::setNotes);
        noteViewModel.getCurrentFolder(uid)
                .observe(getViewLifecycleOwner(), binding::setFolder);
        setupToolbar(binding.getRoot());
        setupFab();
        setupRecyclerView(binding.getRoot().findViewById(R.id.recyclerview_note));

        return binding.getRoot();
    }

    private void setupRecyclerView(RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeCallback(this, adapter));
        itemTouchHelper.attachToRecyclerView(view);
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_done_24dp);
        fab.setOnClickListener(v -> updateAndNavigateBack());
    }


    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(v -> updateAndNavigateBack());
    }

    private void updateAndNavigateBack() {
        hideKeyboard();
        noteViewModel.update(adapter.getNotes(), adapter.getDeletedNotes());
        noteViewModel.updateFolder(binding.getFolder());
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(
                R.id.action_noteDetailsFragment_to_noteListFragment);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
                adapter.addNoteCheckable(binding.getFolder().getId());
                return true;
            case R.id.menu_item_add_text:
                adapter.addNote(binding.getFolder().getId());
                return true;
            case R.id.menu_item_share:
                shareNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareNote() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, adapter.getNotesForSend());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onSwipe() {
        Snackbar.make(binding.getRoot(), R.string.folder_removed_message, LENGTH_LONG)
                .setAction(R.string.undo, v ->
                        adapter.undoDelete())
                .show();
    }
}
