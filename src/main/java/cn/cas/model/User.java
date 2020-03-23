package cn.cas.model;

import lombok.Data;

/**
 * @author lawsssscat
 */
@Data
public class User {
    private String username;
    private String password;
    private String salt ;
    private Integer expired;
}
