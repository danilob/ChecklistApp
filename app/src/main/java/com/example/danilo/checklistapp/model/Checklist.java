package com.example.danilo.checklistapp.model;

/**
 * Created by danilo on 03/05/18.
 */

public class Checklist {

    private int mId;
    private String mDescription;
    private boolean mActive;

    //criar sets, gets e construtor
    public Checklist(){

    }

    public Checklist(String mDescription, boolean mActive) {
        this.mDescription = mDescription;
        this.mActive = mActive;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }
}
