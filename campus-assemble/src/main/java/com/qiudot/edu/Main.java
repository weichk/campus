package com.qiudot.edu;



import com.acooly.core.common.BootApp;
import com.acooly.core.common.boot.Apps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * @author qiubo
 */
@BootApp(sysName = "campus", httpPort = 8888)
@ServletComponentScan
public class Main {
    public static void main(String[] args) {
        Apps.setProfileIfNotExists("net");
        new SpringApplication(Main.class).run(args);
    }
}