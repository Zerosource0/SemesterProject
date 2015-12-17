package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SystemUser")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  private String password;  //Pleeeeease dont store me in plain text
  
  @Id
  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  
  @OneToMany (cascade = CascadeType.ALL, mappedBy = "User")
  private List<Reservation> reservations = new ArrayList<>();
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "SystemUser_USERROLE", joinColumns = {
  @JoinColumn(name = "userName", referencedColumnName = "userName")}, inverseJoinColumns = {
  @JoinColumn(name = "roleName")})
  private List<Role> roles = new ArrayList();

  public User() {
  }
  
  public User(String userName, String password, String firstName, String lastName, String email, String phone) {
    this.userName = userName;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public List<String> getRolesAsStrings(){
    List<String> rolesAsStrings = new ArrayList();
    for(Role role : roles){
      rolesAsStrings.add(role.getRoleName());
    }
    return rolesAsStrings;
  }
  
  public void AddRole(Role role){
    roles.add(role);
    role.addUser(this);
  }
  
  public void addReservation (Reservation r)
  {
      reservations.add(r);
  }

    public List<Reservation> getReservations() {
        return reservations;
    }
  
  
    
  public List<Role> getRoles() {
   return roles;
  }
 
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getEmail(){
      return email;
  }
  
  public void setEmail(String email){
      this.email = email;
  }
 
   public String getFirstName(){
      return firstName;
  }
          
  public void setFirstName(String firstName){
      this.firstName = firstName;
}
  
    public String getLastName(){
      return lastName;
  }
  
  public void setLastName(String lastName){
      this.lastName = lastName;
  }


    @Override
    public String toString() {
        String result="User{" + "password=" + password + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", roles=" + roles + '}'+"\n";
        
        return result;
    }
  
  

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

       
}
