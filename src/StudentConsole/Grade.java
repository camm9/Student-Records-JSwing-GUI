package StudentConsole;


public enum Grade {
	A("A"),B("B"),C("C"),D("D"),P("P"),F("F"), NA("NA");

	private String string;
	Grade(String letter){
		string =letter;}
	
	@Override
	public String toString() {
		return string;
	}
	

}


