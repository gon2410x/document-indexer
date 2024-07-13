package app;

import java.io.IOException;
import java.nio.file.Paths;

import com.ironsoftware.ironpdf.PdfDocument;

public class ReadPdf {
	
	public static String readPdf(String adress) throws IOException {
		
        PdfDocument pdf = PdfDocument.fromFile(Paths.get(adress));  
        return pdf.extractAllText();
	}
}
