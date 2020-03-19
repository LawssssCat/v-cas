package cn.cas;

import lombok.Data;

/**
 * @author lawsssscat
 */
@Data
public class User {
    private String username;
    private String password;
    private Integer expired;
}
