import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start test
		//System.out.println("starting the test Stage 1...");
		//miniNLP f1=new miniNLP("NER.txt");
		//unit test parsing
		//Sentence d=new Sentence(1,"The term First World War was first used in September 1914 by the German philosopher Ernst Haeckel, who claimed that there is no doubt that the course and character of the fear");
		//Token[] y=f1.parseSentence(d);
		//Arrays.stream(y).forEach(s-> System.out.println(s.name));
		
		//stage 1 test
		//miniNLP f1=new miniNLP("NER.txt");
		//Token[] x=f1.processFile("nlp_data.txt");
		//Arrays.stream(x).forEach(s-> System.out.printf("sentenceid is %d, name is %s\n",s.getSentenceID(),s.getName()));
		//f1.writeXML("ner_xml.xml",x);

		//stage 2 test
		//miniNLP f1=new miniNLP("NER.txt");
		//Token[] x=f1.processFile("nlp_data.txt");
		//f1.printKnownEntities(x);
		
		//stage 3 test
		stage3();
		System.out.println("done!");

	}

	static public void stage3()
	{
	    ExecutorService executor = Executors.newFixedThreadPool(5);//creating a pool of 5 threads  
	    //get files from a folder
	    List<Path> fns=null;
	    try {fns=
	    	Files.walk(Paths.get("nlp_data/nlp_data"))
	        .filter(Files::isRegularFile)
	        .collect(Collectors.toList());
	        //.forEach(path -> System.out.println(path.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    WorkerThread[] workers=new WorkerThread[fns.size()];
        for (int i=0;i<fns.size();i++) {  
            workers[i] = new WorkerThread(fns.get(i).toString());  
            executor.execute(workers[i]);//calling execute method of ExecutorService  
          }  
        executor.shutdown();  
        while (!executor.isTerminated()) {   }  
  
        //merge all file results
        Stream<Token> sb=Arrays.stream(workers[0].tk);
        for (int i =1;i<workers.length;i++ )
        {
         sb = Stream.concat(Arrays.stream(workers[i].tk), sb);
        }
        //print tokens
        //sb.forEach(s-> System.out.printf("sentenceid is %d, name is %s\n",s.sentenceID,s.name));
        //print mathced entities
        miniNLP f1=new miniNLP("NER.txt");
        List<Token> ss=sb.collect(Collectors.toList());
        Token[] st=ss.toArray(new Token[ss.size()]);
        f1.printKnownEntities(st);
        //write xml
        f1.writeXML("all_xml.xml",st);

        //sb.toArray(size -> new Token[size]);
        
        System.out.println("Finished all threads");  

	}
}
