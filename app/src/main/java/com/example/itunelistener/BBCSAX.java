package com.example.itunelistener;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class BBCSAX extends DefaultHandler {
    Boolean entryFound = false;
    ParserListener listener;
    String element = "";

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        listener.start();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        listener.finish();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("item")) {
            entryFound = true;
        }
        element = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (entryFound && localName.equals("title")) {
            listener.setTitle(element);
        }
        else if(entryFound && localName.equals("description")){
            listener.setDescription(element);
        }
        else if (localName.equals("item")) {
            entryFound = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (length > 0)
            element += new String(ch, start, length);
    }

    public BBCSAX(final URL url, ParserListener listener) {
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                try {
                    InputStream in = url.openStream();
                    SAXParser parser = factory.newSAXParser();
                    parser.parse(in, BBCSAX.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
