package com.jaymie;

import com.jaymie.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySiteApplicationTests {

	@Test
	public void contextLoads() {

		String avatarUrl = "https://github.com/identicons/";
		String email = "jyzlove@gmail.com";
		String hash = TaleUtils.MD5encode(email.trim().toLowerCase());
		System.out.println(avatarUrl + hash + ".png");

	}

}
