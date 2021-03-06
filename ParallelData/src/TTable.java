import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class TTable implements Serializable{

	ArrayList<HashMap<Integer, Double>> table;
	HashSet<Integer> titles = new HashSet<>();
	
	
	public TTable readTransTable(String fileName) throws IOException, ClassNotFoundException {
		BufferedReader r = new BufferedReader(new FileReader(fileName));
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("TTable.ser"));
		return (TTable)in.readObject();
		
	}
	
	public static int findNumWords(String fileName) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(fileName));
		for(int i=0;i<100000;i++){
			r.readLine();
		}
		String temp = null, curr;
		while((curr=r.readLine())!=null){
			temp = curr;
		}
		int num = Integer.parseInt(temp.split(" ")[0]);
		System.out.println(num);
		return num;
		
		
	}
	
	public void storeTT(String fileName) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(fileName));
		int numWords = findNumWords(fileName);
		table = new ArrayList<HashMap<Integer, Double>>();
		for(int i=0; i<numWords+100; i++){
			table.add(new HashMap<Integer, Double>(10));
		}
		String s;
		String[] spl;
		HashMap<Integer, Double> currWordMap = null;
		int prevWordId = -1;
		while((s = r.readLine())!=null){
			spl = s.split(" ");
			int wordId = Integer.parseInt(spl[0]);
			;
			Integer titleId = Integer.parseInt(spl[1]);
			Double tValue = Double.parseDouble(spl[2]);
			if(wordId != prevWordId){
				currWordMap = table.get(wordId);			
			}
			titles.add(titleId);
			currWordMap.put(titleId, tValue);
			prevWordId = wordId;
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("TTable.ser"));
		out.writeObject(this);
		r.close();
	}
	
	double getTValue(int wordId, int paperId){
		Double val = table.get(wordId).get(paperId);
		if(val == null){
			return 0.0;
		}
		return val;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(findNumWords("Test4(1-10-0-1-1-0).final"));
		new TTable().storeTT("Test4(1-10-0-1-1-0).final");
	}
}
