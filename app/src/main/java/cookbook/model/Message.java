package cookbook.model;

public class Message {
    private String sender_id;
    private String receiver_id;
    private String content;

    public Message(String sender_id, String receiver_id, String content) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
    }

    public String getSender() {
        return sender_id;
    }

    public String getReceiver() {
        return receiver_id;
    }

    public String getContent() {
        return content;
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
}
