package top.wind.groove.web3j.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

/**
 * @program: wind-web3j-spring-boot-starter->Web3jCommonHandler
 * @description: common handler
 * @author: weilifeng
 * @create: 2021-05-12 19:57
 * @version:1.0
 **/
@AllArgsConstructor
@Getter
public class Web3jCommonHandler {

    private Web3j web3j;
    private Credentials credentials;


    public TransactionReceipt sendFunds(String toAddress, BigDecimal amount, Convert.Unit unit) throws Exception {
        return Transfer
                .sendFunds(web3j, credentials, toAddress, amount, unit)
                .send();
    }

}
