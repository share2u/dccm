package test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ncut.recommend.kmeans.Operation;

public class TestKmeans {
	

    //ApplicationContext继承自BeanFactory接口，
    //其实现类能寻找指定的XML配置文件，找到并装载完成ApplicationContext的实例化工作
    private ApplicationContext ac = null;

    @Before
    public void setUp() throws Exception {
        //配置文件的路径,可以是多个 文件
        String[] configs = {"spring/ApplicationContext-main.xml","spring/ApplicationContext-mvc.xml","spring/ApplicationContext-dataSource.xml","spring/ApplicationContext-redis.xml"};
        //通过spring.xml配置文件创建Spring的应用程序上下文环境
        ac = new ClassPathXmlApplicationContext(configs);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        Operation operation = (Operation) ac.getBean("operation");
        operation.KmeansOperation();
    }

}
