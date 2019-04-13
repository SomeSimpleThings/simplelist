package com.somethingsimple.simplelist.network;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class NotePojo implements Parcelable {

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("completed")
    @Expose
    private Boolean completed;

    public final static Parcelable.Creator<NotePojo> CREATOR = new Creator<NotePojo>() {

        @SuppressWarnings({
                "unchecked"
        })
        public NotePojo createFromParcel(Parcel in) {
            return new NotePojo(in);
        }

        public NotePojo[] newArray(int size) {
            return (new NotePojo[size]);
        }

    };

    private NotePojo(Parcel in) {
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.completed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public NotePojo() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotePojo note = (NotePojo) o;
        return Objects.equals(userId, note.userId) &&
                Objects.equals(id, note.id) &&
                Objects.equals(title, note.title) &&
                Objects.equals(completed, note.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, completed);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(completed);
    }

    public int describeContents() {
        return hashCode();
    }

}
