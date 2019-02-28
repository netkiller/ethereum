package api.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import api.domain.Assets;
import api.domain.Message;
import api.pojo.Enum.Examine;
import api.repository.AssetsRepository;
import api.service.BlockchainService;
import api.service.MessageService;

@Component
public class BlockchainTask {

	private static final Logger logger = LoggerFactory.getLogger(BlockchainTask.class);

	@Autowired
	private AssetsRepository assetsRepository;

	@Autowired
	private BlockchainService blockchainService;

	@Autowired
	private MessageService messageService;

	public BlockchainTask() {
		// TODO Auto-generated constructor stub
	}

	private Date getYesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	private void uploadToBlockchain() {
		List<Assets> lists = assetsRepository.findAllByUpdateDateBeforeAndStatus(this.getYesterday(), Examine.Pending);
		for (Assets assets : lists) {
			String transactionId = blockchainService.createBlockchainAssets(assets.getUuid(), assets);
			if (transactionId == null || transactionId.isEmpty()) {
				continue;
			}
			assets.setTransactionId(transactionId);
			messageService.send(new Message(assets.getMemberId(), "资产审核", String.format("恭喜您！%s 已经上链，HASH值是：%s。", assets.getName(), transactionId)));

			assets.setStatus(Examine.Approved);
			Assets tmp = assetsRepository.save(assets);
			logger.info("上链操作 {}", tmp.toString());
		}
	}

	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void echoCurrentTime() {
		this.uploadToBlockchain();
	}

}
