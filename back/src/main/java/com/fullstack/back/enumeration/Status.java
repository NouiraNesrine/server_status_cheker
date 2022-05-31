package com.fullstack.back.enumeration;

public enum Status {
    UP("serer_up"),
    DOWN("server_down");
    private final String status;

    Status(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
}
