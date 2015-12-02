/*
 * Author: Robert Farrar
 * 		   rjf140030
 * 		   CS2336.002
 * 	
 * Parking Garage
 * 
 * Design:	
 * 		To simulate a parking garage, a two-dimensional array will be created 
 * to store the level and space number for each of the three different kinds of 
 * parking spaces: large, compact, and motorcycle. Bus, Car, and Motorcycle objects will
 * be randomly generated, and then placed in their designated two-dimensional array,
 * depending on the vehicle type, and vacancy of previous spaces, to fill the parking
 * garage to the full capacity.
 * 		The vehicle class is the parent class of Car, Bus, and Motorcycle,
 * all vehicles have a license plate. A bus requires 5 sequential large spaces, a car 
 * requires a large or compact space, and a motorcycle can use large, compact or motorcycle spaces.
 * Cars will park in the compact spots, and once there are no more compact spots left, then they
 * will begin to park in large spaces. Motorcycles will park in the motorcycle spots, once
 * all the motorcycle spots are full, then they will begin to park in compact spots. If the compact
 * spots are all filled, then the Motorcycles will park in the large spots.
 * 
 */

import java.util.*;

public class Parking {

	// Class variables:

	// random variable to randomly generate 3 kinds of vehicles
	private Random rn = new Random();
	// the parking garage has 5 levels
	private int floors = 5;
	// each level has 6 large spaces, 18 compact spaces, and 6 motorcycle spaces
	private int largeSpacesPerFloor = 6;
	private int carSpacesPerFloor = 18;
	private int motorcycleSpacesPerFloor = 6;
	// total spaces in parking garage
	private int totalSpaces = (floors * (largeSpacesPerFloor + carSpacesPerFloor + motorcycleSpacesPerFloor));
	// total spaces used
	private static int spacesUsed = 0;
	// two-dimensional array of Vehicles for each of the 3 kinds of parking
	// spots
	private Vehicle carSpaces[][] = new Vehicle[floors][carSpacesPerFloor];
	private Vehicle motorcycleSpaces[][] = new Vehicle[floors][motorcycleSpacesPerFloor];
	private Vehicle largeSpaces[][] = new Vehicle[floors][largeSpacesPerFloor];

	// Class methods:
	// to park a vehicle, returns string description of vehcile's ability to
	// park
	private String park(Vehicle v) {
		String ret = null;

		// if there are open spaces in the parking garage
		if (spacesUsed < totalSpaces) {
			// depending on class type of the Vehicle object, park either a bus,
			// car, or motorcycle.
			if (v.getClass() == Bus.class) {
				ret = parkBus((Bus) v);
			} else if (v.getClass() == Car.class) {
				ret = parkCar((Car) v);
			} else if (v.getClass() == Motorcycle.class) {
				ret = parkMotorcycle((Motorcycle) v);
				// if the object isn't of type vehicle
			} else {
				ret = "Unknown vehicle, cannot park.";
			}
		}

		return ret;
	}

	// to park a bus, returns string description of vehcile's ability to park
	private String parkBus(Bus b) {
		// clear description
		String ret = null;

		// nested for-loop to cycle through each of the large spaces on each
		// level

		// outer for-loop to cycle levels
		for (int idx = 0; idx < floors; idx++) {
			// a bus can park only if either the first or second space is vacant
			if (largeSpaces[idx][0] == null || largeSpaces[idx][1] == null) {
				// where the bus begins to park if the 1st space is empty
				int startAt = 0;
				// where the bus begins to park if the 2nd space is empty
				if (largeSpaces[idx][0] != null) {
					startAt = 1;
				}
				// fill in 4 following spaces dependent on if bus is initially
				// parked in 1st or 2nd space
				for (int jdx = startAt; jdx < 5; jdx++) {
					largeSpaces[idx][jdx] = b;
				}

				// 5 spaces were used to park a bus
				spacesUsed += 5;

				// new description is added to object containing position of
				// parking place
				ret = "Bus " + b.license + " parked on floor " + (idx + 1) + " in spaces LG #" + (startAt + 1)
						+ " through LG #" + (startAt + 5);

				break;
			}
		}

		// if a bus wasn't able to be parked
		if (ret == null) {
			// change description
			ret = "No Bus spaces are available for " + b.license + ", current parking garage";
		}

		return ret;
	}

	// to park a motorcycles, returns string description of vehcile's ability to
	// park
	private String parkMotorcycle(Motorcycle mc) {

		// clear the description
		String ret = null;

		// cycle through the levels
		for (int idx = 0; idx < floors; idx++) {

			// cycle through the motorcycle spaces
			for (int jdx = 0; jdx < motorcycleSpacesPerFloor; jdx++) {
				// if the space is empty
				if (motorcycleSpaces[idx][jdx] == null) {
					spacesUsed++;
					motorcycleSpaces[idx][jdx] = mc;
					// change description
					ret = "Motorcycle " + mc.license + " parked on floor " + (idx + 1) + " in space MC #" + (jdx + 1);
					break;
				}
			}
			// break out of loop if motorcycle is parked
			if (ret != null) {
				break;
			}
		}

		// if all motorcycle spots full, cycle through the compact spots.
		if (ret == null) {
			for (int idx = 0; idx < floors; idx++) {
				for (int jdx = 0; jdx < carSpacesPerFloor; jdx++) {
					// if a space is empty
					if (carSpaces[idx][jdx] == null) {
						spacesUsed++;
						carSpaces[idx][jdx] = mc;
						// change description
						ret = "Motorcycle " + mc.license + " parked on floor " + (idx + 1) + " in space C #"
								+ (jdx + 1);
						break;
					}
				}
				// break out of loop if motocycle is parked
				if (ret != null) {
					break;
				}
			}
		}

		// after checking compact spaces, check large spaces
		if (ret == null) {
			// cycle through large spaces
			for (int idx = 0; idx < floors; idx++) {
				for (int jdx = 0; jdx < largeSpacesPerFloor; jdx++) {
					// if there's an empty space
					if (largeSpaces[idx][jdx] == null) {
						spacesUsed++;
						largeSpaces[idx][jdx] = mc;
						// change description
						ret = "Motorcycle " + mc.license + " parked on floor " + (idx + 1) + " in space LG #"
								+ (jdx + 1);
						break;
					}
				}
				// break out of loop if motocycle is parked
				if (ret != null) {
					break;
				}
			}
		}
		// if motorcycle was unable to park
		if (ret == null) {
			ret = "No Car/Large spaces are available for " + mc.license + ", current parking garage";
		}

		return ret;
	}

