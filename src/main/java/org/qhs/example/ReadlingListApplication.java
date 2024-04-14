package org.qhs.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Hello world!
 *
 */
//关闭security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ReadlingListApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ReadlingListApplication.class,args);
    }
}
