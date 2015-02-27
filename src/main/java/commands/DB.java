package commands;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import twitter4j.auth.AccessToken;
import connectionprovider.ConnectionProvider;

public class DB {

	public AccessToken getOAuthToken(String user, String application) {
		AccessToken accessToken = null;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT oauth, secret FROM TOKENS WHERE username = ? AND application=?");
			stmt.setString(1, user);
			stmt.setString(2, application);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				accessToken = new AccessToken(rs.getString("oauth"),
						rs.getString("secret"));

			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public void saveOAuthToken(String otoken, String user, String app,
			String secret) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO TOKENS(oauth, username, application, secret) VALUES(?, ?, ?, ?)");
			stmt.setString(1, otoken);
			stmt.setString(2, user);
			stmt.setString(3, app);
			stmt.setString(4, secret);
			stmt.executeUpdate();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
