package api.service;

import api.domain.Assets;

public interface BlockchainService {

	Assets queryBlockchainAssets(String id);

	String query(String uuid);

	String create(String uuid, String data);

	String createBlockchainAssets(String string, Assets tmp);

}
