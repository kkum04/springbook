package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import springbook.learningtest.spring.ioc.bean.AnnotatedHello;
import springbook.learningtest.spring.ioc.bean.AnnotatedHelloConfig;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GenericApplicationContextTest {
    private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass())) + "/";

    @Test
    public void contextHierachy() {
        ApplicationContext parent = new GenericXmlApplicationContext(basePath + "parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions(basePath + "childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello, is(notNullValue()));

        hello.print();
        assertThat(printer.toString(), is("Hello Child"));
    }

    @Test
    public void genericApplicationContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("classpath:springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }


    @Test
    public void simpleBeanScanning() {
        ApplicationContext ac = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
        AnnotatedHello hello = ac.getBean("annotatedHello", AnnotatedHello.class);

        assertThat(hello, is(notNullValue()));
    }

    @Test
    public void simpleBeanScanning2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);

        AnnotatedHello hello = ac.getBean("annotatedHello", AnnotatedHello.class);
        assertThat(hello, is(notNullValue()));

        AnnotatedHelloConfig annotatedHelloConfig = ac.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
        assertThat(annotatedHelloConfig, is(notNullValue()));

        assertThat(annotatedHelloConfig.annotatedHello(), is(sameInstance(hello)));
    }

    @Test
    public void resourceApplicationContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("classpath:springbook/learningtest/spring/ioc/resource.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }
}
