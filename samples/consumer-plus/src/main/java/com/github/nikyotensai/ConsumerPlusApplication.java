package com.github.nikyotensai;

import com.github.nikyotensai.feign.EnableFeignPlusClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;


@SpringCloudApplication
@EnableFeignPlusClients
public class ConsumerPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerPlusApplication.class);
    }

}
