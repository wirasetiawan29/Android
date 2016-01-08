package id.or.bandungitclub.tweetme.connection;

public interface ConnectionInterface {
	public void callBackOnSuccess(Object value, int responseCode, int type);
	public void callBackOnFailed(Object value, int responseCode, int type);	
}
