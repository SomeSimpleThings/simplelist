package com.somethingsimple.simplelist.db.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Folder.class,
        parentColumns = "id",
        childColumns = "folderId",
        onDelete = CASCADE,
        onUpdate = CASCADE),
        indices = @Index("folderId"))

public class Note implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @SerializedName("id")
    @Expose
    private long noteId;

    @ColumnInfo
    @SerializedName("position")
    @Expose
    private int position;

    @ColumnInfo
    @SerializedName("folder_id")
    @Expose
    private long folderId;

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

    public Note(long folderId, int position, boolean checkable) {
        this.noteText = "";
        this.folderId = folderId;
        this.position = position;
        this.checkable = checkable;
        this.checked = false;
    }

    Note(Parcel in) {
        noteId = in.readLong();
        folderId = in.readLong();
        position = in.readInt();
        noteText = in.readString();
        checkable = in.readByte() != 0;
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
        dest.writeLong(folderId);
        dest.writeInt(position);
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return noteId + " " + noteText;
    }

    public String toFormattedString() {
        String text = "";
        if (checkable) {
            text = checked ? text + "\u2611" : "\u2610";
        }
        text = text + noteText;
        return text;
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return noteId == note.noteId &&
                folderId == note.folderId &&
                position == note.position &&
                checkable == note.checkable &&
                checked == note.checked &&
                Objects.equals(noteText, note.noteText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, position, folderId, noteText, checkable, checked);
    }
}
