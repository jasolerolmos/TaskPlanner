package connection;

public class DataConnection {
	private String host;
	private String user;
	private String password;
	private String database;
	private int port;
	private boolean userSSL;
	private String driver = "mysql";
	
	public DataConnection() {
		host = "localhost";
		user = "root";
		password = "";
		database = "";
		port = 3306;
		userSSL = false;
	}
	public DataConnection(String host, String user, String password, String database) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = 3306;
		this.userSSL = false;
	}
	public DataConnection(String host, String user, String password, String database, int port) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = 3306;
		this.userSSL = false;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isUserSSL() {
		return userSSL;
	}
	public void setUserSSL(boolean userSSL) {
		this.userSSL = userSSL;
	}
	public String getStringConnection() {
		return "jdbc:"+driver+"://" + host + ":" + port + "/" + database + "?useSSL="+userSSL;
	}
}
