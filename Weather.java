import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Weather {
	private double Date;
	private double PAR;  /*photosythentical Active radiation*/
	private double RAIN;
	private double SRAD; /* solar radiation*/
	private double TMAX;
	private double TMIN;
	private double WPp;
	private List<Double>dataList= new ArrayList<Double>();
	//---------------------------------//
	public Weather() {
		PAR=0;
		RAIN=0;
		SRAD=0;
		TMAX=0;
		TMIN=0;
	}
	//--------------setters-----//
	public void setPAR(double pAR) {
		PAR = pAR;
	}
	public void setRAIN(double rAIN) {
		RAIN = rAIN;
	}
	public void setSRAD(double sRAD) {
		SRAD = sRAD;
	}
	public void setTMAX(double tMAX) {
		TMAX = tMAX;
	}
	public void setTMIN(double tMIN) {
		TMIN = tMIN;
	}
	
	public void GetData() throws IOException,IndexOutOfBoundsException {
		JFileChooser chose= new JFileChooser("C://Users//projectOOP//Documents");
		int status=chose.showOpenDialog(null);
		File selectedFile;
		if(status==chose.APPROVE_OPTION) {
			selectedFile=chose.getSelectedFile();
		
		FileReader reader= new FileReader(selectedFile);
		BufferedReader bfr= new BufferedReader(reader);
		for(String i; (i=bfr.readLine())!=null;) {
			Scanner sc= new Scanner(i);
			dataList.add(sc.nextDouble());
		}
		this.setPAR(dataList.get(0));
		this.setRAIN(dataList.get(1));
		this.setSRAD(dataList.get(2));
		this.setTMAX(dataList.get(3));
		this.setTMIN(dataList.get(4));
		this.setWPp(dataList.get(5));
		bfr.close();
	}
}}
