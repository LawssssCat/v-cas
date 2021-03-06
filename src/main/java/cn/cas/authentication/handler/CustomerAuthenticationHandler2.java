package cn.cas.authentication.handler;

import cn.cas.model.User;
import cn.cas.utils.UserUtils;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.util.StringUtils;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

/**
 * @author lawsssscat
 */
@Log4j2
public class CustomerAuthenticationHandler2 extends AbstractPreAndPostProcessingAuthenticationHandler {

    public CustomerAuthenticationHandler2(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    public boolean supports(Credential credential) {
        // 判断传递过来的Credential是否是自己能处理的类型
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(
            Credential credential)
            throws GeneralSecurityException, PreventedException {
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

        String username = usernamePasswordCredential.getUsername();
        String password = usernamePasswordCredential.getPassword();

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("enter your username");
        } else if (StringUtils.isEmpty(password)) {
            throw new AccountException("enter your PIN");
        }

        log.info("log4j2 is running @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("username:" + username);
        System.out.println("password:" + password);

        User user = UserUtils.findUser(username);

        System.out.println("user:" + user);

        if (user == null) {
            throw new AccountException("Sorry, username not found !");
        }

        String ticket = MD5SaltEncryt(password, user.getSalt());

        if (StringUtils.isEmpty(ticket) || !ticket.equals(user.getPassword())) {
            throw new FailedLoginException("Sorry, password not correct!");
        }

        @NonNull Principal principal = this.principalFactory.createPrincipal(username);

        return createHandlerResult(usernamePasswordCredential, principal);
    }

    private String MD5SaltEncryt(String source, String salt) {
        String algorithmName = Md5Hash.ALGORITHM_NAME; // 加密算法名
        int hashIterations = 3; // 加密次数
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);
        // show
        System.out.println("加密算法:" + algorithmName);
        System.out.println("加密次数" + hashIterations);
        System.out.println("加密盐值:" + salt);
        System.out.println("盐值长度:" + salt.length());
        System.out.println("加密结果:" + hash.toString());
        System.out.println("密文长度:" + hash.toString().length());
        System.out.println("原密码:" + source);
        return hash.toString();
    }

}
