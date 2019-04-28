package com.somethingsimple.simplelist.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;
import com.somethingsimple.simplelist.model.NoteViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {

    private NoteViewModel noteViewModel;

    private EditText editText;
    private FloatingActionButton fab;
    private Note mNote;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details,
                container, false);
        long id = getArguments().getLong("noteId");
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.init(id);
        noteViewModel.getNote().observe(this, note -> {
            mNote = note;
            editText = view.findViewById(R.id.edit_word);
            editText.setText(note.getNote());
        });

        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (!text.replaceAll(" ", "").equals("")) {
                mNote.setNote(text);
                noteViewModel.insert(mNote);
            }
            Navigation.findNavController(view).navigate(
                    R.id.action_noteDetailsFragment_to_noteListFragment);
        });
        return view;
    }
}
