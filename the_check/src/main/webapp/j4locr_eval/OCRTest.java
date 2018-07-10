
import com.java4less.ocr.tess3.OCRFacade;

public class OCRTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OCRFacade facade=new OCRFacade();
		try {
			String text=facade.recognizeFile("order.PNG", "eng");
			
			System.out.println("Text in the image is: ");
			System.out.println(text);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
