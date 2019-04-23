package com.somethingsimple.simplelist.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.somethingsimple.simplelist.R;
import com.somethingsimple.simplelist.db.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {


    private EditText mEditNoteView;
    private Note mNote;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_details,
                container, false);

        mEditNoteView = view.findViewById(R.id.edit_word);
        Button buttonSave = view.findViewById(R.id.button_save);
        Button buttonDel = view.findViewById(R.id.button_delete);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable("note");
            mEditNoteView.setText(mNote.getNote());
        }
        return view;
    }
}
