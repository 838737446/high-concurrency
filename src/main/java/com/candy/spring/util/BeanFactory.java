package com.candy.spring.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/26 15:17
 * @Modified:
 */
public class BeanFactory {

    private String xmlPath;

    public BeanFactory(String xmlPath){
        File file=new File(this.getClass().getResource("/").getPath()+"//"+xmlPath);
        SAXReader reader = new SAXReader();
        try {
            Document document=reader.read(file);
            Element rootElement=document.getRootElement();
            String defaultAutowire=rootElement.attributeValue("default-autowire");
            Iterator<Element> beanIterator=rootElement.elementIterator();
            while (beanIterator.hasNext()){
                //实例化对象
                Element elementChild=beanIterator.next();
                System.out.println(elementChild.getName());
            }
            if(defaultAutowire==null){

            }
            //自动装配，即不用再
            else if("byType".equals(defaultAutowire)){

            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
