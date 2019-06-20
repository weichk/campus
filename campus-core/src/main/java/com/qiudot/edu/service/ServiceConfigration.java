package com.qiudot.edu.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.qiudot.edu.client.whdc.WHWebServiceWHDCIF;
import com.qiudot.edu.client.whdc.WebServiceClient;
import com.qiudot.edu.config.CampusProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author qiubo
 */
@Configuration
@EnableConfigurationProperties({CampusProperties.class})

public class ServiceConfigration {
    @Bean
    public AlipayClient alipayClient(CampusProperties properties) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getServerUrl(), properties.getAppId(), properties.getPrivateKeyContent(), "json", "utf-8", properties.getAlipayPublicKeyContent(), "RSA2");
        return alipayClient;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(WHWebServiceWHDCIF.class.getPackage().getName());
        return marshaller;
    }

    @Bean
    public WebServiceClient countryClient(Jaxb2Marshaller marshaller) {
        WebServiceClient client = new WebServiceClient();
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
