package src.swe.main.ui.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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



public class ExtractMetadata {
	private Book book;
	private List<String> MetaData_keyword;
	private List<Integer> KeywordOccurance;
	private Map<Integer, String> Keyword_occurance;
	private PDDocument document;
 	private PDDocumentInformation PDoc;
 	

	public Book Extractdata(final File file, final int ID, final String libid) throws IOException
	{
		String[] SentenceExtraction, Tokens, keywordTags;
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
		Keyword_occurance = new HashMap<Integer, String>();
		
		List<String> tag_keyword = new ArrayList<String> (); //contains tags for each word in the pdf
		MetaData_keyword = new ArrayList<String> ();
		KeywordOccurance = new ArrayList<Integer> ();
		
		
		try {
			
			document = PDDocument.load(file);
			PDoc = document.getDocumentInformation();
			
		if((document.getNumberOfPages()) < 50)
			{
				//KEYWORD CREATION USING OPENNLP
				if(PDoc.getKeywords() == null || PDoc.getKeywords().isEmpty() )
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
							System.out.println("New set of keywords for " + file.getName());
							
							
				}
				else
				{
					String[] s  = (PDoc.getKeywords()).split("-|\\.|,|:|\\[|\\]|\\ ");
					for(int i=0;i<s.length;i++)
					{
						KeywordOccurance.add(1);
					}
				}
			} else {	//if file is > 50 pages, then skip keywords generating
				
				//if no available keywords then set notice
				if(PDoc.getKeywords() == null || PDoc.getKeywords().isEmpty() ) {
					PDoc.setKeywords("<Notice: File is too large for generating Keywords>");
				}
			
			}
		
				
				String tempSubject = "-";
				if((PDoc.getSubject() != null && !PDoc.getSubject().isEmpty()))
				{	
					tempSubject = PDoc.getSubject();
				
				}
				
				String tempTitle = "-";
				if((PDoc.getTitle() != null && !PDoc.getTitle().isEmpty()))
				{	
					tempTitle = PDoc.getTitle();
				
				}
				
				String tempAuthor = "-";
				if(PDoc.getAuthor() != null && !PDoc.getAuthor().isEmpty())
				{	
					tempAuthor = PDoc.getAuthor();
				
				}
				
				/**
				//insert to database for keywords
				AtraxDatabase dbConn = new AtraxDatabase();
				Keywords = PDoc.getKeywords();
				dbConn.insertKeywordtoKeywordsTable(MetaData_keyword);
				for (String tempString : MetaData_keyword) {
					for (String TempOccur : Keyword_occurance.values()) {
						if(TempOccur.equals(tempString)) {
							dbConn.insertIntoDocKeywordTable(d, keywordID, keywordOccurence) // need get doc id function
						}
					}
				}
				*/
				
				
				
				Date tempDate =  new Date(0);
				if(PDoc.getCreationDate() != null)
				{	
					tempDate = PDoc.getCreationDate().getTime();
				
				}
				
				String tempKeywords = "-";
				if (PDoc.getKeywords() != null && ! PDoc.getKeywords().isEmpty()) {
					tempKeywords = PDoc.getKeywords();
				}
				
				
				book = new Book(ID, tempSubject, file.getName(), tempTitle , tempAuthor, tempDate, file.getAbsolutePath(), tempKeywords ,libid);
				document.close();
		    
			
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		
			return book;
		
	}

}
