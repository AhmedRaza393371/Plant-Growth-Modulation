import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import java.time.LocalTime;
public  class  Simulation{
	/* -------------- initialization-----------------*/
	public void Initialization(Plant plant,SoilWater sw1,Weather weather) throws IndexOutOfBoundsException, IOException {
		plant.getData();
		sw1.GetData();
		sw1.RUNOFF();
		sw1.STRESS();
		weather.GetData();
		}
	/*  ----------------------------------------------*/
	
	/*----------------Rate-Calculation-----------------*/
	public void Rate_Calculation(Plant plant,SoilWater sw1,Weather weather) {
	plant.PTS();
	plant.Calc_Y1();
	plant.PGS();
	plant.LAIS();
	
	
	sw1.DRAINE();
	sw1.rate_Calc();
	sw1.ROF();
	sw1.INF();
	sw1.ETpS();
	sw1.ESaS();
	sw1.SWFAC2();}
	/*-----------------------------------------------------*/
	
	/* ---------------integration----------------
	 * ----------*/
	public void Integration(Plant plant,SoilWater sw1,Weather weather) {
	plant.Integration();
	
	sw1.updatedValue(); //updates swc
	sw1.SWC_ADJ();
	sw1.Stress2();
	sw1.SWFAC2();
}
	
	public void Output(Plant plant, SoilWater sw1) throws FileNotFoundException {
		plant.OutPut();
		sw1.Output();
	}

	

}
