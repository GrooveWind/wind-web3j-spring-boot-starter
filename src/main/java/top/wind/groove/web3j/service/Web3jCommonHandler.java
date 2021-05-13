package top.wind.groove.web3j.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;

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

    public <T extends Contract> T loadContract(Class<? extends Contract> clazz, String contractAddress) throws Exception {
        Constructor constructor = buildConstructor(clazz, String.class, Web3j.class, Credentials.class, ContractGasProvider.class);
        return (T) constructor.newInstance(contractAddress, web3j, credentials, new DefaultGasProvider());
    }

    public <T extends Contract> T loadContract(Class<? extends Contract> clazz, String contractAddress, BigInteger gasPrice, BigInteger gasLimit) throws Exception {
        Constructor constructor = buildConstructor(clazz, String.class, Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
        return (T) constructor.newInstance(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public TransactionReceipt sendFunds(String toAddress, BigDecimal amount, Convert.Unit unit) throws Exception {
        return Transfer
                .sendFunds(web3j, credentials, toAddress, amount, unit)
                .send();
    }

    private Constructor buildConstructor(Class<?> clazz, Class<?>... args) throws NoSuchMethodException {
        Constructor constructor = clazz.getDeclaredConstructor(args);
        constructor.setAccessible(true);
        return constructor;
    }

}
