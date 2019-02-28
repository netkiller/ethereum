package api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.domain.Message;
import api.repository.MessageRepository;
import api.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	@Value("${sms.apikey}")
	private String apikey;

	@Autowired
	private MessageRepository messageRepository;

	public MessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public boolean send(Message message) {

		try {
			message.setCreateDate(new Date());
			message.setUpdateDate(null);
			messageRepository.save(message);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public void sms(String mobile, String message) {

		String url = "http://api01.monyun.cn:7901/sms/v2/std/single_send";

		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		String timestamp = sdf.format(Calendar.getInstance().getTime());

		CloseableHttpClient httpclient = HttpClients.createDefault();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apikey", apikey));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("content", message));

		params.add(new BasicNameValuePair("timestamp", timestamp));
		params.add(new BasicNameValuePair("svrtype", ""));
		params.add(new BasicNameValuePair("exno", ""));
		params.add(new BasicNameValuePair("custid", ""));
		params.add(new BasicNameValuePair("exdata", ""));

		try {

			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "GBK");
			System.out.println(urlEncodedFormEntity.toString());

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncodedFormEntity);

			CloseableHttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			String responseBody = EntityUtils.toString(entity, "UTF-8");

			// System.out.println(params.toString());
			// System.out.println(response.getStatusLine());
			// System.out.println(responseBody.toString());
			logger.info("SMS: {} - {}", response.getStatusLine(), responseBody.toString());
			response.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mail(String to, String title, String content) {

	}
}
