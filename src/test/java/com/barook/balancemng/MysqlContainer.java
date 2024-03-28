package com.barook.balancemng;

import lombok.NoArgsConstructor;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@NoArgsConstructor
public class MysqlContainer {

	@Container
	@ServiceConnection
	public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

}
