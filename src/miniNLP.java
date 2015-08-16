import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class miniNLP extends Thread {

	private String[] knownEntity;
	String doc;
	Token[] tokens;
	
	//methods
	public miniNLP(String fentityName) {
		// load known entities 
		if (this.knownEntity==null)
		{
			try (Stream<String>lines=Files.lines(Paths.get(fentityName),
					Charset.defaultCharset()))
			{
				this.knownEntity=(String[]) lines.toArray(size -> new String[size]);
				//Arrays.stream(knownEntity).forEach(System.out::println);
			} 
			catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	//parse a sentence considering the following patterns
	//U of U; U de U; U ap U
	//U numeric
	public Token[] parseSentence(Sentence s)
	{	String originStr=s.content.trim().replace(",", "").replace(".","").replace("\"", "");
		String replceTo="";
		ArrayList<Token> tks=new ArrayList<Token>();
		Pattern[] patterns = {Pattern.compile("([A-Z]\\S+\\s)+((of|de|ap)\\s)?([A-Z]\\S+\\s)+"),
				Pattern.compile("([A-Z]\\S*)+\\s[0-9]+\\s")
				};
		for (int i=0;i<patterns.length;i++) {
			Matcher m = patterns[i].matcher(originStr);
			while(m.find()) {
		             tks.add(new Token(s.ID,originStr.substring(m.start(), m.end())));
					//System.out.println(originStr.substring(m.start(), m.end()));
			}			
			originStr=m.replaceAll(replceTo);
		}
		//System.out.println(originStr);
		//s.content;
		
		String[] temp=originStr.split("\\s");
		for (int i=0;i<temp.length;i++)
			tks.add(new Token(s.ID, temp[i]));
		return tks.toArray(new Token[tks.size()]);
	}
	//read a text file and split by [.!?] for sentence
	//space for words, remove other punctuation
	public Token[] processFile(String txtName)
	{
		try (Stream<String>lines=Files.lines(Paths.get(txtName),
				Charset.defaultCharset()))
		{
			List<String> temp=lines.filter(s->s.length()>0)
					.flatMap( s ->  Stream.of(s.split("[.!?] "))).collect(Collectors.toList());
			Stream<Sentence> st=IntStream.range(0, temp.size()).
					mapToObj(x->new Sentence(x,temp.get(x)));
			
			
			List<Token> t2=st.flatMap(s->Stream.of(this.parseSentence(s))).
					collect(Collectors.toList());
		return t2.toArray(new Token[t2.size()]);
			
		//	.forEach(System.out::println);

		
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}		
		return null;	
	}
	
	public void writeXML(String fn, Token[] x)
	{
		try (
		XMLEncoder e = new XMLEncoder( 
				new BufferedOutputStream(
						new FileOutputStream(fn))))
		{
			for (int i=0;i<x.length;i++)
				e.writeObject(x[i]);
		}
		catch (IOException ex)
		{ex.printStackTrace();}	
	}
	//Stage 2 function
	//
	public void printKnownEntities(Token[] t)
	{    int ii=0;
	     
		for ( ii=0;ii<this.knownEntity.length;ii++)
		{String tmp=this.knownEntity[ii];
			Arrays.stream(t).filter(s->s.name.equals(tmp))
			.forEach(s-> System.out.printf("sentenceid is %d, name is %s\n",s.sentenceID,s.name));
		}
	}
	

}
