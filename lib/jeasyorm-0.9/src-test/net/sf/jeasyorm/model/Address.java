package net.sf.jeasyorm.model;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.annotation.Transient;

public class Address {
    
    private EntityManager em;
    
    private Long id;
    private String street;
    private String city;
    private Long personId;
    @Transient private Person person;
    
    public Address() {
        // nothing to do
    }
    
    public Address(EntityManager em) {
        this.em = em;
    }
    
    public Address(Person person, String street, String city) {
        this.person = person;
        this.street = street;
        this.city = city;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Long getPersonId() { return personId; }
    public void setPersonId(Long id) { this.personId = id; }
    
    public Person getPerson() {
        if (personId == null) {
            return (person = null);
        } else if (person == null || !personId.equals(person.getId())) {
            return (person = em.load(Person.class, personId));
        } else {
            return person;
        }
    }
    public void setPerson(Person person) { 
        this.person = person;
        this.personId = person != null ? person.getId() : null;
    }
 
}
