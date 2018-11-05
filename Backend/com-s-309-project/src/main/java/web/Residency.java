package web;

public class Residency {

	public Residency() {
		
	}
	
	private String location;
	private String sublocation;
	private String address;
	private String netID;
	
	public Residency(String location, String sublocation, String address, String netID) {
		
		this.location = location;
		this.sublocation = sublocation;
		this.address = address;
		this.netID = netID;
	}
	
	public Residency(String location, String sublocation, String address) {
		
		this.location = location;
		this.sublocation = sublocation;
		this.address = address;

	}
	
	public String getNetID() {
		
		return netID;
	}
	
	public void setNetID(String newNetID) {
		
		this.netID = newNetID;
	}
	
	public String getLocation() {
		
		return location;
	}
	
	public void setLocation(String newLocation) {
		
		location = newLocation;
	}
	
	public String getSublocation() {
		
		return sublocation;
		
	}
	
	public void setSublocation(String newSublocation) {
		
		sublocation = newSublocation;
	}
	
	public String getAddress() {
		
		return address;
	}
	
	public void setAddress(String newAddress) {
		
		address = newAddress;
	}
	
	public String toString() {
		
		return "Residency [location= " + location + ", sublocation= " + sublocation + ", address= " + address + "]";
	}
	
}
