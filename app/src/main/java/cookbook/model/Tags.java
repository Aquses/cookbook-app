package cookbook.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tags {
    private int tag_id;
    private String tag_name;

    public Tags(ResultSet rt) {
        try {
            setId(rt.getInt(1));
            setName(rt.getString(2));
            
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
    }

    public int getId() {
        return tag_id;
    }
    
    public void setId(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return tag_name;
    }

    public void setName(String tag_name) {
        this.tag_name = tag_name;
    }
}
