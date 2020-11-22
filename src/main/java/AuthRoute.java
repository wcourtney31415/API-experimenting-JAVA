import spark.Route;

class AuthRoute {
	String username, password;
	Route pass, fail;
	public AuthRoute(String username, String password, Route pass, Route fail) {
		this.username = username;
		this.password = password;
		this.pass = pass;
		this.fail = fail;
	}
	
}