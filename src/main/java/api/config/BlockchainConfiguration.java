package api.config;

import org.springframework.beans.factory.annotation.Value;

public class BlockchainConfiguration {

	@Value("${hyperledger.fabric.config}")
	public String config;

}
