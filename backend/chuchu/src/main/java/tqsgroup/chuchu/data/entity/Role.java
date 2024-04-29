package tqsgroup.chuchu.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
    
    @Id
    private String role;
     
    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }
}
