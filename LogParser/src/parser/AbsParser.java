package parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public abstract class AbsParser {
	
	
	//protected ArrayList<String> insertStatements = new ArrayList<String>();
	
	public  void parse(String path){
		
		//Split on double <cr>
		
		understand();
		
	} 
	
	abstract  void understand();
	
    protected  String readFileAsString(String filePath) throws java.io.IOException {
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
        f.read(buffer);
        return new String(buffer);
    }

}
