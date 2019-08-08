package com.somethingsimple.simplelist.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;


@Entity
public class Folder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @SerializedName("folder_id")
    @Expose
    private long id;

    @ColumnInfo
    @SerializedName("folder_text")
    @Expose
    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return id == folder.id &&
                Objects.equals(folderName, folder.folderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folderName);
    }
}
