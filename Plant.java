import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.JFileChooser;

public class Plant {
	public double maxT,minT;
	private float SRad;
	public double  scfac1;
	SoilWater scfac2;
	private double  LAI;
	private double PD;  //plant Density
	private double ROWSPC;
	private  int leefMax;
	public static int lfNo;
	private double EMP1 ;
	private double EMP2;
	private double fc;
	private double PT;
	private double dN;
	private double nb;
	private double x;
	private double baseT;
	private double p1;
	private double SLA;
	private double dry_weight;  //w
	private double conopy_dry_weight; //wc
	private double root_dry_weight; //wr
	private double PG;
	private double Y1;
	List<Double> DataList= new ArrayList<Double>();
	
	public void setScfac1(double scfac1) {
			this.scfac1 = scfac1;
		}
		public double getScfac1() {
			return scfac1;
		}
public Plant() throws IOException {}
//--------Setters---------------------------------//
public void setEMP1(double eMP1) {
	EMP1 = eMP1;
}
public void setEMP2(double eMP2) {
	EMP2 = eMP2;
}
public void setLAI(double lAI) {
	LAI = lAI;
}
public void setLeefMax(double leefMax) {
	this.leefMax = (int) leefMax;
}
public static void setLfNo(double lfNo) {
	Plant.lfNo = (int)lfNo;
}
public void setNb(double nb) {
	this.nb = nb;
}
public void setP1(double p1) {
	this.p1 = p1;
}
public void setPD(double pD) {
	PD = pD;
}
public void setSLA(double sLA) {
	SLA = sLA;
}
public void setBaseT(double baseT) {
	this.baseT = baseT;
}
public void setDry_weight(double dry_weight) {
	this.dry_weight = dry_weight;
}
public void setConopy_dry_weight(double conopy_dry_weight) {
	this.conopy_dry_weight = conopy_dry_weight;
}
public void setRoot_dry_weight(double root_dry_weight) {
	this.root_dry_weight = root_dry_weight;
}
	//-------------------------------------------------------//

/*----------------------------Getters---------------------*/
public double getLAI() {
	return LAI;
}
public float getSRad() {
	return SRad;
}
public double getMaxT() {
	return maxT;
}
public double getMinT() {
	return minT;
}

/*---------------initialization---------------------------------*/
public void getData() throws IOException,IndexOutOfBoundsException{
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
	this.setEMP1(DataList.get(0));
	this.setEMP2(DataList.get(1));
	this.setLAI(DataList.get(2));
	this.setLeefMax(DataList.get(3));
	this.setLfNo(DataList.get(4));
	this.setNb(DataList.get(5));
	this.setP1(DataList.get(6));
	this.setPD(DataList.get(7));
	setSLA(DataList.get(8));
	this.setBaseT(DataList.get(9));
	this.setDry_weight(DataList.get(10)); // set w
	this.setConopy_dry_weight(DataList.get(11));  //set wc
	this.setRoot_dry_weight(DataList.get(12));   //set wr
	this.setLAI(DataList.get(13));
	bfr.close();
	}
	}
	
//------------------------------------------------------------------//
public void PTS() {           // Calculates the effect of temperature on  the plants
    		 			     //growth rate reduction factor
	PT=1-0.0025*Math.pow(((0.25*minT + 0.75*maxT)-26),2);
	
}
public void PGS() {				/* calculates the daily output of the plants  weight..*/
 			
	 PG= ( SRad/PD)*(1.0- (-(Math.exp(Y1*LAI))));
	
}
public void Calc_Y1() {
	Y1= 1.5-0.786*Math.pow(Math.pow(ROWSPC*0.01, 2)*PD, 0.1);
}
public  double LAIS() {
	 double dLAI;     //increase in leaf area index
	 int Di;            // rate  of plant development
	 double a;  
	if(lfNo<leefMax) {
		 System.out.println("in vegative phase...");
		 a=Math.exp(EMP2*(lfNo-nb));
		dLAI=scfac1*PT*PD*EMP1*dN*(a/(a+1));
		 return dLAI;
	}
	else {
		System.out.println("reproductive phase...");
		Di= (int) (((maxT+minT)/2)-baseT);
		dLAI=-PD*Di*p1*SLA;
		 return dLAI;
	}
	 
}
public void Integration() {
	LAI=LAI*1.1;
	dry_weight*=1.1;
	conopy_dry_weight*=1.1;
	root_dry_weight*=1.1;
	lfNo*=1.1;
	
}
public void OutPut() throws FileNotFoundException {
	File output= new File("C://Users//Meer//Documents//Plant.OUT.txt");
	PrintWriter ois= new PrintWriter(output);
	ois.println(EMP1);
	ois.println(EMP2);
	ois.println(leefMax);
	ois.println(lfNo);
	ois.println(LAI);
	ois.println(dry_weight);
	ois.println(conopy_dry_weight);
	ois.println(root_dry_weight);
	ois.println(p1);
	ois.println(PD);
	ois.println(SLA);
	ois.println(baseT);
	
	ois.close();
	
}


}
