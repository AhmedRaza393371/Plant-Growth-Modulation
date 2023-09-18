import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SoilWater {
	List<Double> DataList= new ArrayList<Double>();
	private int CN;
	private double DP=0;
	private double DRNP=0;
	private double FCp=0;
	private double STp=0;
	private double SWC = 0;
	private double WPp=0;
	private double WP;
	private double FC;
	private double ST;
	private double S;
	private double THE;
	private double DRN;
	private double RAIN,IRR;
	private double scfac1;
	private double TRAIN=0,TIRR=0,TESA=0,TEPA=0,TROF=0,TDRN=0;// Cumulative values
	private double RunoffRate;
	private double TINF=0;
	private double Pot_inf;
	private double INF;
	private double EPp;
	private double ESa;
	private double SWC_ADJ=33;
	private Plant P;   		//plant object// 
	private double SWFAC1;
	private double WTable;
	private double depth_WTable;
	private double lai;
	private double tmax,tmin;
	private double srad;
	/*------------------------Constant Variables-------------------*/
	private  static final double Stress_Depth=250;
	
	/*_____________________Contstructor______________*/
	public SoilWater(Plant P) throws IOException{
		P= new Plant();
		lai=P.getLAI();
		tmax=P.getMaxT();
		tmin=P.getMinT();
		srad=P.getSRad();
		CN=0;
		DP=0;
		DRNP=0;
		FCp=0;
		SWC=0;
		WPp=0;
		WP=0;
		FC=0;
		ST=0;
		IRR=0;
		TINF=0;
	//this.RUNOFF();
	///this.STRESS();
	}
	/*_____________________________________________________*/ 
	
	/* -----------Setters-----------*/
	public void setTINF(double tINF) {
		TINF = tINF;
	}
	public void setCN(double cN) {
		CN = (int)cN;
	}
	public void setDP(double dP) {
		DP = dP;
	}
	public void setDRNP(double dRNP) {
		DRNP = dRNP;
	}
	public void setFCp(double fCp) {
		FCp = fCp;}
	public void setSTp(double sTp) {
		STp = sTp;
	}
	public void setSWC(double sWC) {
		SWC = sWC;
	}
	public void setWPp(double wPp) {
		WPp = wPp;
	}
	/*-------------------------------------------------------------*/
	public void GetData() throws IOException,IndexOutOfBoundsException{
		JFileChooser chose= new JFileChooser("C://Users//Meer//Documents");
		int status=chose.showOpenDialog(null);
		File selectedFile;
		if(status==chose.APPROVE_OPTION) {
			selectedFile=chose.getSelectedFile();
		
		FileReader reader= new FileReader(selectedFile);
		BufferedReader bfr= new BufferedReader(reader);
		for(String i; (i=bfr.readLine())!=null;) {
			Scanner sc= new Scanner(i);
			DataList.add(sc.nextDouble());
			}
		setCN(DataList.get(0));
		setDP(DataList.get(1));
		setDRNP(DataList.get(2));
		setFCp(DataList.get(3));
		setSTp(DataList.get(4));
		setSWC(DataList.get(5));
		setWPp(DataList.get(6));
		bfr.close();
	}
		WP=DP*WPp*10.0;
		FC=DP* FCp*10.0;
		ST=DP*FCp*10.0;
		TINF=Pot_inf-TROF;
	}
public void RUNOFF() {

	S=254*(100/CN -1);
	
}
public void STRESS() {

	THE=WPp+0.75*(FCp-WPp);
	
}
public void SWC() {
	SWC=SWC+(TINF-ESa-EPp-DRN);
}


public void SWC_ADJ() {
	if(SWC>0) {
		SWC_ADJ=SWC_ADJ-SWC;
		SWC=0;
	}
}

/*------------------------Rate__Calculation---------------*/
public void rate_Calc() {
	this.DRAINE();
	 Pot_inf= RAIN+ IRR;
	if(Pot_inf>0) {
		this.RUNOFF();
	}


}
public void DRAINE() {
	double DRN;
	DRN=(SWC -FCp)*DRNP;
}
public void INF() {
	INF=Pot_inf-RunoffRate;
}
public void ROF() {
	if(Pot_inf>(0.2*S)) {
		RunoffRate=(Math.pow((Pot_inf-0.2*S),2)/(Pot_inf+0.8*S));
	}
	else {
		RunoffRate=0;
	}
}
/*________________________end rate Calculation method______________*/

public double ETpS() {
	double Albedo;
	double Tmed;
	double EEQ;
	double ETp;
	double ESp;
	Albedo=0.1*Math.exp(-0.7*lai) + 0.2*(1-Math.exp(-0.7*lai));
	Tmed=0.6*tmax+0.4*tmin;
	EEQ=srad*(4.88*Math.pow(10, -3) - 4.3*Math.pow(10, -3)*Albedo)*(Tmed+29);
	ETp=EEQ;
	ESp= ETp*Math.exp((-7)*lai);
	EPp= ETp*(1-Math.exp((-7)*lai));
	return ESp;
}

public void ESaS() {
	double a ;
	if(SWC<WP) {
		a=0;
	}
	else if(SWC>FCp) {
		a=1;
	}
	else {
		a=(SWC-WP)/(FCp-WP);
	}
	
	ESa= this.ETpS()*a;
}
/*-------------------------Rate_Calculation-------------------*/
 
/*-------------------------INTEGRATION-------------------------*/
public void RunOffRateUpdate() {
	if(SWC<ST) {
		RunoffRate+=(SWC-ST);
		SWC=ST;
	}
}
public void updatedValue() {
	SWC=SWC+(INF-ESa-EPp-DRN);
	if(SWC>ST) {
		RunoffRate+=(SWC-ST);
		SWC=ST;
	}
	TINF+=INF;
	TESA+=ESa;
	TDRN+=DRNP;
	TROF+=RunoffRate;	
}
		

public void Stress2() {
	 if(SWC<WP) {
		 SWFAC1=0.0;
	 }
	 else if(SWC>THE) {
		 SWFAC1=1.0;
		 
	 }
	 else{
		 SWFAC1=(SWC-WPp)/(THE-WPp);
	 }
}
public void SWFAC2() {
	double DWT;
	double SWFAC2=1.0;
	 WTable=(SWC-FCp)/(ST-FCp)*DP*10;
	 DWT=DP*10-WTable;
	 if(WTable==0) {
		 SWFAC2=0.0;
	 }
	 if(DWT>Stress_Depth) {
		 SWFAC2=1.0;
	 }
	 else {
		 SWFAC2=DWT/Stress_Depth;
	 }

}
public void Output() {

	File output= new File("C://Users//Meer//Documents//SW.OUT.txt");
	PrintWriter ois= new PrintWriter(output);
	ois.println(TINF);
	ois.println(TROF);
	ois.println(TDRN);
	ois.println(TESA);
}




}
