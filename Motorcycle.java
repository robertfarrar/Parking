/*
 * Author: Robert Farrar
 * 		   rjf140030
 * 		   CS2336.002
 */
//motorcycle
public class Motorcycle extends Vehicle {
	
	private static int mcid = 0;
	//assign motorcycle identification number for every new motorcycle created
	public Motorcycle() {
		license = "MC-" + (++mcid);
	}
}
