package parser;

public class FullTiltParser extends AbsParser {

	
	
	private static final FullTiltParser INSTANCE = new FullTiltParser();
	
	public static FullTiltParser getInstance(){
		return INSTANCE;
		
	}
	
	
	@Override
	void understand() {
		// TODO Auto-generated method stub
		System.out.println("I am a FullTilt");
		
	}
	

}
