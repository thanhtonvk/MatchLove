package com.tondz.matchlove.Model;

import java.util.List;

public class Match {
    String id;
    Account account1,account2;
    boolean idUser1Accept,idUser2Accept;
    boolean blocked;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Match(String id, Account account1, Account account2, boolean idUser1Accept, boolean idUser2Accept) {
        this.id = id;
        this.account1 = account1;
        this.account2 = account2;
        this.idUser1Accept = idUser1Accept;
        this.idUser2Accept = idUser2Accept;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public Match(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean isIdUser1Accept() {
        return idUser1Accept;
    }

    public void setIdUser1Accept(boolean idUser1Accept) {
        this.idUser1Accept = idUser1Accept;
    }

    public boolean isIdUser2Accept() {
        return idUser2Accept;
    }

    public void setIdUser2Accept(boolean idUser2Accept) {
        this.idUser2Accept = idUser2Accept;
    }


}
