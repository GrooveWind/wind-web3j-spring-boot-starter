package top.wind.groove.web3j.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: Web3jProperties
 * @author: weilifeng
 * @create: 2021-05-12 19:12
 * @version:1.0
 **/
@ConfigurationProperties(prefix = "wind-web3j")
@Data
public class Web3jProperties {

    private Web3jMessage contract = new Web3jMessage();

    @Data
    public static class Web3jMessage{
        /**
         * Ethereum address
         */
        private String httpService;

        /**
         * Ethereum wallet file path
         */
        private String credentialsPath;

        /**
         * Ethereum wallet password
         */
        private String credentialsPassword;

    }

}
