package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.models;

import com.intrusoft.sectionedrecyclerview.Section;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;

import java.util.List;

public class SectionHeaderFavorites implements Section<Discipline> {

    List<Discipline> disciplineList;
    String sectionText;

    public SectionHeaderFavorites(List<Discipline> disciplineList, String sectionText) {
        this.disciplineList = disciplineList;
        this.sectionText = sectionText;
    }

    @Override
    public List<Discipline> getChildItems() {
        return disciplineList;
    }

    public String getSectionText() {
        return sectionText;
    }
}