package com.knoldus.main_module.model;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Message {
    private Long id;
    private Long senderId;
    private String messageBody;
    private Long receiverId;
    private boolean deleteSenderMessage;
    private boolean deleteReceiverMessage;
    private Date createdDate;
    public Long getId() {
        return id;
    }
    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    public String getMessageBody() {
        return messageBody;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
    public Long getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
    public boolean isDeleteSenderMessage() {
        return deleteSenderMessage;
    }
    public void setDeleteSenderMessage(boolean deleteSenderMessage) {
        this.deleteSenderMessage = deleteSenderMessage;
    }
    public boolean isDeleteReceiverMessage() {
        return deleteReceiverMessage;
    }
    public void setDeleteReceiverMessage(boolean deleteReceiverMessage) {
        this.deleteReceiverMessage = deleteReceiverMessage;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
