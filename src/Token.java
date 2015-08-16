
public class Token implements java.io.Serializable {
	String name;
	String documentName;
	int sentenceID;
	public Token(int id, String name)
	{
		this.sentenceID=id;
		this.name=name;
	}
	public Token()
	{}
	public String getName() {
        return name;
    }

    /**
     * Setter for property <code>name</code>.
     * @param value
     */
    public void setName(final String value) {
        name = value;
    }

}
