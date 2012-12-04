import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class test {

	public static void main(String[] args) {
		Set<String> userSet=new HashSet<String>();
		try {
			BufferedReader readQueue;
			readQueue = new BufferedReader (new FileReader ("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt"));
			String currentLineQueue="";
		
			while (null !=(currentLineQueue=readQueue.readLine())){
				userSet.add(currentLineQueue);	
			}
			readQueue.close();
			
			FileWriter FWparsedUserSet;
			//in case of exception add set to ParsedUserSet.txt file
			FWparsedUserSet= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/TestUserSet.txt");
			Iterator<String> setIterator = userSet.iterator();
	
			while(setIterator.hasNext()){
				FWparsedUserSet.write(setIterator.next()+"\n");
			}
			
			FWparsedUserSet.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
