package connectionprovider;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProvider {
	public static Connection getConnection() throws URISyntaxException,
			SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String dbUrl = "jdbc:postgresql://"
				+ dbUri.getHost()
				+ ':'
				+ dbUri.getPort()
				+ dbUri.getPath()
				+ "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		System.out.println(dbUrl);
		return DriverManager.getConnection(dbUrl, username, password);
	}

	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO GOODS(THING) VALUES('My cool Item');");
			ResultSet rs = stmt.executeQuery("SELECT Thing FROM Goods");
			while (rs.next()) {
				System.out.println("Thing: " + rs.getString("thing"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
