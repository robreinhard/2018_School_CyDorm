package web;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Location")
public class Location {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int location_id;
	
	@Column(name="location")
	private String location;
	
	//Address to user relationship
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "location_sublocation",
    		   joinColumns = @JoinColumn(name= "location_id"),
    		   inverseJoinColumns = @JoinColumn(name = "sublocation_id")
    )
    private Set<Address> addresses;
	
  
	public Location(String location) {
		
		this.location = location;
		
	}
	
	public void setSublocation(String sublocation) {
		
		this.location = location;
	}
	
	@Override
    public String toString() {
        return "Sublocation [sublocation_id=" + location_id + ", sublocation=" + location + "]";
    }
    
	
}