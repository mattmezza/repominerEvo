package net.sf.jeasyorm.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.annotation.Transient;

public class Person {

    private EntityManager em;
    
    private Long id;
    private String firstName;
    private String lastName;
    @Transient private List<Address> addresses;
    
    public Person() {
        addresses = new ArrayList<Address>();
    }
    
    public Person(EntityManager em) {
        this.em = em; 
    }
    
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public List<Address> getAddresses() {
        if (addresses == null && em != null) {
            setAddresses(em.find(Address.class, "where person_id = ?", id));
        }
        return addresses;
    }
    public void setAddresses(List<Address> addresses) { 
        this.addresses = addresses; 
        for (Address address : addresses) address.setPerson(this);
    }
    
}
