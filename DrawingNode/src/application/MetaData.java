package src.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import src.swe.database.AtraxDatabase;

public class MetaData {
	
	private String Keywords;
	private String Author;
	private String Title;
	private String Subject;
	private File file;
	private String LibraryName;
	private List<String> MetaData_keyword;
	private List<Integer> KeywordOccurance;
 	private Calendar CreationDate;

 	PDDocument document;
 	PDDocumentInformation PDoc;
	AtraxDatabase dbConn = new AtraxDatabase();
	
	public MetaData(String keywords, String author, String title, String subject,String filename) {
		super();
		Keywords = keywords;
		Author = author;
		Title = title;
		Subject = subject;
	}
	
	public MetaData(){}
	
	public void ExtractMetaData(File file) throws IOException
	{
		this.file = file;
		String[] PDFText, SentenceExtraction, Tokens, keywordTags;
		HashMap<String, Integer> Keyowrds = new HashMap<String, Integer>(15);
		InputStream Stream_input = new FileInputStream("en-pos-maxent.bin");
				
		//openNLP class for tokenizing
		WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
		
		//open NLP for tagging each word from pdf
		POSModel model = new POSModel(Stream_input);
		POSTaggerME tagger = new POSTaggerME(model);

		//contains the entire pdf as a string
		String Extract_Text_pdf,Keywords;
		
		int occurance =0; //counts the occurance of each word in the pdf
		int MaxKeyword = 0;
		Map<Integer, String> Keyword_occurance = new HashMap<Integer, String>();
		List<String> tag_keyword = new ArrayList<String> (); //contains tags for each word in the pdf
		MetaData_keyword = new ArrayList<String> ();
		KeywordOccurance = new ArrayList<Integer> ();
		
		PDDocument document;
	 	
		try {
			
			document = PDDocument.load(file);
			PDoc = document.getDocumentInformation();
		
			if((document.getNumberOfPages()) < 50)
			{
				//KEYWORD CREATION USING OPENNLP
				if(PDoc.getKeywords() == null || PDoc.getKeywords().isEmpty())
				{
					
						Extract_Text_pdf = new PDFTextStripper().getText(document);
						
						Tokens = whitespaceTokenizer.tokenize(Extract_Text_pdf);
						keywordTags = tagger.tag(Tokens);
						
						//open NLP for sentence detection
						InputStream Stream = new FileInputStream("en-sent.bin");
						SentenceModel Model = new SentenceModel(Stream);
						SentenceDetectorME Detector = new SentenceDetectorME(Model);
						
						SentenceExtraction = Detector.sentDetect(Extract_Text_pdf);
						
						
						for(int index =0;index < keywordTags.length;index++)
						{
							if((keywordTags[index].equals("NN"))| (keywordTags[index].equals("VB")) |(keywordTags[index].equals("NNS")) |(keywordTags[index].equals("NNP")))
							{
								tag_keyword.add(Tokens[index]);
							}
						}
						
						for(int index1 =0;index1 < tag_keyword.size();index1++)
						{
							for(int index2 =0;index2 < SentenceExtraction.length;index2++)
							{
							if(SentenceExtraction[index2].contains(tag_keyword.get(index1)))
								{
									occurance++;
									if(occurance >= 50)
									{
										if(!Keyword_occurance.containsValue(tag_keyword.get(index1)))
										{
											Keyword_occurance.put(occurance,tag_keyword.get(index1));
											occurance = 0;
											break;
										}
									}
								}
							}
						}
						
						
						Map<Integer, String> SortKeyword_occurance = new TreeMap<Integer, String>(Keyword_occurance);	
						
						for(Map.Entry<Integer, String> entry : SortKeyword_occurance.entrySet())
						{	
							if(MaxKeyword < 20)
							{
								KeywordOccurance.add(entry.getKey());
								MetaData_keyword.add(entry.getValue());
								MaxKeyword++;
							}
							else
							{
								break;
							}
							
						}
						
							Keywords = MetaData_keyword.toString();
						    MetaData_keyword.clear();
						
							String[] SpiltString = Keywords.split("-|\\.|,|:|\\[|\\]|\\ ");
							for(int i=0;i<SpiltString.length;i++)
							{
								if((!SpiltString[i].equals("")) && (!SpiltString[i].matches("-?\\d+"))&&(SpiltString[i].length() > 1))
								{
									MetaData_keyword.add(SpiltString[i]);
								}
							}
							
							PDoc.setKeywords( MetaData_keyword.toString());
							
							/**
							for(int i=0;i<MetaData_keyword.size();i++)
							{
								System.out.println(MetaData_keyword.get(i));
							}
							**/
							//System.out.println(PDoc.getKeywords());
				}
				else
				{
					String[] s  = (PDoc.getKeywords()).split("-|\\.|,|:|\\[|\\]|\\ ");
					for(int i=0;i<s.length;i++)
					{
						KeywordOccurance.add(1);
					}
				}
				
				if((PDoc.getTitle() != null))
				{	
					Title = PDoc.getTitle();
				
				}
				
				if((PDoc.getSubject() != null))
				{	
					Subject = PDoc.getSubject();
				
				}
				
				if((PDoc.getAuthor() != null))
				{	
					Author = PDoc.getAuthor();
				
				}
				
				if((PDoc.getKeywords() != null))
				{	
					Keywords = PDoc.getKeywords();
				
				}
				
				if((PDoc.getCreationDate() != null))
				{	
					CreationDate = PDoc.getCreationDate();
				
				}
				System.out.println(file.getName());
				System.out.println(PDoc.getKeywords());
				System.out.println();
				document.close();
			}
		}
			

			 catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFilePath(String FilePath,String Library) throws IOException
	{
		LibraryName = Library;
		file = new File(FilePath);
		
		if(file.isDirectory())
		{
			File[] ListOfFiles = file.listFiles();
			for(int i=0;i<ListOfFiles.length;i++)
			{
				ExtractMetaData(ListOfFiles[i]);
				System.out.println(file.getName());
				System.out.println(PDoc.getKeywords());
				//TODO insertToDatabase function goes here 
				//TODO pass library ID instead of "1"
				String result = dbConn.insertDocToLibrary(getFileName(), getTitle(), getSubject(), getCreationDate(), getPath(), 1, getAuthor(), ""); //temp fixed for keywords
				System.out.println("\n The result from DB query is: " + result);
			}
		}
		else
		{
			ExtractMetaData(file);
			System.out.println(file.getName());
			System.out.println(PDoc.getKeywords());
			//TODO insertToDatabase function goes here
			String result = dbConn.insertDocToLibrary(getFileName(), getTitle(), getSubject(), getCreationDate(), getPath(), 1, getAuthor(), ""); //temp fixed for keywords
			System.out.println("\n The result from DB query is: " + result);
		}
	}

	public String getKeywords() {
		return Keywords;
	}

	public String getAuthor() {
		return Author;
	}

	public String getTitle() {
		return Title;
	}

	public String getSubject() {
		return Subject;
	}
	
	public String getFileName() {
		return file.getName();
	}
	
	public String getLibrary() {
		return LibraryName;
	}
	
	public List<String> getKeywordsList()
	{
		return MetaData_keyword;
	}
	
	public String getPath()
	{
		return file.getPath();
	}
	
	public Date getCreationDate()
	{
		return CreationDate.getTime();
	}
	
	public List<Integer> getKeywordOccurance()
	{
		return KeywordOccurance;
	}
	
}


