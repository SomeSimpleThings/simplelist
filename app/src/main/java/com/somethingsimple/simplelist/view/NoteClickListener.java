package com.somethingsimple.simplelist.view;

import com.somethingsimple.simplelist.db.Folder;
import com.somethingsimple.simplelist.db.Note;

interface NoteClickListener {
    void onNoteClick(Note note);
}