	// to park a car, returns string description of vehcile's ability to park
	private String parkCar(Car c) {

		// clear description
		String ret = null;

		// First look in Car Spaces
		for (int idx = 0; idx < floors; idx++) {
			for (int jdx = 0; jdx < carSpacesPerFloor; jdx++) {
				// if car space is empty
				if (carSpaces[idx][jdx] == null) {
					spacesUsed++;
					carSpaces[idx][jdx] = c;
					// change description
					ret = "Car " + c.license + " parked on floor " + (idx + 1) + " in space C #" + (jdx + 1);
					break;
				}
			}
			// break out of loop if car is parked
			if (ret != null) {
				break;
			}
		}

		// Last look in Large Spaces
		if (ret == null) {
			for (int idx = 0; idx < floors; idx++) {
				for (int jdx = 0; jdx < largeSpacesPerFloor; jdx++) {
					// if large space is empty
					if (largeSpaces[idx][jdx] == null) {
						spacesUsed++;
						largeSpaces[idx][jdx] = c;
						// change description
						ret = "Car " + c.license + " parked on floor " + (idx + 1) + " in space LG #" + (jdx + 1);
						break;
					}
				}
				// break out of loop if car is parked
				if (ret != null) {
					break;
				}
			}
		}

		// if car is not parked
		if (ret == null) {
			ret = "No Car/Large spaces are available for " + c.license + ", current parking garage";
		}

		return ret;
	}

	// creating a random vehicle, returns new vehicle
	private Vehicle nextVehicle() {
		Vehicle v = null;
		// 1 bus per level, 18 cars, 6 motorcycles = 25 ideal vehicles total
		int num = rn.nextInt(1 + carSpacesPerFloor + motorcycleSpacesPerFloor);

		// randomness of each vehicle is ratio of subsequent type of spot to
		// ideal total vehicles per level

		// 1 bus per 25 ideal vehicles total
		if (num < 1) {
			v = new Bus();
			// 18 cars per 25 ideal vehicles total
		} else if (num >= 1 && num < (1 + carSpacesPerFloor)) {
			v = new Car();
			// 6 motorcycles per 25 ideal vehicles total
		} else {
			v = new Motorcycle();
		}

		return v;
	}

	// printing two-dimensional arrays in 3 rows of 10 spaces per level,
	// printing B for Bus
	// occupancy in space, C for car occupancy in space, and M for motorcycle
	// occupancy in space.
	// l for empty large space, c for empty compact space, m for empty
	// motorcycles space
	private void printParkingMap() {
		for (int j = 0; j < 5; j++) {
			System.out.print("Level " + (j + 1) + ":");

			// 1st row
			for (int i = 0; i < 6; i++) {
				if (largeSpaces[j][i] instanceof Bus) {
					System.out.print('B');
				} else if (largeSpaces[j][i] instanceof Car) {
					System.out.print('C');
				} else if (largeSpaces[j][i] instanceof Motorcycle) {
					System.out.print('M');
				} else {
					System.out.print('l');
				}
			}
			for (int i = 0; i < 4; i++) {
				if (carSpaces[j][i] instanceof Car) {
					System.out.print('C');
				} else if (carSpaces[j][i] instanceof Motorcycle) {
					System.out.print('M');
				}

				else {
					System.out.print('c');
				}

				// 2nd row
			}
			System.out.print("\t");
			for (int i = 4; i < 14; i++) {
				if (carSpaces[j][i] instanceof Car) {
					System.out.print('C');
				} else if (carSpaces[j][i] instanceof Motorcycle) {
					System.out.print('M');
				}

				else {
					System.out.print('c');
				}

				// 3rd row
			}
			System.out.print("\t");
			for (int i = 14; i < 18; i++) {
				if (carSpaces[j][i] instanceof Car) {
					System.out.print('C');
				} else if (carSpaces[j][i] instanceof Motorcycle) {
					System.out.print('M');
				}

				else {
					System.out.print('c');
				}
			}
			for (int i = 0; i < 6; i++) {
				if (motorcycleSpaces[j][i] != null) {
					System.out.print('M');
				} else {
					System.out.print('m');
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// main function
	public static void main(String[] args) {

		// create parking garage
		Parking p = new Parking();

		String status = null;
		// Initial description of parking garage
		System.out.println(spacesUsed + " spaces used:");
		System.out.println("Empty parking garage:");

		// display parking garage
		p.printParkingMap();

		// continue to create and park vehicles if there are empty spaces in the
		// parking garage
		do {
			Vehicle v = p.nextVehicle();
			// status changes iff total spaces are used
			status = p.park(v);
			System.out.println(+ spacesUsed + " spaces used:");
			System.out.println((status != null) ? status : "Parking Garage is FULL");
			p.printParkingMap();

		} while (status != null);

	}
}
