package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect implements AutoCloseable {
	private static final String connect= "server";
	//private static final String connect= "local";
    private static final String PASS = "1WMG2023";
	private String USER = "root";
	private String URL;
	private Connection connection;

    public DBconnect() {
    	if(connect.equals("local")) {
    		this.USER = "root";this.URL = "jdbc:mysql://localhost:3306/50slash50";
    	}else if(connect.equals("server")) {
    		this.USER = "benatge";this.URL = "jdbc:mysql://192.168.115.4:3306/50slash50";
    	}
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
