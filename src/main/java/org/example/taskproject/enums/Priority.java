package org.example.taskproject.enums;

public enum Priority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");
    private final String name;
    Priority(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
