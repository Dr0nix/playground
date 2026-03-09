package playground;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PlaygroundApplication {

	public static void main(String[] args)  {
		// dev 브랸치에서 배포 직전 취합
		SpringApplication.run(PlaygroundApplication.class, args);
	}

}
