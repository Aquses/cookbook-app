package cookbook.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tags {
    private int tag_id;
    private String Name;

    public Tags(ResultSet rt) {
        try {
            setId(rt.getInt(1));
            setName(rt.getString(2));
            
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
    }
    

    public Tags(String Name) {
        this.Name = Name;
    }

    public int getId() {
        return tag_id;
    }
    
    public void setId(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
