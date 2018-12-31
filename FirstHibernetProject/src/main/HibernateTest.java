package main;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import models.Address;
import models.UserDetails;
import models.Vehicle;

public class HibernateTest {

	public static void main(String[] args) {
		
		//Create an object
		UserDetails user = new UserDetails();
		user.setUserId(1);
		user.setUserName("FirstUser");
		user.setJoinDate(new Date());
		user.setLargeDescription("its my description ..");
		
		Address addr = new Address();
		addr.setCity("Home city");
		addr.setPincode("123456");
		addr.setState(" Home state");
		addr.setStreet("Home street");
		
		user.setHomeAddress(addr);
		
		Address addr2 = new Address();
		addr2.setCity("Office city");
		addr2.setPincode("123456");
		addr2.setState("Office state");
		addr2.setStreet("Office street");
		
		user.setOfficeAddress(addr2);
		
		//adding address in a list of user object
		user.getListOfAddress().add(addr);
		user.getListOfAddress().add(addr2);
		//adding address in the collection
		user.getListOfAddressCollection().add(addr);
		user.getListOfAddressCollection().add(addr2);
		
		//create an vehicle object
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("car");
		//adding user to vehicle to set (ManyToOne many(vehicle) one(user))
		vehicle.setUser(user);
		//normally adding a vehicle to user (OneToOne one(user) one(vehicle))
		user.setVehicle(vehicle);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleName("jeep");
		//adding user to vehicle to set (ManyToOne many(vehicle) one(user))
		vehicle2.setUser(user);
		//adding multiple vehicle to set (OneToMany one(user) many(vehicle))
		user.getVehicleSet().add(vehicle);
		user.getVehicleSet().add(vehicle2);
			
		//Persist the object using hibernate.
		//step1 .Create a session Factory (using <session-factory> in hibernate.cfg.xml )
		SessionFactory sessionFactory = new Configuration()//Configuration object
				.configure()//it takes the configuration file and reads this to understand what configuration should be made 
				.buildSessionFactory();
		//Define a session object
		Session session = sessionFactory.openSession();
		//Begin transaction 
		session.beginTransaction();
		//pass the object to hibernate
		session.save(user);
		session.save(vehicle);
		session.save(vehicle2);
		//commit changes
		session.getTransaction().commit();
		//close current session
		session.close();
		
		user = null;
		vehicle = null;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/*Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		user = (UserDetails) session2.get(UserDetails.class, 1);
		vehicle = (Vehicle) session2.get(Vehicle.class, 1);
		System.out.println("user retrived "+vehicle.getVehicleName());
		session2.close();
		System.out.println("user retrived "+user.getUserName());
		*/
		
	}

}