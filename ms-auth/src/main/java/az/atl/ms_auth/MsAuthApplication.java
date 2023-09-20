package az.atl.ms_auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "User API",version = "1.0",description = "User API Documentation"))
@EnableFeignClients(basePackages = {"az.atl.ms_auth.feign"})
public class MsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthApplication.class, args);
	}

}
