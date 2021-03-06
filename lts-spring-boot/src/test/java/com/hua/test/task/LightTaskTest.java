/**
 * 描述: 
 * LightTaskTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.task;

//静态导入
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import com.github.ltsopensource.tasktracker.TaskTracker;
//import com.github.ltsopensource.tasktracker.TaskTracker;
//import com.hua.runner.MyJobRunner;
import com.hua.test.BaseTest;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * LightTaskTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
// for Junit 5.x
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"classpath:conf/spring-scheduler.xml"
		})
public final class LightTaskTest extends BaseTest {

	
	/*
	配置方式1: 
	@WebAppConfiguration(value = "src/main/webapp")  
	@ContextConfiguration(locations = {
			"classpath:conf/xml/spring-bean.xml", 
			"classpath:conf/xml/spring-config.xml", 
			"classpath:conf/xml/spring-mvc.xml", 
			"classpath:conf/xml/spring-service.xml"
		})
	@ExtendWith(SpringExtension.class)
	
	配置方式2: 	
	@WebAppConfiguration(value = "src/main/webapp")  
	@ContextHierarchy({  
		 @ContextConfiguration(name = "parent", locations = "classpath:spring-config.xml"),  
		 @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")  
		}) 
	@ExtendWith(SpringExtension.class)
	 */
	
	/**
	 * 而启动spring 及其mvc环境，然后通过注入方式，可以走完 spring mvc 完整的流程.
	 * 
	 */
	//@Resource
	//private UserController userController;
	
	/**
	 * 引当前项目用其他项目之后，然后可以使用
	 * SpringJunitTest模板测试的其他项目
	 * 
	 * 可以使用所引用目标项目的所有资源
	 * 若引用的项目的配置与本地的冲突或无法生效，需要
	 * 将目标项目的配置复制到当前项目同一路径下
	 * 
	 */
	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testScheduler() {
		try {
			
			
		} catch (Exception e) {
			log.error("testScheduler =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testJobClient() {
		try {
			// 工作客户端
			JobClient jobClient = new JobClient();
			jobClient.setNodeGroup("test_jobClient");
			jobClient.setClusterName(CLUSTER_NAME);
			jobClient.setRegistryAddress(ZOOKEEPER_URL);
			jobClient.start();
			
			// 任务
			Job job = new Job();
			job.setTaskId("38ki3kddk");
			job.setParam("name", "zhangsan");
			job.setTaskTrackerNodeGroup("test_taskTracker");
			// Cron 表达式
			job.setCronExpression("10 * * * * ?");
			// 指定时间执行
			//job.setTriggerDate(new Date());
			
			
			// 提交任务
			Response response = jobClient.submitJob(job);
			
			// {"code":"11","msg":"Can not found JobTracker node!","success":false}
			System.out.println(response.toString());
			
			
			
		} catch (Exception e) {
			log.error("testJobClient =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testTaskTracker() {
		try {
			TaskTracker taskTracker = new TaskTracker();
			//taskTracker.setJobRunnerClass(MyJobRunner.class);
			taskTracker.setRegistryAddress(ZOOKEEPER_URL);
			taskTracker.setNodeGroup("test_trade_TaskTracker");
			taskTracker.setClusterName(CLUSTER_NAME);
			taskTracker.setWorkThreads(20);
			taskTracker.start();
			
		} catch (Exception e) {
			log.error("testTaskTracker =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testTask() {
		try {
			// 工作客户端
			JobClient jobClient = new JobClient();
			// 提交节点组
			jobClient.setNodeGroup("test_jobClient");
			jobClient.setClusterName(CLUSTER_NAME);
			jobClient.setRegistryAddress(ZOOKEEPER_URL);
			jobClient.start();
			
			// 任务: 确定任务多大执行时间/参数等
			Job job = new Job();
			job.setTaskId("38ki3kddk");
			job.setParam("name", "zhangsan");
			// (任务)执行节点组
			job.setTaskTrackerNodeGroup("test_taskTracker");
			// Cron 表达式
			job.setCronExpression("10 * * * * ?");
			// 指定时间执行
			//job.setTriggerDate(new Date());
			
			// 提交任务
			Response response = jobClient.submitJob(job);
			
			// {"code":"11","msg":"Can not found JobTracker node!","success":false}
			System.out.println(response.toString());
			
			// 任务的追踪者
			TaskTracker taskTracker = new TaskTracker();
			//taskTracker.setJobRunnerClass(MyJobRunner.class);
			taskTracker.setRegistryAddress(ZOOKEEPER_URL);
			taskTracker.setNodeGroup("test_taskTracker");
			// (任务)执行节点组
			taskTracker.setClusterName(CLUSTER_NAME);
			taskTracker.setWorkThreads(20);
			taskTracker.start();			
			
			
			
			// 不让主线程提前结束
			Thread.sleep(1000 * 1000);
			
			
		} catch (Exception e) {
			log.error("testTask =====> ", e);
		}
	}
		
	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testTemp")
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testCommon")
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testSimple")
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testBase")
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("beforeMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@BeforeEach
	public void beforeMethod() {
		System.out.println("beforeMethod()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("afterMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@AfterEach
	public void afterMethod() {
		System.out.println("afterMethod()");
	}
	
	/**
	 * 
	 * 描述: 测试忽略的方法
	 * @author qye.zheng
	 * 
	 */
	@Disabled
	@DisplayName("ignoreMethod")
	@Test
	public void ignoreMethod() {
		System.out.println("ignoreMethod()");
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("noUse")
	@Disabled("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(expecteds, actuals, message);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(true, message);
		assertTrue(true, message);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(expecteds, actuals, message);
		assertNotSame(expecteds, actuals, message);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(actuals, message);
		assertNotNull(actuals, message);
		
		fail();
		fail("Not yet implemented");
		
		dynamicTest(null, null);
		
	}

}
