package com.itheima.service.impl.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信验证码的模板号
	 */
	public static final String VALIDATE_CODE = "SMS_202566528";

	/**
	 * 发送体检预约成功通知的模板号
	 */
	public static final String ORDER_NOTICE = "SMS_159771588";

	public static void main(String[] args) {
		try {
			SMSUtils.sendShortMessage(VALIDATE_CODE,"your phone Number","9527");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信
	 * @param phoneNumbers
	 * @param param
	 * @throws ClientException
	 */
	public static void sendShortMessage(String templateCode,String phoneNumbers,String param) throws ClientException{
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		// 短信API产品名称（短信产品名固定，无需修改）
		final String product = "Dysmsapi";
		// 短信API产品域名（接口地址固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";
		// 替换成你的AK
		// 你的accessKeyId
<<<<<<< HEAD
		final String accessKeyId = "LTAI4G9jqTz9Uudevx5x78KU";
		// 你的accessKeySecret
		final String accessKeySecret = "kpMSEoLYpD2z0OCmSTr9nC2wzU8lAt";
=======
		final String accessKeyId = "your accessKeyId";
		// 你的accessKeySecret
		final String accessKeySecret = "your accessKeySecret";
>>>>>>> d0f4ebeac8d1f377126272b3a8b66256ec2a4ee3
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phoneNumbers);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("传智健康");
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"code\":\""+param+"\"}");
		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		// request.setOutId("yourOutId");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
			// 请求成功
			System.out.println("请求成功");
		}
	}
}
