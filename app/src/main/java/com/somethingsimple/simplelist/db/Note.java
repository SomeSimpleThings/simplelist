package com.somethingsimple.simplelist.db;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Parcelable {

    @SerializedName("userId")
    @Expose
    @Ignore
    private Integer userId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @SerializedName("id")
    @Expose
    private long noteId;

    @ColumnInfo
    @SerializedName("title")
    @Expose
    private String noteTitle;

    @ColumnInfo
    @SerializedName("text")
    @Expose
    private String noteText;

    @ColumnInfo
    @SerializedName("completed")
    @Expose
    private boolean checked;

    public Note(String noteTitle) {
        this.noteTitle = noteTitle;
        //// TODO: 27/03/2019
        this.checked = false;
    }

    Note(Parcel in) {
        noteId = in.readLong();
        noteTitle = in.readString();
        noteText = in.readString();
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
        dest.writeString(noteTitle);
        dest.writeString(noteText);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return noteId + " " + noteTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return noteId == note1.noteId &&
                checked == note1.checked &&
                Objects.equals(noteTitle, note1.noteTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle, noteText, checked);
    }
}
