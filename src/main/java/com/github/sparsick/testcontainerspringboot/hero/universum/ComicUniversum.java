package com.github.sparsick.testcontainerspringboot.hero.universum;

public enum ComicUniversum {
    MARVEL("Marvel"),
    DC_COMICS ("DC Comic");

    private String displayName;

    ComicUniversum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
