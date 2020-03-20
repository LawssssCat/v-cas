package cn.cas.utils;

import cn.cas.model.User;
import org.junit.Test;

public class TestUserUtils {

    @Test
    public void test_find() {
        User user = UserUtils.findUser("admin");
        System.out.println(user);
    }
}
