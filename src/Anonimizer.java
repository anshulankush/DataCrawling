import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
public class Anonimizer {
	public static void main(String[] args) {
		int number=1;
		int size=10000;
		FileWriter FWnewDataSet = null;	
		FileWriter FWnewDataSet1 = null;			

		Set<String> userSet=new HashSet<String>();
		HashMap<String,Integer> usermap=new HashMap<String,Integer>();
		String filename="/Users/anshulkumarchawla/Documents/Crawling/TestDataset.csv";
		String filename1="";
		BufferedReader br;
		try {
			br = new BufferedReader( new FileReader(filename));
			while((filename1 = br.readLine()) != null) {
				String[] result = filename1.split(",");
				for (int x=0; x<2; x++) {
					if(!usermap.containsKey(result[x])){
						usermap.put(result[x],number);
						FWnewDataSet1= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/ConvertMap.csv",true);
						FWnewDataSet1.write(result[x]+","+number+"\n");
						FWnewDataSet1.close();
						number++;
					
					}
//					else{
//						usermap.get(result[x])
//					}
				if(number>=size){
					size=size+10000;
					System.out.println("\nLine Number: "+number);
				}
				}
//				System.out.println(number++);
			}
			System.out.println("Copy Start");
			br = new BufferedReader(new FileReader(filename));
			while((filename1 = br.readLine()) != null) {
				String[] result1 = filename1.split(",");
				for (int x=0; x<2; x++) {
					result1[x]=Integer.toString(usermap.get(result1[x]));
				}
				FWnewDataSet= new FileWriter("/Users/anshulkumarchawla/Documents/Crawling/NewDataset.csv",true);
				FWnewDataSet.write(result1[0]+","+result1[1]+"\n");
				FWnewDataSet.close();
			}
			System.out.println("Done!!!!");
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

