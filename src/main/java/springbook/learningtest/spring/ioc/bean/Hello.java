package springbook.learningtest.spring.ioc.bean;

import javax.annotation.Resource;

public class Hello {
    String name;

    @Resource
    Printer printer;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String sayHello() {
        return "Hello " + name;
    }

    public void print() {
        this.printer.print(sayHello());
    }

    public void setName(String name) {
        this.name = name;
    }
}
