package util;

import java.util.Properties;

public class PropertyUtil {
	
	public static String getPropertyString() {
		
        Properties properties = new Properties();
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/cars");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "832rehana_B7");
        String url = properties.getProperty("db.url", "");
        String user = properties.getProperty("db.user", "");
        String password = properties.getProperty("db.password", "");
        return url +"?useSSL=false&user=" + user + "&password=" + password;
    }

}