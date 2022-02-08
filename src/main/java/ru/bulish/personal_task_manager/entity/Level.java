package ru.bulish.personal_task_manager.entity;

public enum Level {
    EASY("Easy"),HARD("Hard"),MEDIUM("Medium");

    String value;

    Level(String value) {
        this.value = value;
    }
}
