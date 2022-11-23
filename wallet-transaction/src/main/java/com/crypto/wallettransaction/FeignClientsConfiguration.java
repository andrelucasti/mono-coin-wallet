package com.crypto.wallettransaction;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.crypto.wallettransaction.thirdparty"})
public class FeignClientsConfiguration {
}
