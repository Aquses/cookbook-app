package cookbook.model;

public class Message {
    private int message_id; 
    private String sender_id;
    private String receiver_id;
    private String content;
    private Recipe recipe_id;
    private int date_created; 

    public Message(String sender_id, String receiver_id, String content,int message_id,Recipe recipe_id, int date_created ) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
        this.message_id= message_id; 
        this.recipe_id = recipe_id;
        this.date_created= date_created; 
    }

    public String getSender() {
        return sender_id;
    }

    public int getMessage_id() {
        return message_id;
    }

   

    public String getReceiver() {
        return receiver_id;
    }

    public String getContent() {
        return content;
    }

    public Recipe getRecipe_id() {
        return recipe_id;
    }

    public int getDate_created() {
        return date_created;
    }


    public void setRecipe_id(Recipe recipe_id) {
        this.recipe_id = recipe_id;
    }


    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public void setSender(String sender) {
        this.sender_id = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver_id = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    public void setDate_created(int date_created) {
        this.date_created = date_created;
    }

}
