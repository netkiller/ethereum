package api.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CountDownLatch;

/**
 * run this main method and check result
 */
public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String args[]) throws Exception {
		FabricHelper helper = FabricHelper.getInstance();
		String config = Main.class.getClassLoader().getResource("bcs-whbsxu-sdk-config.yaml").getPath();
		helper.setConfigCtx(config);
//		helper.setConfigCtx("fixture/config/bcs-whbsxu-sdk-config.yaml");
		LoopInvoke(1);
		// StartMultiTask(1,1);
	}

	public static void LoopInvoke(int loop) throws Exception {
		FabricHelper helper = FabricHelper.getInstance();
		for (int i = 0; i < loop; i++) {
			helper.invokeBlockchain("create", new String[] { "neo1", "chen" });
			helper.invokeBlockchain("create", new String[] { "jam2", "zheng" });
			// helper.invokeBlockchain("invoke", new String[]{"a","b","100"});
			String a = helper.queryBlockchain("query", new String[] { "neo1" });
			String b = helper.queryBlockchain("query", new String[] { "jam2" });
			logger.info("after invoke  a=" + a + ", invoke b=" + b);
		}
	}

	// StartMultiTask(1, 1);
	public static void StartMultiTask(int threadNumber, int loop) throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
		for (int i = 0; i < threadNumber; i++) {
			final int threadID = i;
			new Thread() {
				public void run() {
					try {
						LoopInvoke(loop);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("threadID:[%s] finished!!", threadID);
					countDownLatch.countDown();
				}
			}.start();
		}

		countDownLatch.await();
		logger.info("main thread finished!!");
	}
}
