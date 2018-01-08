package cn.ncut.util.wechat;

import java.lang.reflect.Method;
import java.util.Properties;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.test.context.TestExecutionListeners;

/**
 * 读取配置文件中关于属性的设置项
 * */
public class SpringPropertyResourceReader {
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/ApplicationContext-wechat.xml");
	private static AbstractApplicationContext abstractContext = (AbstractApplicationContext) applicationContext;
	private static Properties properties = new Properties();
	static {
		try {
			// get the names of BeanFactoryPostProcessor
			String[] postProcessorNames = abstractContext.getBeanNamesForType(BeanFactoryPostProcessor.class, true,
					true);

			for (String ppName : postProcessorNames) {
				// get the specified BeanFactoryPostProcessor
				BeanFactoryPostProcessor beanProcessor = abstractContext.getBean(ppName,
						BeanFactoryPostProcessor.class);
				// check whether the beanFactoryPostProcessor is
				// instance of the PropertyResourceConfigurer
				// if it is yes then do the process otherwise continue
				if (beanProcessor instanceof PropertyResourceConfigurer) {
					PropertyResourceConfigurer propertyResourceConfigurer = (PropertyResourceConfigurer) beanProcessor;

					// get the method mergeProperties
					// in class PropertiesLoaderSupport
					Method mergeProperties = PropertiesLoaderSupport.class.getDeclaredMethod("mergeProperties");
					// get the props
					mergeProperties.setAccessible(true);
					Properties props = (Properties) mergeProperties.invoke(propertyResourceConfigurer);

					// get the method convertProperties
					// in class PropertyResourceConfigurer
					Method convertProperties = PropertyResourceConfigurer.class.getDeclaredMethod("convertProperties",
							Properties.class);
					// convert properties
					convertProperties.setAccessible(true);
					convertProperties.invoke(propertyResourceConfigurer, props);

					properties.putAll(props);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param propertyName key
	 * @return 属性文件key对应的value
	 * */
	public static String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
}