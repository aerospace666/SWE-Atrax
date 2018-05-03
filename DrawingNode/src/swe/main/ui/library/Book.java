package src.swe.main.ui.library;


import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleStringProperty title;
	private final SimpleStringProperty subject;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty libid;
    private final SimpleStringProperty author;
    private final Date date;
    private final SimpleStringProperty filepath;
   

    
    public Book(int id, String subject ,String title, String author, Date date, String filepath, String libid) {
    	this.title = new SimpleStringProperty(title);
    	this.subject = new SimpleStringProperty(subject);
    	this.id = new SimpleIntegerProperty(id);
    	this.author = new SimpleStringProperty(author);
    	this.date = date;
    	System.out.println(date);
    	this.filepath = new SimpleStringProperty(filepath);
    	this.libid = new SimpleStringProperty(libid);
    }
    public String getTitle() {
    	
    	return title.get();
    }
    public int getId() {
    	
    	return id.get();
    }
    public String getAuthor() {
    	
    	return author.get();
    }
    public Date getDate() {

    	return date;
    }
    public String getFilepath() {
    	return filepath.get();
    }
    
    public String getSubject() {
    	return subject.get();
    }
    
    public String getLibid() {
    	return libid.get();
    }
    
    
}
