

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
//reserve.txt파일을 읽어서 적절하게 Parsing
public class Data {
	static private ArrayList<Data> information;
	private String Day;
	private String month;
	private String year;
	private String seats;
	private String name;
	private String phone;
	private String res = "Reserved";
	private String cansel = "--";
	static{
		information = new ArrayList<Data>();
		Scanner scanner;
		try{
			scanner = new Scanner(new File("reserve.txt"));
			String tmp = scanner.nextLine();
			
			while(scanner.hasNext()){
				StringTokenizer st = new StringTokenizer(scanner.nextLine(),"\t\r\n");
				Data data = new Data();
				data.month = st.nextToken();
				data.Day = st.nextToken();
				data.year =st.nextToken();
				data.seats = st.nextToken();
				data.name = st.nextToken();
				data.phone = st.nextToken();

				information.add(data);
			}scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	public Data(){}
	
	public Data(String month, String day, String year,String seats, String name, String phone){
		this.month = month;
		this.Day = day;
		this.year = year;
		this.seats = seats;
		this.name = name;
		this.phone = phone;
		}
//데이터 입력
	private static void insertInfo(){
		try {
			FileWriter fw = new FileWriter(new File("reserve.txt"));
			
			String column = "월\t일\t년\t자리NO.\t예약자명\t연락처\r\n";
			fw.write(column);
			
			for(Data data : information){
				String line = data.month +"\t"+ data.Day+"\t"+data.year+"\t"+data.seats+"\t"+data.name+"\t" + data.phone +"\r\n";
				fw.write(line);
			}
			
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//정보 추가 (동기화)
	synchronized public static void addInfo(Data newData){
		information.add(newData);
		insertInfo();
	}
	//정보 업데이트(동기화)
	synchronized public static void updateInfo(Data newData,String month, String day, String year,String seats){
		for(Data data : information){
			if(data.seats.equals(seats) && data.month.equals(month) && data.Day.equals(day) && data.year.equals(year) && data.seats.equals(seats)){
				data.name = newData.name;
				data.phone = newData.phone;
				
			}
		}
		insertInfo();
	}
	
	synchronized public static int updatecanselInfo(Data newData,String month,String day,String year, String name,String phone){
		for(Data data : information){
			if(data.name.equals(name) && data.month.equals(month) && data.Day.equals(day) && data.year.equals(year) && data.phone.equals(phone)){
				data.name = newData.cansel;
				data.phone = newData.cansel;
				insertInfo();
				return 1;
			}
			else
				continue;
		}
		insertInfo();
		return 0;
	}
	
	synchronized public static int updatecanselInfo(Data newData,String seats,String name,String phone){
		for(Data data : information){
			if(data.name.equals(name) && data.seats.equals(seats) && data.phone.equals(phone)){
				data.name = newData.cansel;
				data.phone = newData.cansel;
				insertInfo();
				return 1;
			}
			else
				continue;
		}
		insertInfo();
		return 0;
	}
	public static ArrayList<Data> getInfo(){
		return information;
	}
	//getter
	public String getSeats(){
		return seats;
	}
	
	public String getName(){
		return name;
	}
	public String getPhone(){
		return phone;
	}
	public String getmonth(){
		return month;
	}
	public String getyear(){
		return year;
	}
	public String getDay(){
		return Day;
	}
	public String getRes()
	{
		return res;
	}
	
}
