package com.reserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.io.IOException;

@SpringBootApplication
public class ReserveApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ReserveApplication.class, args);
//        String cmd = "cmd /c start http://localhost:8081";
//        Runtime run = Runtime.getRuntime();
//        run.exec(cmd);
    }
    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}
