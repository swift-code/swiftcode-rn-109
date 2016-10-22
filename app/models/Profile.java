package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by lubuntu on 10/22/16.
 */
@Entity
public class Profile extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long pid;
    public String fname;
    public String lname;
    public String company;

    public static Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(Profile.class);

    public Profile(String fname, String lname) {
        this.lname = lname;
        this.fname = fname;
    }
}
