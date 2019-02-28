package api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import api.blockchain.FabricHelper;
import api.domain.Assets;
import api.service.BlockchainService;

@Service
public class BlockchainServiceImpl implements BlockchainService {

	private static final Logger logger = LoggerFactory.getLogger(BlockchainService.class);

	private FabricHelper helper;

	@Value("${hyperledger.fabric.config}")
	private String config;

	public BlockchainServiceImpl() throws Exception {

	}

	private void getInstance() {
		// String config = blockchainConfiguration.config;
		String cfg = this.getClass().getClassLoader().getResource(config).getPath();
		logger.info("Loading hyperledger fabric {}", cfg);

		helper = FabricHelper.getInstance();
		helper.setConfigCtx(cfg);
	}

	public String create(String uuid, String data) {
		this.getInstance();
		return helper.invokeBlockchain("create", new String[] { uuid, data });
	}

	public String query(String uuid) {
		this.getInstance();
		String result = helper.queryBlockchain("query", new String[] { uuid });
		System.out.println(">>> result: " + result);
		return result;
	}

	public String update(String uuid, String data) {
		this.getInstance();
		return helper.invokeBlockchain("update", new String[] { uuid, data });
	}

	public String createBlockchainAssets(String uuid, Assets assets) {

		Gson gson = new Gson();
		String json = gson.toJson(assets);
		return this.create(uuid, json);
	}

	public Assets queryBlockchainAssets(String uuid) {
		Assets assets = new Assets();
		Gson gson = new Gson();
		String blockdata = this.query(uuid);
		if (blockdata == null) {
			return null;
		}
		assets = gson.fromJson(blockdata, Assets.class);
		return assets;
	}

	public String updateBlockchainAssets(String uuid, Assets assets) {

		Gson gson = new Gson();
		String json = gson.toJson(assets);
		return this.update(uuid, json);
	}

}
