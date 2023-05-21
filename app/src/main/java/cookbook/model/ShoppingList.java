package cookbook.model;

import java.sql.Date;


/*
 * ShppingList Class. 
 */
public class ShoppingList {
    private int list_id; 
    private User user_id; 
    private String list_name;
    private Date created_date; 


    /**
     * gets list_id.
     
     * @return list_id.
     */
    public int getList_id() {
        return list_id;
    }

    /**
     * sets list_id.
     
     * @param list_id list_id.
     */
    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
    
    /**
     * gets user_id.
     
     * @return user_id.
     */
    public User getUser_id() {
        return user_id;
    }

    /**
     * sets user_id.
     
     * @param user_id user_id.
     */
    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
    
    /**
     * gets list_name.
    
     * @return list_name.
     */
    public String getList_name() {
        return list_name;
    }

    /**
     * sets list_name.
     
     * @param list_name list_name.
     */
    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    /**
     * gets created-date.
     
     * @return created_date.
     */
    public Date getCreated_date() {
        return created_date;
    }

    /**
     * sets created-date.
     
     * @param created_date created_date.
     */
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    
}
