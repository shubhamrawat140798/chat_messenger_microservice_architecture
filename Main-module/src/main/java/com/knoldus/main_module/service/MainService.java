package com.knoldus.main_module.service;

import com.knoldus.main_module.model.Message;
import com.knoldus.main_module.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class MainService {
    public User fetchReceiver(User user){
        RestTemplate obj = new RestTemplate();
         return obj.postForObject("http://localhost:9092/user/fetchUserRelation",user,User.class);
    }
    public List<User> fetchUserList(User user){
        RestTemplate obj = new RestTemplate();
        List<Integer> result= obj.postForObject("http://localhost:9092/message/getUserIdOfPreviousMessage",user,List.class);
        System.out.println(result);
        List<User> listUser = new LinkedList<>();
        for (int index: result ) {
            RestTemplate object = new RestTemplate();
            System.out.println(index);
            User getUser = new User();
            getUser.setId(index);
            getUser = object.postForObject("http://localhost:9092/user/fetchUserRelation",getUser,User.class);
            listUser.add(getUser);
//            System.out.println(getUser.getId());
//            System.out.println(getUser.getMobile());
//            System.out.println(getUser.getPassword());
//            System.out.println(getUser.getName());
        }
        return listUser;
    }
    public List<Message> fetchMessageList(Message message){
        RestTemplate obj = new RestTemplate();
        return obj.postForObject("http://localhost:9092/message/getMessagesOneToOne",message,List.class);

    }
}
