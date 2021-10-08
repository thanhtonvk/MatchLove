package com.tondz.matchlove.Model;

public class Match {
    String id,idUser1,idUser2,idUser1Accept,idUser2Accept;

    public Match(){

    }
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

    public String getIdUser1Accept() {
        return idUser1Accept;
    }

    public void setIdUser1Accept(String idUser1Accept) {
        this.idUser1Accept = idUser1Accept;
    }

    public String getIdUser2Accept() {
        return idUser2Accept;
    }

    public void setIdUser2Accept(String idUser2Accept) {
        this.idUser2Accept = idUser2Accept;
    }

    public Match(String id, String idUser1, String idUser2, String idUser1Accept, String idUser2Accept) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.idUser1Accept = idUser1Accept;
        this.idUser2Accept = idUser2Accept;
    }
}
