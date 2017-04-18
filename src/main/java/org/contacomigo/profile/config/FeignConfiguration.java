package org.contacomigo.profile.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.contacomigo.profile")
public class FeignConfiguration {

}
