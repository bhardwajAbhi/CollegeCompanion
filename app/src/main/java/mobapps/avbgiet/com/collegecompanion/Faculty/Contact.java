package mobapps.avbgiet.com.collegecompanion.Faculty;

public class Contact {

  //variables
  private String Email, Name, Phone, ImagePath;


  public Contact() {
  }

  public Contact(String email, String name, String phone, String imagePath) {
    Email = email;
    Name = name;
    Phone = phone;
    ImagePath = imagePath;
  }

  //getters and setters

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getPhone() {
    return Phone;
  }

  public void setPhone(String phone) {
    Phone = phone;
  }

  public String getImagePath() {
    return ImagePath;
  }

  public void setImagePath(String imagePath) {
    ImagePath = imagePath;
  }
}
