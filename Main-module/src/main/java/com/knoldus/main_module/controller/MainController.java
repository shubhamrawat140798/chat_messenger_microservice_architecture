package com.knoldus.main_module.controller;
import com.knoldus.main_module.model.ErrorMessage;
import com.knoldus.main_module.model.Message;
import com.knoldus.main_module.model.User;
import com.knoldus.main_module.model.UserInput;
import com.knoldus.main_module.service.MainService;
import com.knoldus.main_module.validation.MobileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Scope("session")
@Controller
public class MainController {
    @Autowired
    private User user;
    @Autowired
    private User receiveUser;
    @Autowired
    private MobileValidation mobileValidation;
    @Autowired
    UserInput searchUser;
    @Autowired
    private ErrorMessage errorMessage;
    @Autowired
    private MainService service;
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user",user);
        return "LoginAndRegistration";
    }
    @PostMapping("/dashboard")
    public String PostSubmit(@ModelAttribute User receivedUser, Model model) {
        // check for login is name is empty that means user login

        if (receivedUser.getName() == null){
            System.out.println("h1");
            RestTemplate obj = new RestTemplate();
            User result= obj.postForObject("http://localhost:9092/user/login",receivedUser,User.class);
            System.out.println("h2");
            if (result == null){
                model.addAttribute("message","Incorrect Mobile number or password");
                return "LoginAndRegistration";
            }
            else {
                System.out.println(result.getName());
                user = result;
            }
        }
        else {
            //System.out.println(receivedUser.getName());
            RestTemplate obj = new RestTemplate();
            String result= obj.postForObject("http://localhost:9092/user/save",receivedUser,String.class);
            if(result != null){

                return "LoginAndRegistration";
            }
        }
        List<User> listUser = service.fetchUserList(user);
        List<Message> messagesList =new LinkedList<>();
        User receiverUser = new User();

        receiverUser.setName("");
        Message message= new Message();
        //model.addAttribute("");
        model.addAttribute("searchUser",searchUser);
        model.addAttribute("messagePayload",message);
        model.addAttribute("user",user);
        model.addAttribute("listUser",listUser);
        model.addAttribute("listMessages",  messagesList);
        model.addAttribute("receiverUser",receiverUser);
        model.addAttribute("searchFormError",errorMessage);
        return "chatWindow";
    }
    @PostMapping("/Chat")
    public String Chat(@ModelAttribute User receivedUser, Model model) {
        RestTemplate obj = new RestTemplate();
        User result= obj.postForObject("http://localhost:9092/user/login",receivedUser,User.class);
        receiveUser =receivedUser;
        List<User> listUser = service.fetchUserList(user);
        User receiverUser = service.fetchReceiver(receivedUser);
        Message message= new Message();
        message.setSenderId(user.getId());
        message.setReceiverId(receiverUser.getId());
        List<Message> messagesList = service.fetchMessageList(message);
        //System.out.println(message.getReceiverId());
        model.addAttribute("messagePayload",message);
        model.addAttribute("user",user);
        model.addAttribute("listMessages", messagesList);
        model.addAttribute("receiverUser",receiverUser);
        model.addAttribute("listUser",listUser);
        model.addAttribute("searchUser",searchUser);
        model.addAttribute("searchFormError",errorMessage);

//            System.out.println(model);
//            System.out.println(receivedUser.getName());
//            System.out.println(receivedUser.getId());
//            System.out.println(receivedUser.getMobile());
//            System.out.println(receivedUser.getPassword());
        return "chatWindow";
    }
    @PostMapping("/SendMessage")
    public String SendMessage(@ModelAttribute Message message, Model model) {
       // System.out.println("hi man");
        message.setSenderId(user.getId());
        message.setReceiverId(receiveUser.getId());
//        System.out.println(message.getMessageBody());
//        System.out.println(message.getSenderId());
//        System.out.println(message.getReceiverId());
        if (message.getMessageBody() != "") {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:9092/message/sendMessage", message, String.class);
        }
        List<User> listUser = service.fetchUserList(user);

        User receiverUser = new User();
        receiverUser.setId(Math.toIntExact(message.getReceiverId()));
        receiverUser = service.fetchReceiver(receiverUser);
//        Message message= new Message();
        message.setSenderId(user.getId());
        message.setReceiverId(receiverUser.getId());
        message.setMessageBody("");
        List<Message> messagesList = service.fetchMessageList(message);
        model.addAttribute("messagePayload",message);
        model.addAttribute("user",user);
        model.addAttribute("listMessages", messagesList);
        model.addAttribute("receiverUser",receiverUser);
        model.addAttribute("listUser",listUser);
        model.addAttribute("searchUser",searchUser);
        model.addAttribute("searchFormError",errorMessage);
        return "chatWindow";
    }
    @PostMapping("/SearchUser")
    public String SearchUser(@ModelAttribute UserInput searchUser, Model model){
         //System.out.println("No:" + searchUser.getMobile());
        User sendInput = new User();
       if (mobileValidation.validateMobileField(searchUser.getMobile())){
           //setting error message null
           errorMessage.setSearchFormErrorMessage("");
           RestTemplate obj = new RestTemplate();

           sendInput.setMobile(Long.parseLong(searchUser.getMobile()));
           System.out.println(sendInput.getMobile());
           sendInput= obj.postForObject("http://localhost:9092/user/searchUser",sendInput,User.class);
           System.out.println(sendInput.getName());

       }
       else {
           //setting error message
            errorMessage.setSearchFormErrorMessage("Invalid Number");
       }
        Message message= new Message();
        List<Message> messagesList =new LinkedList<>();
        List<User> listUser = new LinkedList<>();
        User receiverUser= new User();
        if (sendInput.getName() != null) {
            listUser.add(sendInput);
        }
        model.addAttribute("messagePayload",message);
        model.addAttribute("user",user);
        model.addAttribute("listMessages", messagesList);
        model.addAttribute("receiverUser",receiverUser);
        model.addAttribute("searchFormError",errorMessage);
        model.addAttribute("listUser",listUser);
        model.addAttribute("searchUser",searchUser);

        return "chatWindow";
    }
}
