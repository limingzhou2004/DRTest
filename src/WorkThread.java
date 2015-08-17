 
class WorkerThread implements Runnable {  
    private String finName;  
    public Token[] tk;
    public WorkerThread(String s){  
        this.finName=s;  
    }  
     public void run() {  
        System.out.println(Thread.currentThread().getName()+" (Start) file: = "+this.finName);  
        processFile();
        System.out.println(Thread.currentThread().getName()+" (End)");//prints thread name  
    }  
    private void processFile() {  
        try { 
        	miniNLP f1=new miniNLP("NER.txt");
        	this.tk=f1.processFile(this.finName);  
        	for(int i=0;i<this.tk.length;i++) this.tk[i].documentName=this.finName;
        }
        catch (Exception e) { e.printStackTrace(); }  
    }  
} 

