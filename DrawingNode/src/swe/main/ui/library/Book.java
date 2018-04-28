package src.swe.main.ui.library;

import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleStringProperty title;
    private final SimpleStringProperty id;
    private final SimpleStringProperty author;
    private final SimpleStringProperty date;
    private final SimpleStringProperty filepath;

    
    public Book(String title, String id, String author, String date, String filepath) {
    	this.title = new SimpleStringProperty(title);
    	this.id = new SimpleStringProperty(id);
    	this.author = new SimpleStringProperty(author);
    	this.date = new SimpleStringProperty(date);
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
    public String getDate() {

    	return date.get();
    }
    public String getFilepath() {
    	return filepath.get();
    }
    
    
}
