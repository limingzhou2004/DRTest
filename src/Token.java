
public class Token implements java.io.Serializable {
	/**
	 * 
	 */
	 static final long serialVersionUID = 1L;
	 String name;
	 String documentName;
	 int sentenceID;
	public Token(int id, String name)
	{documentName="";
		this.sentenceID=id;
		this.name=name;
	}
	 public Token()
	{}
	
	
	public String getName() {
        return name;
    }

    public void setName(final String value) {
        name = value;
    }
    public int getSentenceID (){
        return sentenceID;
    }

    public void setSentenceID(final int value) {
        sentenceID = value;
    }
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(final String value) {
        documentName = value;
    }

}
