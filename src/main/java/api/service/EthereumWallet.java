package api.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import api.pojo.TokenResponse;
import api.wallet.Ethereum.WalletResponse;

public interface EthereumWallet {

	String getUrl();

	TokenResponse getToken(String contractAddress);

	public void addToken(String address, String contractAddress);

	public Map<String, String> getAllBalance(String address) throws IOException, InterruptedException, ExecutionException;

	public WalletResponse create();

	String loginByMnemonic(String mnemonic);

	String loginByPrivateKey(String string);

}
