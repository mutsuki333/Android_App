package com.example.itunelistener;

public interface ParserListener {
    void start();
    void finish();
    void setTitle(final String s);
    void setDescription(final String s);
    void setUrl(final String url);
}
