package net.sf.jeasyorm.model;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

@Table(name="TPERSON")
public class MyPerson {
    
    @Column(name="ID") private Long myId;
    private String firstName;
    @Column(name="LAST_NAME") private String name;
    
    public MyPerson() { }
    public MyPerson(String firstName, String name) {
        this.firstName = firstName;
        this.name = name;
    }

    public Long getMyId() { return myId; }
    public void setMyId(Long myId) { this.myId = myId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
}
