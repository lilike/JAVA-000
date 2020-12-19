package io.kimmking.rpcfx.demo.consumer;

import annotation.MyFeignClient;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RpcfxClientApplication   {



	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(RpcfxClientApplication.class, args);

		// 首先扫描包获取到特定注解的对象

		// 然后生成动态代理类,然后注册到spring容器里面


		 final String BASE_PACKAGE = "io.kimmking.rpcfx";
		 final String RESOURCE_PATTERN = "/**/*.class";

		Map<String, Class> handlerMap = new HashMap<>();
		getMyClient(BASE_PACKAGE, RESOURCE_PATTERN, handlerMap);

		for (String s : handlerMap.keySet()) {
			Class clazz = handlerMap.get(s);

			// 创建一个动态代理类
			Object o = Rpcfx.create(clazz, "http://localhost:8080/");
			run.getBeanFactory().registerSingleton(clazz.getName(),o);
		}

		UserService userService = run.getBean(UserService.class);
		OrderService orderService = run.getBean(OrderService.class);

		System.out.println(userService.findById(1));
		System.out.println(orderService.findOrderById(2));

	}




	private static void getMyClient(String BASE_PACKAGE, String RESOURCE_PATTERN, Map<String, Class> handlerMap) {
		//spring工具类，可以获取指定路径下的全部类
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		try {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
			Resource[] resources = resourcePatternResolver.getResources(pattern);
			//MetadataReader 的工厂类
			MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (Resource resource : resources) {
				//用于读取类信息
				MetadataReader reader = readerfactory.getMetadataReader(resource);
				//扫描到的class
				String classname = reader.getClassMetadata().getClassName();
				Class<?> clazz = Class.forName(classname);
				//判断是否有指定主解
				MyFeignClient anno = clazz.getAnnotation(MyFeignClient.class);
				if (anno != null) {
					//将注解中的类型值作为key，对应的类作为 value
					handlerMap.put(classname, clazz);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
		}
	}


	private static class TagRouter implements Router {
		@Override
		public List<String> route(List<String> urls) {
			return urls;
		}
	}

	private static class RandomLoadBalancer implements LoadBalancer {
		@Override
		public String select(List<String> urls) {
			return urls.get(0);
		}
	}

	@Slf4j
	private static class CuicuiFilter implements Filter {
		@Override
		public boolean filter(RpcfxRequest request) {
			log.info("filter {} -> {}", this.getClass().getName(), request.toString());
			return true;
		}
	}
}



