package api.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.bitcoinj.wallet.UnreadableWalletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import api.domain.Token;
import api.domain.UserToken;
import api.domain.UserToken.UserTokenPrimaryKey;
import api.wallet.Ethereum;
import api.wallet.Ethereum.WalletResponse;
import api.pojo.TokenResponse;
import api.repository.TokenRepository;
import api.repository.UserTokenRepository;
import api.service.EthereumWallet;

@Service
public class EthereumWalletImpl implements EthereumWallet {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${ethereum.url.infura}")
	private String url;

	@Autowired
	private Web3j web3j;
	// private Ethereum ethereum;

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	UserTokenRepository userTokenRepository;

	private Ethereum ethereum;

	public EthereumWalletImpl() {
		// TODO Auto-generated constructor stub
		this.ethereum = new Ethereum(this.web3j);
	}

	public String getUrl() {
		return this.url;
	}

	public WalletResponse create() {
		try {
			return this.ethereum.createMnemonic();
		} catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String loginByMnemonic(String mnemonic) {
		// AES aes = new AES(key);
		try {
			WalletResponse wallet = this.ethereum.importFromMnemonic(mnemonic);
			return wallet.getMnemonic();
		} catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String loginByPrivateKey(String privateKey) {
		WalletResponse wallet = this.ethereum.importFromPrivateKey(privateKey);
		return wallet.getPrivateKey();
	}

	public TokenResponse getToken(String contractAddress) {
		Ethereum token = new Ethereum(web3j);
		token.setContractAddress(contractAddress);
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setName(token.getName());
		tokenResponse.setSymbol(token.getSymbol());
		tokenResponse.setDecimals(token.getDecimals());
		tokenResponse.setContractAddress(contractAddress);
		return tokenResponse;
	}

	public void addToken(String address, String contractAddress) {

		Token token = tokenRepository.findOneByContractAddress(contractAddress);
		if (token == null) {

			TokenResponse tokenResponse = this.getToken(contractAddress);

			token = new Token();
			token.setContractAddress(contractAddress);
			token.setName(tokenResponse.getName());
			token.setSymbol(tokenResponse.getSymbol());
			token.setDecimals(tokenResponse.getDecimals());
			tokenRepository.save(token);
			logger.info("Add Token: " + token.toString());
		}

		UserTokenPrimaryKey primaryKey = new UserTokenPrimaryKey(address, contractAddress);
		// logger.info(userTokenRepository.getByAddress(address).toString());
		if (userTokenRepository.findOneByPrimaryKey(primaryKey) == null) {

			UserToken userToken = new UserToken();

			userToken.setPrimaryKey(primaryKey);
			userToken.setName(token.getName());
			userToken.setSymbol(token.getSymbol());
			userToken.setDecimals(token.getDecimals());
			userToken.setStatus(true);
			userTokenRepository.save(userToken);

			logger.info("Add token to table: " + userToken.toString());

		}
	}

	public Map<String, String> getAllBalance(String address) throws IOException, InterruptedException, ExecutionException {
		Map<String, String> balances = new LinkedHashMap<String, String>();
		Ethereum ethereumToken = new Ethereum(web3j);

		balances.put("ETH", ethereumToken.toEth(ethereumToken.getBalance(address)));

		List<UserToken> userTokens = userTokenRepository.findAllByPrimaryKeyAddress(address);
		logger.info("User token: " + userTokens.toString());
		if (userTokens != null) {
			for (UserToken userToken : userTokens)
				try {

					ethereumToken.setContractAddress(userToken.getPrimaryKey().getContractAddress());
					BigInteger balance = ethereumToken.getTokenBalance(userToken.getPrimaryKey().getAddress());
					// logger.info(balance.toString());
					logger.info(userToken.getSymbol() + ": " + ethereumToken.toBigDecimal(balance, userToken.getDecimals()).toPlainString());
					balances.put(userToken.getSymbol(), ethereumToken.toBigDecimal(balance, userToken.getDecimals()).toPlainString());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return balances;
	}
}
