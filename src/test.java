import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start test
		System.out.println("starting the test Stage 1...");
		miniNLP f1=new miniNLP("NER.txt");
		//unit test parsing
		//Sentence d=new Sentence(1,"The term First World War was first used in September 1914 by the German philosopher Ernst Haeckel, who claimed that there is no doubt that the course and character of the fear");
		//Token[] y=f1.parseSentence(d);
		//Arrays.stream(y).forEach(s-> System.out.println(s.name));
		
		Token[] x=f1.processFile("nlp_data.txt");
		Arrays.stream(x).forEach(s-> System.out.printf("sentenceid is %d, name is %s\n",s.sentenceID,s.name));
		f1.writeXML("ner_xml.xml",x);

		
		System.out.println("done!");

	}

}
