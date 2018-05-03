package src.swe.main.ui.library;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleStringProperty title;
	private final SimpleStringProperty subject;
    private final SimpleStringProperty id;
    private final SimpleStringProperty author;
    private final Calendar date;
    private final SimpleStringProperty filepath;
   

    
    public Book(String id, String subject ,String title, String author, Calendar date, String filepath) {
    	this.title = new SimpleStringProperty(title);
    	this.subject = new SimpleStringProperty(subject);
    	this.id = new SimpleStringProperty(id);
    	this.author = new SimpleStringProperty(author);
    	this.date = (Calendar)date.clone();
    	this.filepath = new SimpleStringProperty(filepath);
    }
    public String getTitle() {
    	
    	return title.get();
    }
    public String getId() {
    	
    	return id.get();
    }
    public String getAuthor() {
    	
    	return author.get();
    }
    public Calendar getDate() {

    	return date;
    }
    public String getFilepath() {
    	return filepath.get();
    }
    
    public String getSubject() {
    	return subject.get();
    }
    
    
}
