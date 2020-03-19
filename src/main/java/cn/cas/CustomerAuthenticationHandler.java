package cn.cas;

import cn.cas.utils.UserUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.StringUtils;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lawsssscat
 */
public class CustomerAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    public CustomerAuthenticationHandler(
            String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(
            UsernamePasswordCredential credential, String originalPassword)
            throws GeneralSecurityException, PreventedException {
        String username = credential.getUsername();
        String password = credential.getPassword();

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("enter your username");
        } else if (StringUtils.isEmpty(password)) {
            throw new AccountException("enter your PIN");
        }

        System.out.println("username:" + username);
        System.out.println("password:" + password);

        User user = UserUtils.findUser(username);

        System.out.println("user:" + user);

        if (user == null) {
            throw new AccountException("Sorry, username not found !");
        }

        System.out.println("database username:" + user.getUsername());
        System.out.println("database password:" + user.getPassword());

        if (!password.equals(user.getPassword())) {
            throw new FailedLoginException("Sorry, password not correct !");
        }

        // 可自定义返回给客户端多个属性信息
        HashMap<String, Object> info = new HashMap<>();
        info.put("expired", user.getExpired());

        // 不能为null，否则提交信息无法认证成功!
        List<MessageDescriptor> warning = new ArrayList<>();

        return createHandlerResult(credential, this.principalFactory.createPrincipal(username, info), warning);
    }
}
