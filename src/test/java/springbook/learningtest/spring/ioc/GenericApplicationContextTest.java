package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.learningtest.spring.ioc.bean.Hello;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GenericApplicationContextTest {
    @Test
    public void genericApplicationContext() {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("classpath:springbook/learningtest/spring/ioc/genericApplicationContext.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }
}
