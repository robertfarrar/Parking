/*
 * Author: Robert Farrar
 * 		   rjf140030
 * 		   CS2336.002
 */
//bus class
public class Bus extends Vehicle {
	
	private static int bid = 0;
	//assign bus identification number for every new bus created
	public Bus() {
		license = "B-" + (++bid);
	}
}
