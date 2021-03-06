package net;

import java.util.List;

import net.CryptoCn.E_CODE;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import start.Info;

public class Connect {

	//游戏版本
	private static final String UserAgent = "Million/"+Info.userAgent+" (GT-I9100; GT-I9100; 2.3.4) samsung/GT-I9100/GT-I9100:2.3.4/GRJ22/eng.build.20120314.185218:eng/release-keys";
	
	private static final String Auth = "eWa25vrE";
	private static final String Key = "2DbcAh3G";

	private static DefaultHttpClient client;
	private static CryptoCn dk;
	public Connect() {
		client = new DefaultHttpClient();
		HttpParams hp = client.getParams();
		hp.setParameter("http.socket.timeout", 0x7530);
		hp.setParameter("http.connection.timeout", 0x7530);
	}
	
	//加密参数
//	private static List<NameValuePair> requestProcess(List<NameValuePair> source) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//		ArrayList<NameValuePair> result = new ArrayList<NameValuePair>();
//		Iterator<NameValuePair> i  = source.iterator();
//		while(i.hasNext()) {
//			NameValuePair n = i.next();
//			result.add(new BasicNameValuePair(n.getName(),Crypto.Encrypt2Base64NoKey(n.getValue())));
//		}
//		return result;
//	}

	//访问请求
	public byte[] connectToServer(String url, List<NameValuePair> content) throws Exception {
//		List<NameValuePair> post = requestProcess(content);
		dk = new CryptoCn();
		List<NameValuePair> post = dk.addnew_crypt_K_param(content,url);
		HttpPost hp = new HttpPost(url);
		hp.setHeader("User-Agent", UserAgent);
		hp.setHeader("Accept-Encoding", "gzip, deflate");
		hp.setEntity(new UrlEncodedFormEntity(post,"UTF-8"));
		
		AuthScope as = new AuthScope(hp.getURI().getHost(),hp.getURI().getPort());
		CredentialsProvider cp = client.getCredentialsProvider();
		UsernamePasswordCredentials upc = new UsernamePasswordCredentials(Auth,Key);
		cp.setCredentials(as, upc);
		byte[] b = client.execute(hp,new HttpResponseHandler());
		
		/* end */
		if (b != null) {
			if (url.contains("gp_verify_receipt?")) {
				// need to be decoded
				return null;
			}
//			try {
//				return Crypto.DecryptNoKey(b);
//			} catch (Exception ex) {
//				throw ex;
//			}
//			if(url.indexOf("login?") != -1 || url.indexOf("regist?") != -1)
//			{
//				return dk.new_decrypt_cn(E_CODE.RSA, b);
//			}else{
//				return dk.new_decrypt_cn(E_CODE.AES, b);
//			}
			return CryptoCn.decode(b);
		} else{
			connectToServer(url,content);
		}
		return null;
	}
	
}
