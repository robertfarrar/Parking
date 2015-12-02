/*
 * Author: Robert Farrar
 * 		   rjf140030
 * 		   CS2336.002
 */
//car type
public class Car extends Vehicle {

	private static int cid = 0;
	//assign car identification number for every new car created
	public Car() {
		license = "C-" + (++cid);
	}
}
