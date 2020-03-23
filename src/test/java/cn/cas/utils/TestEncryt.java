package cn.cas.utils;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class TestEncryt {

    @Test
    public void md5_salt_Test() {
        String algorithmName = Md5Hash.ALGORITHM_NAME; // 加密算法名
        Object source = "admin"; // 密码明文
        String salt = "d5a76533-c05d-49e5-8653-6bc1397dc6ce" ;
        int hashIterations = 3; // 加密次数
        String d = "4e06b3cee0dc656695020bf34e07220f";
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);
        // show
        System.out.println("加密算法:" + algorithmName);
        System.out.println("加密次数" + hashIterations);
        System.out.println("加密盐值:" + salt);
        System.out.println("盐值长度:" + salt.length());
        System.out.println("加密结果:" + hash.toString());
        System.out.println("密文长度:" + hash.toString().length());
        System.out.println("原密码:" + source);
    }
}
