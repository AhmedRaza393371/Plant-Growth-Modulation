import java.io.IOException;
import java.time.LocalTime;

public class TImerClass {
	public static void main(String[] args) throws IOException {
		LocalTime start= LocalTime.of(8,0);
		LocalTime end= LocalTime.of(20,0);
		
		/*-----------Instatiation------------*/
		Plant plant= new Plant();
		SoilWater sw1= new SoilWater(plant);
		Weather weather= new Weather();
		Simulation s= new Simulation();
		/*-----------TimeLoop---------------*/
		
		while(true) {
			LocalTime currentTime= LocalTime.now();
			if(currentTime.isBefore(start)&&currentTime.isAfter(end)) {
				s.Initialization(plant, sw1, weather);
				
				 for(int i=0; i<3;i++) {
					 s.Rate_Calculation(plant, sw1, weather);
					 s.Initialization(plant, sw1, weather);
				 }
				 s.Output( plant);
			}
			try {
				Thread.sleep(60*1000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}}
}
