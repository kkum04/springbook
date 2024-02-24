package springbook.learningtest.spring.ioc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SingletonTest {
    @Test
    public void singletonScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(
            SingletonBean.class, SingletonClientBean.class
        );
        HashSet<SingletonBean> beans = new HashSet<>();
        beans.add(ac.getBean(SingletonBean.class));
        beans.add(ac.getBean(SingletonBean.class));
        assertThat(beans.size(), is(1));

        beans.add(ac.getBean(SingletonClientBean.class).bean1);
        beans.add(ac.getBean(SingletonClientBean.class).bean2);
        assertThat(beans.size(), is(1));
    }


    static class SingletonBean {}
    static class SingletonClientBean {
        @Autowired SingletonBean bean1;
        @Autowired SingletonBean bean2;
    }
    
    @Test
    public void prototypeScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(
            PrototypeBean.class, PrototypeClientBean.class
        );

        Set<PrototypeBean> bean = new HashSet<>();

        bean.add(ac.getBean(PrototypeBean.class));
        bean.add(ac.getBean(PrototypeBean.class));
        assertThat(bean.size(), is(2));

        bean.add(ac.getBean(PrototypeClientBean.class).bean1);
        bean.add(ac.getBean(PrototypeClientBean.class).bean2);
        assertThat(bean.size(), is(4));
    }
    
    @Scope("prototype")
    static class PrototypeBean {}
    
    static class PrototypeClientBean {
        @Autowired PrototypeBean bean1;
        @Autowired PrototypeBean bean2;
    }
}
