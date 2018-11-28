package com.hdsxtech.tjbhzc_datarecp_tcp.server.handler;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author :wanchangxu
 * @version：2012-6-11
 * 
 */
public class SpringUtil {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
}
