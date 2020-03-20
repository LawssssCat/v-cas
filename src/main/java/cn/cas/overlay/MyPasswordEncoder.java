package cn.cas.overlay;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * @author lawsssscat
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        String encode = charSequence.toString();
        if (StringUtils.isEmpty(encode)) {
            return null;
        }
        // charSequence为输入的用户密码
        return encode;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        // 当encode方法返回不为null时，matches方法才会调用，charSequence为encode返回的字符串
        // str字符串为数据库中字段返回的值
        String encode = charSequence.toString();
        return "aaaaaa".equals(encode);
    }
}
