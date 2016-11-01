package com.dreamchain.skeleton.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.dreamchain.skeleton.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dreamchain.skeleton.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping(UserController.URL)
@PropertySource("classpath:config.properties")

public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class.getName());


    static final String URL = "/user";

    @Autowired
    UserService userService;
    @Autowired
    Environment environment;


    @RequestMapping(method = RequestMethod.POST, params = "_method=put")
    public String put(Model model, @Valid UserGrid userGrid, BindingResult result) {
        if (result.hasErrors()) {
            return URL;
        }
        return "redirect:" + URL;
    }



    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map saveUser(@RequestBody User user,HttpSession httpSession) throws ParseException {
        String successMsg = "";
        String validationError="";
        String invalidUserError="";
        HashMap<String, String> serverResponse = new HashMap<>();
        boolean isLoggedUserInvalid=checkLoggedInUserExistence(httpSession);
        if(isLoggedUserInvalid) invalidUserError= environment.getProperty("user.invalid.error.msg");
        if(!isLoggedUserInvalid)validationError = userService.save(user);
        if (validationError.length() == 0 && !isLoggedUserInvalid) successMsg = environment.getProperty("user.save.success.msg");
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("validationError", validationError);
        serverResponse.put("invalidUserError", invalidUserError);
        return serverResponse;
    }


    @RequestMapping(value = "/principle", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Map userPrinciple() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("userName", currentUser.getName());
        userDetails.put("email", currentUser.getEmail());
        userDetails.put("id", String.valueOf(currentUser.getId()));
        userDetails.put("version", String.valueOf(currentUser.getVersion()));
        return userDetails;
    }



    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public
    @ResponseBody
    Map ChangePassword(@RequestBody Map<String, String> userInfo, HttpSession httpSession) throws Exception {
        String successMsg = "";
        String invalidUserError="";
        String validationError="";
        HashMap<String, String> serverResponse = new HashMap<>();
        boolean isLoggedUserInvalid=checkLoggedInUserExistence(httpSession);
        if(isLoggedUserInvalid) invalidUserError= environment.getProperty("user.invalid.error.msg");
        if(!isLoggedUserInvalid) validationError = userService.changePassword(userInfo.get("userName"), userInfo.get("oldPassword"), userInfo.get("newPassword"));
        if (validationError.length() == 0 && !isLoggedUserInvalid) {
            httpSession.invalidate();
            successMsg = environment.getProperty("user.password.change.success.msg");
        }
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("invalidUserError", invalidUserError);
        return serverResponse;
    }





    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public
    @ResponseBody
    List<User> loadUserList(@RequestBody String email,HttpSession httpSession) {
        List userList=new ArrayList();
        boolean isLoggedUserInvalid=checkLoggedInUserExistence(httpSession);
        if(!isLoggedUserInvalid)
        logger.info("start");
        userList = userService.findAll(email);
        return userList;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map updateUser(@RequestBody User user,HttpSession httpSession) throws ParseException {


        String successMsg = "";
        String validationError="";
        String invalidUserError="";
        boolean isLoggedUserInvalid=false;
        HashMap serverResponse = new HashMap<>();
        isLoggedUserInvalid=checkLoggedInUserExistence(httpSession);
        if(isLoggedUserInvalid) invalidUserError= environment.getProperty("user.invalid.error.msg");
        if(!isLoggedUserInvalid) validationError = userService.updateUser(user);
        if (validationError.length() == 0 && !isLoggedUserInvalid) successMsg = environment.getProperty("user.update.success.msg");
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("validationError", validationError);
        serverResponse.put("invalidUserError", invalidUserError);
        serverResponse.put("user", userService.get(user.getId()));
        return serverResponse;

   }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    Map deleteUser(@RequestBody String id,HttpSession httpSession) throws ParseException {
        String successMsg = "";
        String validationError = "";
        String invalidUserError="";
        HashMap serverResponse = new HashMap();
        boolean isLoggedUserInvalid=checkLoggedInUserExistence(httpSession);
        if(isLoggedUserInvalid) invalidUserError= environment.getProperty("user.invalid.error.msg");
        if(!isLoggedUserInvalid) validationError = userService.delete(Long.parseLong(id));
        if (validationError.length() == 0 && !isLoggedUserInvalid) successMsg = environment.getProperty("user.delete.success.msg");
        serverResponse.put("successMsg", successMsg);
        serverResponse.put("validationError", validationError);
        serverResponse.put("invalidUserError", invalidUserError);
        return serverResponse;
    }


    private boolean checkLoggedInUserExistence(HttpSession httpSession){
        boolean isLoggedUserExists =false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) auth.getPrincipal();
        User user=userService.findByUserName(loggedUser.getEmail());
        if(user ==null){
            isLoggedUserExists=true;
            httpSession.invalidate();
        }
        return isLoggedUserExists;
    }

}
