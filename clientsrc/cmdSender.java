import java.rmi.RemoteException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import LockManager.DeadlockException;
import ResInterface.ResourceManager;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;


public class cmdSender {
	
	static int Id;
	static int Cid;
    static int flightNum;
    static int flightPrice;
    static int flightSeats;
    static boolean Room;
    static boolean Car;
    static int price;
    static int numRooms;
    static int numCars;
    static String location;
	static String strprice = "10";
	static String strqty = "10";
	static int maxInt = 100;
			
	public static String pickLocation()
	{
		Random rand = new Random();
		switch (rand.nextInt(4)) {
		case 1:
			return "Sao Paulo";
		case 2:
			return "Paris";
		case 3:
			return "Vancouver";
		case 4:
			return "Washington";

		default:
			return "Montreal";
		}
	}

    public static String randInt(int n)
    {
    	Random rand = new Random();
    	return new Integer(rand.nextInt(n)).toString();
    }
    public static String toString(int tid)
    {
    	return new Integer(tid).toString();
    }
	private static String buildCommand(int option, int tid)
	{
		
		if(option == 0)
		    return "newflight,"+toString(tid)+","+randInt(maxInt)+","+strqty+","+strprice;
		else if(option == 1)
		    return "newcar,"+new Integer(tid).toString()+","+pickLocation()+","+strqty +","+strprice;
		else if(option == 2)
		    return "newroom,"+new Integer(tid).toString()+","+randInt(maxInt)+","+strprice+","+strqty;
		else if(option == 3)
		    return "newcustomer,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 4)
		    return "deleteflight,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 5)
		    return "deletecar,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 6)
		    return "deleteroom,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 7)
		    return "deletecustomer,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 8)
		    return "queryflight,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 9)
		    return "querycar,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 10)
		    return "queryroom,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 11)
		    return "querycustomer,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 12)
		    return "queryflightprice,"+new Integer(tid).toString()+","+randInt(maxInt);
		else if(option == 13)
		    return "querycarprice,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 14)
		    return "queryroomprice,"+new Integer(tid).toString()+","+pickLocation();
		else if(option == 15)
		    return "reserveflight,"+new Integer(tid).toString()+","+randInt(maxInt)+","+randInt(maxInt);
		else if(option == 16)
		    return "reservecar,"+new Integer(tid).toString()+","+randInt(maxInt)+","+pickLocation();
		else if(option == 17)
		    return "reserveroom,"+new Integer(tid).toString()+","+randInt(maxInt)+","+pickLocation();
		else if(option == 18)
		    return "itinerary,"+new Integer(tid).toString()+","+randInt(maxInt)+","+randInt(maxInt)+","+pickLocation()+",true,true";
		else
			return "";
	}
	
	
	public static void sendCommand( client obj, ResourceManager rm, int tid)
	{
		int option = new Random().nextInt(19);
		String command = cmdSender.buildCommand(option,tid);
		System.out.println(command);
		command=command.trim();
		Vector arguments=obj.parse(command);
		
		//decide which of the commands this was
		switch(obj.findChoice((String)arguments.elementAt(0))){
		case 1: //help section
		    if(arguments.size()==1)   //command was "help"
			obj.listCommands();
		    else if (arguments.size()==2)  //command was "help <commandname>"
			obj.listSpecific((String)arguments.elementAt(1));
		    else  //wrong use of help command
			System.out.println("Improper use of help command. Type help or help, <commandname>");
		    break;
		    
		case 2:  //new flight
		    if(arguments.size()!=5){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Adding a new Flight using id: "+arguments.elementAt(1));
		    System.out.println("Flight number: "+arguments.elementAt(2));
		    System.out.println("Add Flight Seats: "+arguments.elementAt(3));
		    System.out.println("Set Flight Price: "+arguments.elementAt(4));
		    
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			flightNum = obj.getInt(arguments.elementAt(2));
			flightSeats = obj.getInt(arguments.elementAt(3));
			flightPrice = obj.getInt(arguments.elementAt(4));
			if(rm.addFlight(Id,flightNum,flightSeats,flightPrice))
			    System.out.println("Flight added");
			else
			    System.out.println("Flight could not be added");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 3:  //new Car
		    if(arguments.size()!=5){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Adding a new Car using id: "+arguments.elementAt(1));
		    System.out.println("Car Location: "+arguments.elementAt(2));
		    System.out.println("Add Number of Cars: "+arguments.elementAt(3));
		    System.out.println("Set Price: "+arguments.elementAt(4));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			numCars = obj.getInt(arguments.elementAt(3));
			price = obj.getInt(arguments.elementAt(4));
			if(rm.addCars(Id,location,numCars,price))
			    System.out.println("Cars added");
			else
			    System.out.println("Cars could not be added");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 4:  //new Room
		    if(arguments.size()!=5){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Adding a new Room using id: "+arguments.elementAt(1));
		    System.out.println("Room Location: "+arguments.elementAt(2));
		    System.out.println("Add Number of Rooms: "+arguments.elementAt(3));
		    System.out.println("Set Price: "+arguments.elementAt(4));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			numRooms = obj.getInt(arguments.elementAt(3));
			price = obj.getInt(arguments.elementAt(4));
			if(rm.addRooms(Id,location,numRooms,price))
			    System.out.println("Rooms added");
			else
			    System.out.println("Rooms could not be added");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 5:  //new Customer
			if(arguments.size()!=3){
				obj.wrongNumber();
				break;
		    }
		    	System.out.println("Adding a new Customer using id:"+arguments.elementAt(1) + " and cid " +arguments.elementAt(2));
		    try{
				Id = obj.getInt(arguments.elementAt(1));
				Cid = obj.getInt(arguments.elementAt(2));
				//rm.newCustomerWithId(Id,Cid);
				System.out.println("new customer id:"+Cid);
		    }
		    catch(Exception e){
				System.out.println("EXCEPTION:");
				System.out.println(e.getMessage());
				e.printStackTrace();
		    }
			    
		    break;
		    
		case 6: //delete Flight
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Deleting a flight using id: "+arguments.elementAt(1));
		    System.out.println("Flight Number: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			flightNum = obj.getInt(arguments.elementAt(2));
			if(rm.deleteFlight(Id,flightNum))
			    System.out.println("Flight Deleted");
			else
			    System.out.println("Flight could not be deleted");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 7: //delete Car
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Deleting the cars from a particular location  using id: "+arguments.elementAt(1));
		    System.out.println("Car Location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			
			if(rm.deleteCars(Id,location))
			    System.out.println("Cars Deleted");
			else
			    System.out.println("Cars could not be deleted");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 8: //delete Room
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Deleting all rooms from a particular location  using id: "+arguments.elementAt(1));
		    System.out.println("Room Location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			if(rm.deleteRooms(Id,location))
			    System.out.println("Rooms Deleted");
			else
			    System.out.println("Rooms could not be deleted");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 9: //delete Customer
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Deleting a customer from the database using id: "+arguments.elementAt(1));
		    System.out.println("Customer id: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			if(rm.deleteCustomer(Id,customer))
			    System.out.println("Customer Deleted");
			else
			    System.out.println("Customer could not be deleted");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 10: //querying a flight
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a flight using id: "+arguments.elementAt(1));
		    System.out.println("Flight number: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			flightNum = obj.getInt(arguments.elementAt(2));
			int seats=rm.queryFlight(Id,flightNum);
			System.out.println("Number of seats available:"+seats);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 11: //querying a Car Location
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a car location using id: "+arguments.elementAt(1));
		    System.out.println("Car location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			numCars=rm.queryCars(Id,location);
			System.out.println("number of Cars at this location:"+numCars);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 12: //querying a Room location
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a room location using id: "+arguments.elementAt(1));
		    System.out.println("Room location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			numRooms=rm.queryRooms(Id,location);
			System.out.println("number of Rooms at this location:"+numRooms);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 13: //querying Customer Information
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying Customer information using id: "+arguments.elementAt(1));
		    System.out.println("Customer id: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			String bill=rm.queryCustomerInfo(Id,customer);

			System.out.println("Customer info:"+bill);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;		       
		    
		case 14: //querying a flight Price
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a flight Price using id: "+arguments.elementAt(1));
		    System.out.println("Flight number: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			flightNum = obj.getInt(arguments.elementAt(2));
			price=rm.queryFlightPrice(Id,flightNum);
			System.out.println("Price of a seat:"+price);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 15: //querying a Car Price
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a car price using id: "+arguments.elementAt(1));
		    System.out.println("Car location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			price=rm.queryCarsPrice(Id,location);
			System.out.println("Price of a car at this location:"+price);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }			    
		    break;

		case 16: //querying a Room price
		    if(arguments.size()!=3){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Querying a room price using id: "+arguments.elementAt(1));
		    System.out.println("Room Location: "+arguments.elementAt(2));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			location = obj.getString(arguments.elementAt(2));
			price=rm.queryRoomsPrice(Id,location);
			System.out.println("Price of Rooms at this location:"+price);
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 17:  //reserve a flight
		    if(arguments.size()!=4){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Reserving a seat on a flight using id: "+arguments.elementAt(1));
		    System.out.println("Customer id: "+arguments.elementAt(2));
		    System.out.println("Flight number: "+arguments.elementAt(3));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			flightNum = obj.getInt(arguments.elementAt(3));
			if(rm.reserveFlight(Id,customer,flightNum))
			    System.out.println("Flight Reserved");
			else
			    System.out.println("Flight could not be reserved.");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 18:  //reserve a car
		    if(arguments.size()!=4){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Reserving a car at a location using id: "+arguments.elementAt(1));
		    System.out.println("Customer id: "+arguments.elementAt(2));
		    System.out.println("Location: "+arguments.elementAt(3));
		    
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			location = obj.getString(arguments.elementAt(3));
			
			if(rm.reserveCar(Id,customer,location))
			    System.out.println("Car Reserved");
			else
			    System.out.println("Car could not be reserved.");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 19:  //reserve a room
		    if(arguments.size()!=4){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Reserving a room at a location using id: "+arguments.elementAt(1));
		    System.out.println("Customer id: "+arguments.elementAt(2));
		    System.out.println("Location: "+arguments.elementAt(3));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			location = obj.getString(arguments.elementAt(3));
			
			if(rm.reserveRoom(Id,customer,location))
			    System.out.println("Room Reserved");
			else
			    System.out.println("Room could not be reserved.");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    
		case 20:  //reserve an Itinerary
		    if(arguments.size()<7){
			obj.wrongNumber();
			break;
		    }
		    System.out.println("Reserving an Itinerary using id:"+arguments.elementAt(1));
		    System.out.println("Customer id:"+arguments.elementAt(2));
		    for(int i=0;i<arguments.size()-6;i++)
			System.out.println("Flight number"+arguments.elementAt(3+i));
		    System.out.println("Location for Car/Room booking:"+arguments.elementAt(arguments.size()-3));
		    System.out.println("Car to book?:"+arguments.elementAt(arguments.size()-2));
		    System.out.println("Room to book?:"+arguments.elementAt(arguments.size()-1));
		    try{
			Id = obj.getInt(arguments.elementAt(1));
			int customer = obj.getInt(arguments.elementAt(2));
			Vector flightNumbers = new Vector();
			for(int i=0;i<arguments.size()-6;i++)
			    flightNumbers.addElement(arguments.elementAt(3+i));
			location = obj.getString(arguments.elementAt(arguments.size()-3));
			Car = obj.getBoolean(arguments.elementAt(arguments.size()-2));
			Room = obj.getBoolean(arguments.elementAt(arguments.size()-1));
			
			if(rm.itinerary(Id,customer,flightNumbers,location,Car,Room))
			    System.out.println("Itinerary Reserved");
			else
			    System.out.println("Itinerary could not be reserved.");
		    }
		    catch(Exception e){
			System.out.println("EXCEPTION:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		    }
		    break;
		    		    
		default:
		    System.out.println("The interface does not support this command.");
		    break;
		}//end of switch
	}
}
