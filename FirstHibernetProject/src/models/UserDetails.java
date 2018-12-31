package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity (name="USER_DETAILS")//javax.persistence.Entity -> not from hibernate (org.hibernate.annotations.Entity)
@Table (name="UserDetails")//give a user Defined name to hibernate 
public class UserDetails {

	@Id//javax.persistence.Id -> not from hibernate (org.hibernate.annotations.Id)
	@Column (name="USER_ID")
	@GeneratedValue (strategy= GenerationType.AUTO)//generate a value for the userId attribute
	private int userId;
	@Column (name="USER_NAME")
	private String userName;
	@Transient//hibernet will ignore the variable
	private String unNecessaryVar;
	@Lob//to specify the hibernet a large object
	private String largeDescription;
	@Temporal (TemporalType.DATE) //specify hibernet to use Date data type in Database(sql)
	private Date joinDate;
	@Embedded//to add an object in the table(which class should annotated as the @Embededable)
	@AttributeOverrides({//to add multiple attribute overrides
		@AttributeOverride(name="street", column=@Column(name="office_Street")),//to override a particular attribute
		@AttributeOverride(name="city", column=@Column(name="office_city")),
		@AttributeOverride(name="state", column=@Column(name="office_state")),
		@AttributeOverride(name="pincode", column=@Column(name="office_pincode")),
	})
	private Address officeAddress;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="home_Street")),
		@AttributeOverride(name="city", column=@Column(name="home_city")),
		@AttributeOverride(name="state", column=@Column(name="home_state")),
		@AttributeOverride(name="pincode", column=@Column(name="home_pincode")),
	})
	private Address homeAddress;
	
	@ElementCollection//specify hibernate to store the set of address in a different database table with a @id attribute of the current class 
	@JoinTable(
		name="userAddressSet",
		joinColumns=@JoinColumn(name="userId")
	)
	private Set<Address> listOfAddress = new HashSet();
	
	@ElementCollection(//specify hibernate to store the collection of address in a different database table with a @id attribute of the current class 
			fetch=FetchType.EAGER)//to specify the hibernet to load the data eagerly (not lazy loading-> load a proxy first then fetch the data)
	@JoinTable(
		name="userAddressCollection",
		joinColumns=@JoinColumn(name="userId")
	)
	@GenericGenerator(
			name="hilo-gen", 
			strategy="hilo")
	@CollectionId(
			columns = { @Column (name="addressId") },
			generator = "hilo-gen", 
			type = @Type(type="long"))	
	private Collection<Address> listOfAddressCollection = new ArrayList();
	
	@OneToOne//declaring the relation between the two Entity table*********************one to one*****
	@JoinColumn(name="vehicleId")
	private Vehicle vehicle ;
	
	//@OneToMany//declaring the relation between the two Entity table********************** one to many****
	@ManyToMany
	@JoinTable(
			name="user_vehicle",
			joinColumns=@JoinColumn(name="userId"),
			inverseJoinColumns=@JoinColumn(name="vehicleId")
			)
	private Set<Vehicle> vehicleSet = new HashSet<>() ;

	
	
	public Set<Vehicle> getVehicleSet() {
		return vehicleSet;
	}

	public void setVehicleSet(Set<Vehicle> vehicleSet) {
		this.vehicleSet = vehicleSet;
	}

	/*public String getUnNecessaryVar() {
		return unNecessaryVar;
	}

	public void setUnNecessaryVar(String unNecessaryVar) {
		this.unNecessaryVar = unNecessaryVar;
	}*/

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Collection<Address> getListOfAddressCollection() {
		return listOfAddressCollection;
	}

	public void setListOfAddressCollection(Collection<Address> listOfAddressCollection) {
		this.listOfAddressCollection = listOfAddressCollection;
	}

	public Set<Address> getListOfAddress() {
		return listOfAddress;
	}

	public void setListOfAddress(Set<Address> listOfAddress) {
		this.listOfAddress = listOfAddress;
	}

	public Address getOfficeAddress() {
		return officeAddress;
	}
	
	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}
	
	public Address getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getLargeDescription() {
		return largeDescription;
	}
	
	public void setLargeDescription(String largeDescription) {
		this.largeDescription = largeDescription;
	}
	
	public Date getJoinDate() {
		return joinDate;
	}
	
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	//	@Id// annotation can be used over the getter method of that particular variable
	//	@Column (name="USER_ID")
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	//	@Column (name="USER_NAME")//annotation can be used over the getter method of that particular variable
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
