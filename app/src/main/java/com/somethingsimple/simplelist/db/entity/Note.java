package com.somethingsimple.simplelist.db.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Folder.class,
        parentColumns = "id",
        childColumns = "folderId",
        onDelete = CASCADE,
        onUpdate = CASCADE),
        indices = @Index("folderId"))

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
    @SerializedName("folder_id")
    @Expose
    private long folderId;

    @ColumnInfo
    @SerializedName("title")
    @Expose
    private String noteTitle;

    @ColumnInfo
    @SerializedName("text")
    @Expose
    private String noteText;

    @ColumnInfo
    @SerializedName("checkable")
    @Expose
    private boolean checkable;

    @ColumnInfo
    @SerializedName("checked")
    @Expose
    private boolean checked;

    public Note(String noteTitle, long folderId, boolean checkable) {
        this.noteTitle = noteTitle;
        this.folderId = folderId;
        this.checkable = checkable;
        this.checked = false;
    }

    Note(Parcel in) {
        noteId = in.readLong();
        noteTitle = in.readString();
        noteText = in.readString();
        checkable = in.readByte() != 0;
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
        dest.writeString(noteTitle);
        dest.writeString(noteText);
        dest.writeByte((byte) (checkable ? 1 : 0));
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

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
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

    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
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
        Note note = (Note) o;
        return noteId == note.noteId &&
                folderId == note.folderId &&
                checkable == note.checkable &&
                checked == note.checked &&
                Objects.equals(userId, note.userId) &&
                Objects.equals(noteTitle, note.noteTitle) &&
                Objects.equals(noteText, note.noteText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, noteId, folderId, noteTitle, noteText, checkable, checked);
    }
}
