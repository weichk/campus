//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.0 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.09.28 时间 01:16:54 PM CST 
//


package com.qiudot.edu.client.whdc;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hello.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hello.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WHWebServiceWHDCIF }
     * 
     */
    public WHWebServiceWHDCIF createWHWebServiceWHDCIF() {
        return new WHWebServiceWHDCIF();
    }

    /**
     * Create an instance of {@link WHWebServiceWHDCIFResponse }
     * 
     */
    public WHWebServiceWHDCIFResponse createWHWebServiceWHDCIFResponse() {
        return new WHWebServiceWHDCIFResponse();
    }

}
