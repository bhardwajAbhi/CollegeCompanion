package mobapps.avbgiet.com.collegecompanion.notifications;

public class Notification {

  private String Title, Desc, Image, Date;

  public Notification()
  {
    //empty constructor
  }


  public Notification(String title, String desc, String image, String date) {
    Title = title;
    Desc = desc;
    Image = image;
    Date = date;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDesc() {
    return Desc;
  }

  public void setDesc(String desc) {
    Desc = desc;
  }

  public String getImage() {
    return Image;
  }

  public void setImage(String image) {
    Image = image;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    Date = date;
  }
}
