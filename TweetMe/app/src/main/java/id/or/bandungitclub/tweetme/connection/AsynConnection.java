package id.or.bandungitclub.tweetme.connection;

import java.util.HashMap;
import java.util.Iterator;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsynConnection extends AsyncHttpResponseHandler {
	
	private ConnectionInterface callback;
	private String urlToConnect;
	private int type;
	private final int timeout = 60000;	
	
	public enum RequestType{
		GET, POST, PUT, DELETE
	};
	
	private HashMap<String, String> hParams;
	private HashMap<String, String> hHeader;
	private RequestParams rParams;
	private RequestType rType;	
	
	public AsynConnection(ConnectionInterface connectionInterface, String url, int type) {
		// TODO Auto-generated constructor stub
		this.callback = connectionInterface;
		this.urlToConnect = url;
		this.type = type;
	}
	
	public void asyncConnectRequest(HashMap<String, String> headers, HashMap<String, String> params, RequestType request){
		
		this.rType = request;
		this.hParams = params;
		this.hHeader = headers;
		this.rParams = new RequestParams(hParams);
		
		AsyncHttpClient client = new AsyncHttpClient();		
		if(hHeader != null){
			Iterator<String> keySetIterator = hHeader.keySet().iterator();		
			String headerName = "";
			String headerValue = "";
			
			while(keySetIterator.hasNext()){
				headerName = keySetIterator.next();
				headerValue = hHeader.get(headerName);
				client.addHeader(headerName, headerValue);			
				Log.i("Header Name", headerName);
				Log.i("Header Value", headerValue);			
			}
		}
		
		client.setTimeout(timeout);
		if(request == RequestType.GET){			
			client.get(urlToConnect, this);	
		} else if(request == RequestType.POST){
			if(hParams != null){
				client.post(urlToConnect, rParams, this);
			} else {
				client.post(urlToConnect, this);
			}			
		}
	}
		
	@Override
	public void onSuccess(int statusCode, String content) {
		// TODO Auto-generated method stub
		super.onSuccess(statusCode, content);		
		callback.callBackOnSuccess(content, statusCode, type);		
	}
	
	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		// TODO Auto-generated method stub
		super.onFailure(statusCode, error, content);
		callback.callBackOnFailed(content, statusCode, type);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
	}
	
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
	
	}
		
}
