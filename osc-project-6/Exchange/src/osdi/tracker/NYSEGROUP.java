package osdi.tracker;

public class NYSEGROUP {
	//NYSE GROUP INC
	private String quote = null;
	
	public NYSEGROUP(String quote){
		this.quote = quote;
	}
	public String quote(){
		System.out.println(quote);
		return quote;
	}
}
