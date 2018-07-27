package src.swe.main.ui.library;

import java.util.Arrays;

import org.grobid.core.data.BiblioItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

public class StudyMetadata {
	
	
	private final String pGrodHome = "grobid-home";

	
	public BiblioItem extractMetadata(String pdfPath) {
		
		GrobidHomeFinder Grofind = new GrobidHomeFinder(Arrays.asList(pGrodHome)) ;
	
		GrobidProperties.getInstance(Grofind);
	
		System.out.println("-----GROBID_HOME= " + GrobidProperties.get_GROBID_HOME_PATH());
	
		Engine engine = GrobidFactory.getInstance().createEngine();
	
		BiblioItem resHeader = new BiblioItem();
		engine.processHeader(pdfPath, true, resHeader);
		
		return resHeader;
	}
}
