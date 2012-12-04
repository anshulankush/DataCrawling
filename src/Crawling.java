import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawling  {

	public static void main(String[] args) throws IOException {
		Set<String> userSet=new HashSet<String>();
		Queue<String> userQueue=new LinkedList<String>();
		// Loading parsed users from ParsedUserSet.txt
		BufferedReader readSet;
		readSet = new BufferedReader (new FileReader ("/Users/anshulkumarchawla/Documents/Crawling/ParsedUserSet.txt"));
		String currentLineSet="";

		while (null !=(currentLineSet=readSet.readLine())){
			userSet.add(currentLineSet);	
		}

		readSet.close();
		// Loading first user to traverse from PendingUserQueue.txt
		BufferedReader readInitialQueue;
		readInitialQueue = new BufferedReader (new FileReader ("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt"));
		String user="";
		//checking if current user on top of queue is already traversed or not.
		//test comment
		while(userSet.contains(user = readInitialQueue.readLine())){};

		readInitialQueue.close();

		try {
			long userCount=0;
			int pageNo=1;
			String lastPage="";
			URL myUrl;
			//Reading from url of user
			BufferedReader readQueue;
			readQueue = new BufferedReader (new FileReader ("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt"));
			String currentLineQueue="";

			while (null !=(currentLineQueue=readQueue.readLine())){
				userQueue.add(currentLineQueue);	
			}

			readQueue.close();
			//Queue Implemetation;
			while(userQueue!=null){
				FileWriter FWuserList;
				FWuserList = new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/Dataset.csv",true);
				// for each user
				do{
					try{
						myUrl = new URL("http://www.last.fm/user/"+ user +"/friends?page="+pageNo);
						BufferedReader br= new BufferedReader(new InputStreamReader(myUrl.openStream()));
						String stringPageSource = "";
						// Reading page Source
						while(null != (stringPageSource = br.readLine())){
							// Finding Friends
							String patternFriendsName = "<strong><a href=\"/user/([a-zA-z0-9-]+)";
							Pattern compiledPattern = Pattern.compile(patternFriendsName);
							Matcher matcher = compiledPattern.matcher(stringPageSource);

							if(matcher.find()){
								FWuserList.write(user+","+matcher.group(1)+"\n");
								userCount++;
								userQueue.add(matcher.group(1));
							}
							//Finding PageNumber
							String patternPageNumber = "lastpage\".([0-9]+)";
							Pattern compiledPattern1 = Pattern.compile(patternPageNumber);
							Matcher matcher1 = compiledPattern1.matcher(stringPageSource);

							if(matcher1.find()){
								lastPage=matcher1.group(1);
							}	
						}
					}
					catch(FileNotFoundException e1){
						e1.printStackTrace();
						// Writing banned users to BannedUserQueue.txt
						FileWriter FWbannedUserQueue;			
						FWbannedUserQueue= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/BannedUserQueue.txt",true);
						FWbannedUserQueue.write(user+"\n");
						FWbannedUserQueue.close();
						user=userQueue.poll();
					}
					catch(Exception e1){
						e1.printStackTrace();
						continue;
						// Writing banned users to BannedUserQueue.txt
						//FileWriter FWbannedUserQueue;			
						//FWbannedUserQueue= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/UserQueue.txt",true);
						//FWbannedUserQueue.write(user+"\n");
						//FWbannedUserQueue.close();
						//user=userQueue.poll();
					}
					System.out.println("Total Users traversed till Page "+pageNo+" of User = "+user+" : "+userCount);

					if(lastPage.isEmpty()){
						lastPage="1";
					}

					System.out.println("\n\n\n\nLastPage:"+lastPage);
					pageNo++;//going to next page after traversing all friends on current page
				}while(pageNo<=Integer.parseInt(lastPage));//traverse until lastpage
				// reset pageno to 1 and lastpage to empty.
				lastPage="";
				pageNo=1;
				System.out.println("User Name:"+user);
				userSet.add(user);// add user whose friends are traversed to set.
				FWuserList.close();
				// take out next user from userQueue
				user=userQueue.poll();
				//check from userSet, if user already traversed 

				while(userSet.contains(user)){
					user=userQueue.poll();
				}

				System.out.println("Next User: "+user);
				// perform mandatory write operation after every new 500 friends get fetched. to make data safe.
				long size=1000;

				if(userQueue.size()>=size){
					size=size+size;
					//Program ending write operations.
					FileWriter FWpendingUserQueue;			
					FWpendingUserQueue= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt");
					Iterator<String> queueIterator = userQueue.iterator();

					while(queueIterator.hasNext()){
						FWpendingUserQueue.write(queueIterator.next()+"\n");
					}

					FWpendingUserQueue.close();
					FileWriter FWparsedUserSet;
					//in case of exception add set to ParsedUserSet.txt file
					FWparsedUserSet= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/ParsedUserSet.txt");
					Iterator<String> setIterator = userSet.iterator();

					while(setIterator.hasNext()){
						FWparsedUserSet.write(setIterator.next()+"\n");
					}

					FWparsedUserSet.close();
				}
			}
			System.out.println("Program Terminating!!!");
		}
		//		catch(IOException e){
		//			e.printStackTrace();
		//
		//			FileWriter FWpendingUserQueue;			
		//			FWpendingUserQueue= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt");
		//			Iterator<String> queueIterator = userQueue.iterator();
		//			
		//			while(queueIterator.hasNext()){
		//				FWpendingUserQueue.write(queueIterator.next()+"\n");
		//			}
		//			
		//			FWpendingUserQueue.close();
		//			FileWriter FWparsedUserSet;//in case of exception add set to .txt file
		//			FWparsedUserSet= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/ParsedUserSet.txt");
		//			Iterator<String> setIterator = userSet.iterator();
		//
		//			while(setIterator.hasNext()){
		//				FWparsedUserSet.write(setIterator.next()+"\n");
		//			}
		//			
		//			FWparsedUserSet.close();
		//		} 
		catch(Exception e){
			e.printStackTrace();

			FileWriter FWpendingUserQueue;			
			FWpendingUserQueue= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/PendingUserQueue.txt");
			Iterator<String> queueIterator = userQueue.iterator();

			while(queueIterator.hasNext()){
				FWpendingUserQueue.write(queueIterator.next()+"\n");
			}

			FWpendingUserQueue.close();
			FileWriter FWparsedUserSet;//in case of exception add set to .txt file
			FWparsedUserSet= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/ParsedUserSet.txt");
			Iterator<String> setIterator = userSet.iterator();

			while(setIterator.hasNext()){
				FWparsedUserSet.write(setIterator.next()+"\n");
			}

			FWparsedUserSet.close();
		} 
	}
}

