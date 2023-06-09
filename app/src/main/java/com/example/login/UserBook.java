package com.example.login;

public class UserBook {

    private String bookname;
    private String Author;
    private String Punisher;
    private String punishdate;
    private String Genre;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPunisher() {
        return Punisher;
    }

    public void setPunisher(String punisher) {
        Punisher = punisher;
    }

    public String getPunishdate() {
        return punishdate;
    }

    public void setPunishdate(String punishdate) {
        this.punishdate = punishdate;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
