import java.io.FileInputStream;

import com.java4less.ocr.docparser.DocumentDef;
import com.java4less.ocr.docparser.Parser;
import com.java4less.ocr.docparser.data.Document;
import com.java4less.ocr.docparser.data.DocumentSet;
import com.java4less.ocr.docparser.data.Section;
import com.java4less.ocr.tess3.OCRFacade;
import com.java4less.pdf.PDFToTextConverter;


public class PDFParserTest {

    public static void main( String[] args ) throws Exception
    {
    	PDFToTextConverter conv=new PDFToTextConverter();
    	conv.setPreserveSpaces(true);
    	conv.setAddEmptyLines(false);
    	String s=conv.convertToString(new FileInputStream("order.pdf"));
    	
    	System.out.println( "Page text:\n"+s);
    	
    	try {
			OCRFacade facade=new OCRFacade();

			DocumentDef docDef=new DocumentDef();
			docDef.loadFromXml("ordedef.xml");
			
			Parser parser=new Parser(docDef);
			DocumentSet docSet=parser.parse(s);
			
			if (docSet.getCount()<1) {
				System.err.println("No purchase order found");
				System.exit(-1);
			}
			
			Document doc=docSet.getDocument(0); // we assume each image has one order only

			
			//  create XML file for the extracted data
			System.out.println("\n\nXML output=\n"+docSet.toXml());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    		
    }
	
}
