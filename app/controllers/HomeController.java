package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ConnectionRequest;
import models.Profile;
import models.User;
import play.data.validation.ValidationError;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import sun.net.ConnectionResetException;
import sun.rmi.transport.Connection;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by lubuntu on 10/23/16.
 */
public class HomeController extends Controller {
    @Inject
    ObjectMapper objectMapper;
   public Result getprofile(Long Id){
       User user = User.find.byId(Id);
       Profile profile= user.profile.find.byId(Id);
       ObjectNode data = objectMapper.createObjectNode();
       data.put("firstName",profile.fname);
       data.put("email",user.email);
       data.put("lastname",profile.lname);
       data.put("Id",user.uid);
       data.set("connections",objectMapper.valueToTree(user.connections.stream().map(connection->{
           ObjectNode connectionJson = objectMapper.createObjectNode();
           User connectionUser= User.find.byId(connection.uid);
           Profile connectionProfile= Profile.find.byId(connection.profile.pid);
           connectionJson.put("id",connectionUser.uid);
           connectionJson.put("email",connectionUser.email);
           connectionJson.put("fname",connectionProfile.fname);
           connectionJson.put("lname",connectionProfile.lname);
           connectionJson.put("company",connectionProfile.company);
           return (connectionJson);
       }).collect(Collectors.toList())));
       return ok();
   }
}
