package com.example.enotes_api.enums;

public enum TodoStatus {

    NOT_STARTED(1,"Not Started"),
    IN_PROGRESS(2, "In_Progress"),
    COMPLETED(3,"Completed");

    private Integer id;
    private String name;

    TodoStatus(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
