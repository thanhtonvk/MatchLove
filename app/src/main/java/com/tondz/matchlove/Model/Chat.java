package com.tondz.matchlove.Model;

public class Chat {
    String id,idUser1,idUser2,UI;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public String getUI() {
        return UI;
    }

    public void setUI(String UI) {
        this.UI = UI;
    }

    public Chat(String id, String idUser1, String idUser2, String UI) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.UI = UI;
    }
}
