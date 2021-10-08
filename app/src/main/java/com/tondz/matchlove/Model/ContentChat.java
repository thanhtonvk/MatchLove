package com.tondz.matchlove.Model;

public class ContentChat {
    String id,idChat,content,idUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public ContentChat(String id, String idChat, String content, String idUser) {
        this.id = id;
        this.idChat = idChat;
        this.content = content;
        this.idUser = idUser;
    }
}
