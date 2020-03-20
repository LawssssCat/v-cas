package cn.cas.authentication;

import cn.cas.authentication.handler.CustomerAuthenticationHandler;
import cn.cas.authentication.handler.CustomerAuthenticationHandler2;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lawsssscat
 */
@Configuration("CustomAuthenticationConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomAuthenticationConfiguration implements AuthenticationEventExecutionPlanConfigurer {

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    // 验证器交给Spring管理
    @Bean
    public AuthenticationHandler customerAuthenticationHandler() {
        System.out.println("initializating AuthenticationHandler@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        String name = CustomerAuthenticationHandler2.class.getName();
        ServicesManager servicesManager = this.servicesManager;
        PrincipalFactory principalFactory = new DefaultPrincipalFactory();
        // 定义为优先优先使用
        Integer order = 1;
        return new CustomerAuthenticationHandler2(name,servicesManager, principalFactory, order) ;
    }

    // 注册自定义验证器
    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(customerAuthenticationHandler());
    }
}
