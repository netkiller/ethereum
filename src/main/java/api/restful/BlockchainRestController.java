package api.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.domain.Assets;
import api.pojo.RestfulResponse;
import api.service.BlockchainService;

@RestController
@RequestMapping("/blockchain")
public class BlockchainRestController {

	@Autowired
	private BlockchainService blockchainService;

	public BlockchainRestController() throws Exception {

	}

	@GetMapping("/set/{key}/{value}")
	public RestfulResponse json(@PathVariable String key, @PathVariable String value) {

		String transactionID = blockchainService.create(key, value);
		return new RestfulResponse(true, 0, null, transactionID);
	}

	@GetMapping("/get/{key}")
	public RestfulResponse json(@PathVariable String key) {

		String string = blockchainService.query(key);
		return new RestfulResponse(true, 0, null, string);
	}

	
	@Cacheable(value="users", key="#uuid")
	@GetMapping("/assets/{uuid}")
	public RestfulResponse assets(@PathVariable String uuid) {
		Assets assets = blockchainService.queryBlockchainAssets(uuid);

		if (assets == null) {
			return new RestfulResponse(false, 0, "blockchain", assets);
		} else {
			return new RestfulResponse(true, 0, "blockchain成功", assets);
		}
	}
	
//	@Cacheable(value="users", key="#uuid")
	@GetMapping("/assets/{uuid}/{nfc}")
	public RestfulResponse assets(@PathVariable String uuid, @PathVariable String nfc) {
		Assets assets = blockchainService.queryBlockchainAssets(uuid);

		if (assets == null) {
			return new RestfulResponse(false, 0, "blockchain", assets);
		} else {
			return new RestfulResponse(true, 0, "blockchain成功", assets);
		}
	}
	@GetMapping("/assets/{qrcode}")
	public RestfulResponse assetsByQrcode(@PathVariable String qrcode) {
		Assets assets = blockchainService.queryBlockchainAssets(qrcode);

		if (assets == null) {
			return new RestfulResponse(false, 0, "blockchain", assets);
		} else {
			return new RestfulResponse(true, 0, "blockchain成功", assets);
		}
	}
}
