package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String welcome() {

		final String selectSql = "SELECT dna, isMutant FROM dna_mutantes;";

		String url, result = "Welcome!";
		if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
			url = System.getProperty("ae-cloudsql.cloudsql-database-url");
			try {
				Class.forName("com.mysql.jdbc.GoogleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Error loading Google JDBC Driver \t| " + e);
			}
		} else {
			url = System.getProperty("ae-cloudsql.local-database-url");
		}
		System.out.println("connecting to: " + url);
		try {
			Connection conn = DriverManager.getConnection(url);
			try (ResultSet rs = conn.prepareStatement(selectSql).executeQuery()) {
				while (rs.next()) {
					String dna = rs.getString("dna");
					boolean isMutant = rs.getBoolean("isMutant");
					result = "DNA: " + dna + " IsMutant: " + isMutant + "\n";
					System.out.print(result);
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return result;
	}
}