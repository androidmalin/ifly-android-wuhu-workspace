package com.runcross.maback.mini.action;

import java.util.ArrayList;
import java.util.Calendar;

import org.w3c.dom.Document;

import com.runcross.maback.mini.data.Analysis;
import com.runcross.maback.mini.data.ErrorData;
import com.runcross.maback.mini.data.HTTPLink;
import com.runcross.maback.mini.data.Info;
import com.runcross.maback.mini.run.Go;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
public class Login {

	
	public static final ActionRegistry.Action Name = ActionRegistry.Action.LOGIN;
	// URLs
	private static final String URL_CHECK_INSPECTION = HTTPLink.host+"connect/app/check_inspection?cyt=1";
	private static final String URL_LOGIN = HTTPLink.host+"connect/app/login?cyt=1";
	// error type
	public static final String ERR_CHECK_INSPECTION = "Login/check_inspection";
	public static final String ERR_LOGIN = "Login/login";
	
	private static byte[] result;
	
	public static boolean run() throws Exception {
		try {
			return run(true);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static boolean run(boolean jump) throws Exception {
		Document doc;
		if (!jump) {
			try {
				result = Go.network.ConnectToServer(URL_CHECK_INSPECTION, new ArrayList<NameValuePair>(), true);
			} catch (Exception ex) {
				ErrorData.currentDataType = ErrorData.DataType.text;
				ErrorData.currentErrorType = ErrorData.ErrorType.ConnectionError;
				ErrorData.text = ERR_CHECK_INSPECTION;
				throw ex;
			}
		}
		ArrayList<NameValuePair> al = new ArrayList<NameValuePair>();
		al.add(new BasicNameValuePair("login_id",Info.LoginId));
		al.add(new BasicNameValuePair("password",Info.LoginPw));
//		System.out.println(Info.LoginId+" "+Info.LoginPw);
		try {
//			System.out.println("url "+URL_LOGIN);
			result = Go.network.ConnectToServer(URL_LOGIN, al,true);
			
		} catch (Exception ex) {
			ErrorData.currentDataType = ErrorData.DataType.text;
			ErrorData.currentErrorType = ErrorData.ErrorType.ConnectionError;
			ErrorData.text = ex.getMessage();
			ex.printStackTrace();
			throw ex;
		}
		try {
			doc = Analysis.ParseXMLBytes(result);
		} catch (Exception ex) {
			ErrorData.currentDataType = ErrorData.DataType.text;
			ErrorData.currentErrorType = ErrorData.ErrorType.LoginDataError;
			ErrorData.text = ERR_LOGIN;
			throw ex;
		}
		try {
			return parse(doc);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private static boolean parse(Document doc) throws Exception {
		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			if (!xpath.evaluate("/response/header/error/code", doc).equals("0")) {
				ErrorData.currentErrorType = ErrorData.ErrorType.LoginResponse;
				ErrorData.currentDataType = ErrorData.DataType.text;
				ErrorData.text = xpath.evaluate("/response/header/error/message", doc);
//				System.out.println("error code 0");
//				System.out.println(new String(result));
				return false;
			}
			
			if (GuildDefeat.judge(doc)) {
				return false;
			}
			
			if (!xpath.evaluate("//fairy_appearance", doc).equals("0")) {
				Go.info.events.push(Info.EventType.fairyAppear);
			}
//			Info.name = xpath.evaluate("//your_data/name", doc);
//			Info.level = xpath.evaluate("//your_data/town_level", doc);
//			Info.maxap = Integer.valueOf(xpath.evaluate("//ap/max", doc));
//			Info.ap = Integer.valueOf(xpath.evaluate("//ap/current", doc));
//			Info.maxbc = Integer.valueOf(xpath.evaluate("//bc/max", doc));
//			Info.bc = Integer.valueOf(xpath.evaluate("//bc/current", doc));
			Go.info.userId = xpath.evaluate("//login/user_id", doc);
			
			 long time = Calendar.getInstance().getTimeInMillis();
			ParseUserDataInfo.parse(doc);
//			ParseCardList.parse(doc);
//			PULLCardList.getCards(result);
			System.out.println(Calendar.getInstance().getTimeInMillis() - time);
			
			Go.info.SetTimeoutByAction(Name);
			
			Go.info.cardMax = Integer.parseInt(xpath.evaluate("//your_data/max_card_num",doc));
			
		} catch (Exception ex) {
			if (ErrorData.currentErrorType != ErrorData.ErrorType.none) throw ex;
			ErrorData.currentDataType = ErrorData.DataType.bytes;
			ErrorData.currentErrorType = ErrorData.ErrorType.LoginDataParseError;
			ErrorData.bytes = result;
			throw ex;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	

}
