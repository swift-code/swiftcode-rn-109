package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import forms.LoginForm;
import forms.Signup;
import models.Profile;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.data.FormFactoryModule;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by lubuntu on 10/22/16.
 */
public class Application extends Controller{
    @Inject
    FormFactory formFactory;

    @Inject
    ObjectMapper objectMapper;

    public Result login(){
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();

        if(loginForm.hasErrors())
            return ok(loginForm.errorsAsJson());
        ObjectNode userJson = objectMapper.createObjectNode();

        User user = User.find.where().eq("email",loginForm.data().get("email")).findUnique();

        userJson.put("id",user.uid);
        userJson.put("email",user.email);
        userJson.put("password",user.password);

        return ok(userJson);
    }

    public Result signup(){
        Form<Signup> signupForm = formFactory.form(Signup.class).bindFromRequest();

        if(signupForm.hasErrors())
            return ok(signupForm.errorsAsJson());

        Profile profile = new Profile(signupForm.data().get("fname"),signupForm.data().get("lname"));
        Profile.db().save(profile);

        User user = new User(signupForm.data().get("email"),signupForm.data().get("password"));

        user.profile = profile;
        User.db().save(user);

        return ok((JsonNode) objectMapper.valueToTree(user));
    }

}
