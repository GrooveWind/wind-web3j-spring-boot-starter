package top.wind.groove.web3j.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.DependsOn;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import top.wind.groove.web3j.service.Web3jCommonHandler;

/**
 * @program: wind-web3j-spring-boot-starter->Web3jConfiguration
 * @description: Web3jConfiguration
 * @author: weilifeng
 * @create: 2021-05-12 19:25
 * @version:1.0
 **/
@EnableConfigurationProperties(value = Web3jProperties.class)
@Configuration(proxyBeanMethods = false)
public class Web3jConfiguration {

    private final Web3jProperties web3jProperties;

    private Web3j web3j;
    private Credentials credentials;


    public Web3jConfiguration(Web3jProperties web3jProperties) {
        this.web3jProperties = web3jProperties;
    }

    @Bean
    @ConditionalOnMissingBean(Web3j.class)
    public Web3j web3j() {
        String httpService = web3jProperties.getContract().getHttpService();
        if (httpService == null || "".equals(httpService.trim())) {
            return null;
        }
        web3j = Web3j.build(new HttpService(httpService));
        return web3j;
    }

    @Bean
    @ConditionalOnMissingBean(Credentials.class)
    public Credentials credentials() {

        try {
            String credentialsPath = web3jProperties.getContract().getCredentialsPath();
            String credentialsPassword = web3jProperties.getContract().getCredentialsPassword();
            String credentialsPrivateKey = web3jProperties.getContract().getCredentialsPrivateKey();

            if (StringUtils.isNotBlank(credentialsPath) && StringUtils.isNotBlank(credentialsPassword)) {
                credentials = WalletUtils.loadCredentials(credentialsPassword, credentialsPath);
            } else if (StringUtils.isNotBlank(credentialsPrivateKey)) {
                credentials = Credentials.create(credentialsPrivateKey);
            }

            return credentials;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    @DependsOn({"web3j", "credentials"})
    public Web3jCommonHandler web3jCommonHandler() {
        return new Web3jCommonHandler(web3j, credentials);
    }

}
