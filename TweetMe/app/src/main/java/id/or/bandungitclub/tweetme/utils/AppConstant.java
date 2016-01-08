package id.or.bandungitclub.tweetme.utils;

public class AppConstant {
	
	private static String BASE_URL = "http://10.10.0.1/tweetme/index.php/api";
	
	// POST
	public static String URL_LOGIN = BASE_URL + "/user/login";	
	public static String URL_TWEET = BASE_URL + "/tweet/create";
	
	// GET
	public static String URL_TIMELINE = BASE_URL + "/tweet/timeline/id/";
	public static String URL_SEARCH_USER = BASE_URL + "/user/users/name/";
	
	
}
