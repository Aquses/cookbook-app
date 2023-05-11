package cookbook.model;

import java.sql.Date;
import java.sql.Timestamp;



public class Message {
    private int message_id; 
    private int sender_id;
    private int receiver_id;
    private String content;
    private int recipe_id;
    // private Date date_created; 
    private Timestamp date_created; 

    public Message(int message_id, int sender_id, int receiver_id, int recipe_id, String content, Timestamp date_created) {
        this.message_id = message_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.recipe_id = recipe_id;
        this.content = content;
        this.date_created = date_created; 
    }

    public int getSenderId() {
        return sender_id;
    }

    public int getMessageId() {
        return message_id;
    }

    public int getReceiverId() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    // public Date getDateCreated() {
    //     return date_created;
    // }

    public Timestamp getDateCreated() {
        return date_created;
    }

    public void setRecipeId(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setMessageId(int message_id) {
        this.message_id = message_id;
    }

    public void setSenderId(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiverId(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // public void setDateCreated(Date date_created) {
    //     this.date_created = date_created;
    // }

    public void setDateCreated(Timestamp date_created) {
        this.date_created = date_created;
    }
}
