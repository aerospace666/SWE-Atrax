import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.*;


public class box
{
	public static void main(String[] args) throws IOException 
	{
		
		MetaData Data = new MetaData();
		
		String Path = "C:/Users/dolly/Downloads/Atraxtestdata/demo";
		
		Data.getFilePath(Path,1); //testing purpose
		

		
		/****************************************************************************************
		Scanner scan = new Scanner(System.in);
		List<String> words = new ArrayList<String> ();
		
		//to write new keywords into bufferedWriter
		List<String> Writer = new ArrayList<String> ();
		//To save the keywords in the file
		BufferedReader in = null; 
		BufferedWriter out = null;
		
        int Title_count  = 0,Subject_count =0,Keyword_count =0;
		
     	File file = new File("C:/Users/dolly/Downloads/Atraxtestdata/demo/Adams2017.pdf");
		
		PDDocument document = PDDocument.load(file);
		PDDocumentInformation pdd = document.getDocumentInformation();
		
		System.out.println(pdd.getKeywords());
		List<String> FileNames = new ArrayList<String> ();
		
		List<String> wordsofFirst;//PDFText;
		String[] PDFText, Keyword_ExtractText;
			
		// the directory/library to search for similar keywords
		 File Folder = new File(file.getParent());
		
		//make an array of files, which contains all the files
		File[] ListOfFiles = Folder.listFiles();
		
		
		// openNLP file, used for word type detection
		InputStream Stream_input = new FileInputStream("en-pos-maxent.bin");
		
		POSModel model = new POSModel(Stream_input);
		POSTaggerME tagger = new POSTaggerME(model);
		
		WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
		String[] tokens, keywordTags;
		String KeywordExtract_Text;
		int occurance =0;
		List<String> MetaData_Keyword = new ArrayList<String> (); 
		List<String> tag_keyword = new ArrayList<String> ();
			
		for(int i =0;i<ListOfFiles.length;i++)
		{
			
			
			if(ListOfFiles[i].isFile() && (! file.getName().equals(ListOfFiles[i].getName())))
			{
				
				PDDocument SecondDocument = PDDocument.load(ListOfFiles[i]);
				PDDocumentInformation PDoc = SecondDocument.getDocumentInformation();
				
				//checking for the keywords in the file
				if(PDoc.getKeywords() == null)
				{
					in = new BufferedReader(new FileReader("Keywords.txt"));
					String Line;
					while((Line = in.readLine()) != null)
						{
							if(ListOfFiles[i].getName().equals(Line))
							{
								Line = in.readLine();
								PDoc.setKeywords(Line);
								System.out.println(PDoc.getKeywords());
								break;
							}
						}
						in.close();
					
					if(PDoc.getKeywords() == null)
					{
					//	
						KeywordExtract_Text = new PDFTextStripper().getText(SecondDocument);
						
						tokens = whitespaceTokenizer.tokenize(KeywordExtract_Text);
						keywordTags = tagger.tag(tokens);
						
						InputStream Stream = new FileInputStream("en-sent.bin");
						SentenceModel Model = new SentenceModel(Stream);
						SentenceDetectorME Detector = new SentenceDetectorME(Model);
						
						Keyword_ExtractText = Detector.sentDetect(KeywordExtract_Text);
						
						
						for(int index =0;index < keywordTags.length;index++)
						{
							if((keywordTags[index].equals("NN"))| (keywordTags[index].equals("VB")) |(keywordTags[index].equals("NNS")) |(keywordTags[index].equals("NNP")))
							{
								tag_keyword.add(tokens[index]);
							}
						}
						
						for(int index1 =0;index1 < tag_keyword.size();index1++)
						{
							for(int index2 =0;index2 < Keyword_ExtractText.length;index2++)
							{
							if(Keyword_ExtractText[index2].contains(tag_keyword.get(index1)))
								{
									occurance++;
									if(occurance >= 20)
									{
										if(!MetaData_Keyword.contains(tag_keyword.get(index1)))
										{
										MetaData_Keyword.add(tag_keyword.get(index1));
										occurance = 0;
										break;
									}
									}
								}
							}
						}
						
							PDoc.setKeywords( MetaData_Keyword.toString());
							Writer.add(ListOfFiles[i].getName());
							Writer.add(PDoc.getKeywords());
	
							MetaData_Keyword.clear();
							System.out.println(PDoc.getKeywords());
					}
				}
				
				
				if((PDoc.getTitle() != null) && (pdd.getTitle() != null))
				{
					wordsofFirst = Arrays.asList(pdd.getTitle().split("-|:|;|\\[|\\]|@|\\ "));
					
					tokens = whitespaceTokenizer.tokenize(PDoc.getTitle());
					String[] tags = tagger.tag(tokens);
					
					
					for(int i1=0;i1<tags.length;i1++)
					{
						if((tags[i1].equals("NN")) | (tags[i1].equals("VB")) |(tags[i1].equals("NNPS"))|(tags[i1].equals("DT")) | (tags[i1].equals("VBD")) |(tags[i1].equals("NNS")) |(tags[i1].equals("NNP")))
							{
								words.add((tokens[i1]));
							}
					}
					
					for(String s : words)
					{
						if(wordsofFirst.contains(s))
						{
							Title_count+=1;	
						}
					}
					words.clear();
					
					if(Title_count >= 1)
					{
						Title_count = 0;
						if(FileNames.isEmpty())
						{
							FileNames.add(ListOfFiles[i].getName());
						}
						else
						{
							if(!FileNames.contains(ListOfFiles[i].getName()))
							{
								FileNames.add(ListOfFiles[i].getName());
							}
						}
						System.out.println("Title of  Adams2017.pdf is similiar to " + ListOfFiles[i].getName());
					}
				
				}
				
				if((PDoc.getAuthor() != null))
				{
					if(PDoc.getAuthor().equals(pdd.getAuthor()))
					{
						if(FileNames.isEmpty())
						{
							FileNames.add(ListOfFiles[i].getName());
						}
						else
						{
							if(!FileNames.contains(ListOfFiles[i].getName()))
							{
								FileNames.add(ListOfFiles[i].getName());
							}
						}
						System.out.println(ListOfFiles[i].getName() + " and " + file.getName() + "has same authors.");
					}
				}
				
				if((PDoc.getSubject() != null) &&(pdd.getSubject() != null))
				{
					wordsofFirst = Arrays.asList(pdd.getSubject().split("-|:|;|\\[|\\]|@|\\ "));
					
					tokens = whitespaceTokenizer.tokenize(PDoc.getSubject());
					String[] tags = tagger.tag(tokens);
					
					
					for(int i1=0;i1<tags.length;i1++)
					{
						if((tags[i1].equals("NN")) | (tags[i1].equals("VB")) | (tags[i1].equals("VBD")) |(tags[i1].equals("NNS")) |(tags[i1].equals("NNP")))
							{
								words.add((tokens[i1]));
							}
					}
					
					for(String s : words)
					{
						if(wordsofFirst.contains(s))
						{
							Subject_count+=1;	
						}
					}
					words.clear();
				
					
					if(Subject_count >= 1)
					{
						Subject_count = 0;
						
						if(FileNames.isEmpty())
						{
							FileNames.add(ListOfFiles[i].getName());
						}
						else
						{
							if(!FileNames.contains(ListOfFiles[i].getName()))
							{
								FileNames.add(ListOfFiles[i].getName());
							}
						}
						
						System.out.println("Subject of  Adams2017.pdf is similiar to " + ListOfFiles[i].getName());
					}
				
				}
				
				if((PDoc.getKeywords() != null) &&(pdd.getKeywords() != null))
				{
					wordsofFirst = Arrays.asList(pdd.getKeywords().split("-|:|;|\\[|\\]|@|\\ "));
					
					tokens = PDoc.getKeywords().split("-|,|;|\\[|\\]|@|\\ ");
					
					
					for(String s : tokens)
					{
						if((wordsofFirst.contains(s)) && (!s.equals("")))
						{
							Keyword_count+=1;	
							System.out.println(s);
						}
					}
					words.clear();

					
					if(Keyword_count >= 1)
					{
						Keyword_count = 0;
						if(FileNames.isEmpty())
						{
							FileNames.add(ListOfFiles[i].getName());
						}
						else
						{
							if(!FileNames.contains(ListOfFiles[i].getName()))
							{
								FileNames.add(ListOfFiles[i].getName());
							}
						}
						System.out.println("Keyword/s of Adams2017.pdf similiar to " + ListOfFiles[i].getName());
					}
				}
					
					SecondDocument.close();
			}
			
		}
	//	out.close();
		
		System.out.println("The files which may have similiar data");
		int number = 0;
		for(String FileName : FileNames)
		{
			System.out.println(number + " - " +FileName);
			number++;
		}
		
		System.out.println("Which file would you like to view?");
		String s = scan.nextLine();
		
		System.out.println(s);
		int index = 0;
		
		for(int i =0;i<ListOfFiles.length;i++)
		{
			if(ListOfFiles[i].getName().equals(s))
			{
				index = i;
			}
		}

	
		//--------------------------------
		//USING OPENNLP
		//-------------------------------
		PDDocument Pdocument = PDDocument.load(ListOfFiles[index]);
		System.out.println("Please enter the keywords");
		String key = scan.next();
		String[] KeywordList = key.split(",");
		String TextPDF = new PDFTextStripper().getText(Pdocument);
		
		InputStream Stream = new FileInputStream("en-sent.bin");
		SentenceModel Model = new SentenceModel(Stream);
		SentenceDetectorME Detector = new SentenceDetectorME(Model);
		
		PDFText = Detector.sentDetect(TextPDF);
		
		for(int j=0; j<KeywordList.length; j++)
		{
			for(int i=0;i<PDFText.length;i++)
			{
				KeywordList[j].toLowerCase();
				PDFText[i].toLowerCase();
				if(PDFText[i].contains(KeywordList[j]))
				{
					System.out.println(PDFText[i]);
				}
			}
		}

		out = new BufferedWriter(new FileWriter("Keywords.txt",true));
		for(int i=0;i<Writer.size();i++)
		{
			out.append(Writer.get(i));
			out.newLine();
		}
		out.close();
	//	Writer.clear();
	    document.close();
	    
	    ***********************************************************************************/
	    
	}

}
