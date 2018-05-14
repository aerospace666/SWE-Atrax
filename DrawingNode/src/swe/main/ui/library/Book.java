package src.swe.main.ui.library;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleStringProperty title;
	private final SimpleStringProperty name;
	private final SimpleStringProperty subject;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty libid;
    private final SimpleStringProperty author;
    private final Date date;
    private final SimpleStringProperty filepath;
    private final SimpleStringProperty keywords;
    private final Map<Integer, String> keyOcurrences;
    private final List<String> keywordList;

    //declaration for keywords insert;
    public Book(int id, String subject ,String name,String title, String author, Date date, String filepath, String keywords, String libid, Map<Integer, String> keyOcurrences, List<String> keywordList ) {
    	this.title = new SimpleStringProperty(title);
    	this.name = new SimpleStringProperty(name);
    	this.subject = new SimpleStringProperty(subject);
    	this.id = new SimpleIntegerProperty(id);
    	this.author = new SimpleStringProperty(author);
    	this.date = date;
    	this.filepath = new SimpleStringProperty(filepath);
    	this.libid = new SimpleStringProperty(libid);
    	this.keywords = new SimpleStringProperty(keywords);
    	this.keyOcurrences = keyOcurrences;
    	this.keywordList = keywordList;
    }
    
    
    public Book(int id, String subject ,String name,String title, String author, Date date, String filepath, String keywords, String libid) {
    	this.title = new SimpleStringProperty(title);
    	this.name = new SimpleStringProperty(name);
    	this.subject = new SimpleStringProperty(subject);
    	this.id = new SimpleIntegerProperty(id);
    	this.author = new SimpleStringProperty(author);
    	this.date = date;
    	this.filepath = new SimpleStringProperty(filepath);
    	this.libid = new SimpleStringProperty(libid);
    	this.keywords = new SimpleStringProperty(keywords);
    	this.keyOcurrences = null;
    	this.keywordList = null;
    }
    
    public String getTitle() {
    	
    	return title.get();
    }
    public String getName() {
    	
    	return name.get();
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
    public String getKeywords() {
    	return keywords.get();
    }
    public Map<Integer, String> getKeyOcurrences(){
    	return keyOcurrences;
    }
    public List<String> getKeywordlist(){
    	return keywordList;
    }
}
