package playground;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan
public class PlaygroundApplication {

	public static void main(String[] args)  {
		SpringApplication.run(PlaygroundApplication.class, args);
	}

}
