# SpringBoot学习笔记

## 1. SpringBoot是什么

SpringBoot用来简化创建基于Spring的独立的生产级别的应用。它只需要最少量的Spring配置。

### 特性

1. 生成单体spring应用
2. 嵌入式tomcat，jetty或者undertow，可以直接以jar包运行
3. 提供starter依赖简化构建配置
4. 自动配置spring和第三方库
5. 准生产级的特性，如指标，运行状况检查，外部配置等
6. 无代码生成，无xml配置

## 2. 快速开始

系统环境：win10 pro，jdk1.8+，maven 3.6，环境变量配置好JAVA_HOME，jdk和maven的bin目录

工具：powershell，sublime text，IDEA

```powershell
> javac -version
javac 1.8.0_172

> mvn -version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: D:\developer\java\maven\maven-3.6.3\bin\..
Java version: 1.8.0_172, vendor: Oracle Corporation, runtime: D:\developer\java\jdk1.8\jre
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

新建目录：GetStarted

```powershell
> mkdir GetStarted
> cd GetStarted
```



### 2.1 编写pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>myproject</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <!-- 继承parent，提供springboot依赖版本控制和maven默认设置 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.9.RELEASE</version>
    </parent>



    <description/>
    <developers>
    <developer/>
    </developers>
    <licenses>
    <license/>
    </licenses>

    <!-- 依赖库 -->

    <dependencies>
        <!-- web模块的starter，提供Embed Tomcat和web应用相关的依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- 生成可执行jar的插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 2.2 编写Example.java

```powershell
> mkdir -p src/main/java
> cd .\src\main\java\
> subl.exe Example.java
```

```java
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello Spring Boot!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }

}
```

注解解析：

1. `@RestController`：标注此注解的类，Spring会在请求进来时查询是否它可以处理该请求，并且是直接将方法结果返回给调用方，这是与`@Controller`注解不一样的地方。
2. `@RequestMapping`：该注解提供路由信息，表示路径是"/"的请求会被`home()`方法处理。
3. `@EnableAutoConfiguration`：开启自动配置功能，Spring会根据导入依赖jar的情况开启默认配置信息。通常与“starter”机制配合使用

main方法：应用入口，将会代理到`SpringApplication.run`方法，依次启动应用，spring以及自动配置的Embed Tomcat容器。需要在参数中指定Example.class作为主组件并可以使用`args`参数将命令行参数传递给应用程序。通常添加`@SpringBootApplication`注解。

### 2.3 编译运行

返回项目目录下，执行：

```powershell
> mvn spring-boot:run
```

打开 http://localhost:8080

### 2.4 生成可执行jar包

Spring Boot Build Tool Plugin提供了一种**repackage**功能，能对maven打包好的jar封装成可执行的jar。在pom.xml添加以下插件即可使用：

```xml
    <build>
        <plugins>
            <!-- 生成可执行jar的插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```



1. 打包

   ```powershell
   > mvn package
   ```

2. 执行

   ```powershell
   > java -jar .\target\myproject-0.0.1-SNAPSHOT.jar
   ```

### 2.5 使用spring initializr

可以使用IDEA集成开发工具或者<https://start.spring.io/> 快速生成项目结构。



## 3. 如何使用SpringBoot

### 3.1 代码布局

project/src/main/java目录下：

```
com
 +- example
     +- myapplication
         +- Application.java
         |
         +- entity
         |   +- Customer.java
         |	 +- Order.java
         +- controller
         |   +- CustomerController.java
         |	 +- OrderController.java
         +- service
         |   +- CustomerService.java
         |	 +- OrderService.java
         +- repository
         |   +- CustomerRepository.java
         | 	 +- OrderRepository.java             
```

通常`Application.java`类使用`@SpringBootApplication`注解作为启动类，如下所示：

```java
package com.example.myapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```



### 3.2 配置类

1. `@Configuration： `：SpringBoot支持使用`@Configuration `自定义配置类
2. `@Import`：导入第三方配置类
3. `@ImportResource`：导入XML配置
4. `@ComponentScan`：导入子包下所有`@Component`的Bean



### 3.3 自动配置

Spring Boot自动配置会尝试根据添加的jar依赖关系自动配置Spring应用程序。

前提：在任意标注`@Configuration`注解的类上添加`@EnableAutoConfiguration `注解或者`@SpringBootApplication `注解。

可以在自动配置的基础上进行自定义配置。只需要简单注入自定义bean去替换默认配置即可。

如果不希望应用某一个自动配置，只需排除即可，如下所示：

```java
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MyApplication {
}
```



### 3.4 Spring Beans和依赖注入

与Spring一样，Spring Boot依然使用`@ComponentScan`进行包扫描和使用`@Component,@Service,@Controller,@Repository`等注解进行bean注入。



### 3.5 `@SpringBootApplication`注解

该注解相当于一个复合注解：

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
...
}
```

标注该注解的类可以同时具备配置类，包扫描，以及开启自动配置的功能。

### 3.6 运行SpringBoot应用

1. IDE工具：IDEA，Eclipse
2. 可执行jar包：`java -jar XXX.jar`
3. Maven Plugin：`mvn spring-boot:run`，可以设置环境变量来控制JVM的堆内存大小：`export MAVEN_OPTS=-Xmx1024m`。
4. Gradle Plugin：`gradle bootRun`，设置堆内存大小：`export JAVA_OPTS=-Xmx1024m`。

### 3.7 devtools

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

devtools需要配合IDE工具来进行热启动。默认情况下，当classpath下文件有变更时，原来的restart类加载器被丢弃，新的restart类加载器被创建和重新加载类文件，从而完成快速重启。

有些文件改动不应该引起重新启动，可以在`application.properties`文件中配置：

```properties
spring.devtools.restart.exclude=static/**,public/**
```

或者需要不在classpath下的文件改动时进行重启：

```properties
spring.devtools.restart.additional-paths=XXPath
```

禁用重启功能：

```properties
spring.devtools.restart.enabled=false
```



## 4. SpringBoot功能特性

### 4.1 配置

 [参考文档](https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/reference/htmlsingle/#boot-features-external-config)

Spring Boot允许你使用外部化配置，这样同一份代码就可以运行在不同的环境中。

#### 4.1.1 配置优先级

较常用的配置优先级：

1. 命令行参数，如 `java -jar --server.port=8080 xxx.jar`

   使用 --name=value的格式进行参数配置。

2. 系统环境变量

3. jar外部的`application-{profile}.properties`或者`application-{profile}.yml`

4. jar内部的`application-{profile}.properties `或者`application-{profile}.yml`

5. jar外部的`application.properties`或者`application.yml`

6. jar内部的`application.properties`或者`application.yml`

7. `@PropertySource `注解包含的配置

.properties或者.yml配置文件放在`src/main/resources`目录下，编译之后即在`classpath:/`下。

#### 4.1.2 配置文件路径

`application.yml`的加载路径优先级为

1. ./config/
2. ./
3. classpath:/config/
4. classpath:/

#### 4.1.3 yml语法

**key: value**：冒号后面需要有空格。并且以缩进代表层级，如：

```yaml
spring: 
	application:
		name: myproject
```

字符串不需要添加引号。如要表示特殊字符，如转义字符，则需要使用引号：

- 双引号："my \n project"，\n会被解析为换行符。
- 单引号：'my \n project'，\n会被保留。

1. 对象和Map类型语法

   如person是对象或者map，它的属性应该另起一行

   - 多行写法

     ```yaml
     person:
     	name: li
     	age: 20
     ```

   - 单行写法

     ```yaml
     person: {name: li, age: 20}
     ```

     

2. 数组和列表类型语法

   - 多行写法：

     ```yaml
     list: 
     	- li
     	- gao
     	- bai
     ```

   - 单行写法：

     ```yaml
     list: [cat, dog, duck]
     ```

3. 占位符

   允许使用${name}来引用属性值，如：

   ```yaml
   name: li
   name2: ${name}
   ```

   

#### 4.1.4 属性值注入

   SpringBoot允许将`application.yml`文件中的键值设置为Java bean的属性值。

   1. 使用`@Value`：允许注入单个值
   2. 使用`@ConfigurationProperties`：以JavaBean为单位批量注入多个值，可以进行数据校验和注入复杂类型。类型安全。
   3. 使用`@PropertyResource`：加载指定的配置文件

   示例：

   `application.yml`：

   ```yaml
   my:
     config:
       name: hundanli
       birth: 2010/09/03
       map: {k1: v2, k2: v2}
       list: [cat, dog, duck]
   ```

   MyConfigProperties.java`：

   ```java
@Component
@ConfigurationProperties(prefix = "my.config")
@Data
public class MyConfigProperties {

    private String name;
    private Date birth;
    private Map<String, String> map;
    private List<String> list;

}
   ```

   

#### 4.1.5 多profile

1. 多个application.yml文件：application-{profile}.yml
2. 单个application.yml文件多个profile用 `---` 分隔

- 激活环境：
  - 在application.yml中设置spring.profiles.active=${profile}
  - 在application.yml中设置spring.profiles.include=${profile}来添加配置

不要混合使用1，2两种profile方式。

还可以通过`@Profile`注解来对`@Configuration`标注的类，以此在特定环境下配置类的内容生效。

```java
@Configuration(proxyBeanMethods = false)
@Profile("production")
public class ProductionConfiguration {

    // ...

}
```

#### 4.1.6 自动配置原理

在主启动类上添加了 `@EnableAutoConfiguration`注解后，SpringBoot会通过`AutoConfigurationImportSelector`读取每个jar包下META-INF/spring.factories文件，将key为 **org.springframework.boot.autoconfigure.EnableAutoConfiguration**的类进行自动配置。如`org.springframework.boot.autoconfigure`jar包下的`spring.factories`文件如下：

```factories
......

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,\

.......

org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\

...

```



`xxxAutoConfiguration`：配置类，其中功能是根据条件添加了某些组件，如`RedisAutoConfiguration`：其中添加了RedisTemplate和StringRedisTemplate两个bean。同时`@EnableConfigurationProperties`注解通过`RedisProperties`类注入属性值，并将这个类实例加载到ioc容器中。

`xxxProperties`：属性值类，通过`ConfigurationProperties`注入配置文件中的属性值。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

}
```

可以通过配置`debug=true`来查看哪些自动配置生效。



### 4.2 日志

#### 4.2.1 日志框架

常用的日志框架

| 抽象api                                                      | 日志实现                                              |
| ------------------------------------------------------------ | ----------------------------------------------------- |
| JCL（Jakarta Commons Logging）、 SLF4j（Simple Logging Facade for Java）、 jboss-logging | Log4j 、JUL（java.util.logging）、 Log4j2 **Logback** |

SpringBoot底层使用的日志框架组合：slf4j + logback。同时还将jul和log4j转接到了slf4j

如何使用日志：配合`@Slf4j`使用或者使用`org.slf4j.LoggerFactory`创建Loggger实例

```java
@Slf4j
@SpringBootTest
class MyConfigPropertiesTest {

    @Test
    void getName() {
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
    }
}
```



#### 4.2.2 日志格式

```
2020-08-05 10:23:36.440  INFO 17176 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

```

时间：Date Time

日志级别：`ERROR`, `WARN`, `INFO`, `DEBUG`, or `TRACE`. 

进程id

分隔符：---

线程名称

Logger名称

日志消息



#### 4.2.3 日志配置

可以通过`application.yml`对日志进行设置。

1. 输出到文件

   ```yaml
   logging:
   	file:
   		name: # 绝对或者相对路径文件名
   		path: # 绝对或者相对路径目录名，生成spring.log文件
   ```

   

2. 日志级别

   ```yaml
   logging:
   	level:
   		root: # 设置全局日志级别，默认info
   		org.springframework.web: # 设置某个包的日志级别
   ```

   还可以定义日志组统一设置日志级别：

   ```yaml
   logging:
   	group:
   		tomcat: org.apache.catalina, org.apache.coyote, org.apache.tomcat
   	level:
   		tomcat: warn
   ```

   

3. 输出格式

   ```yaml
   logging:
   	pattern:
       	console: #设置控制台
       	file: # 文件
       	dateformat: # 时间
   ```

   #### 4.2.4 自定义日志配置

   | Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, or `logback.groovy` |
   | ----------------------- | ------------------------------------------------------------ |
   | Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`                          |
   | JDK (Java Util Logging) | `logging.properties`                                         |



### 4.3 Web开发

SpringBoot需要导入`spring-boot-starter-web `或者`spring-boot-starter-webflux `模块支持Web应用开发，并可使用嵌入式Http服务器如Tomcat，Jetty和Undertow。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### 4.3.1 Spring MVC

SpringBoot可以无缝衔接Spring MVC框架，常用注解：

`@Controller, @RestController, @RequestMapping, @GetMapping, @PostMapping, @RequestBody, @ResposeBody, @PathVariable`等。

##### 1.Spring MVC自动配置

SpringBoot为SpringMVC提供了有用的默认配置：

- 视图解析器
- 静态资源映射
- 转换器/格式化器注册
- `HttpMessageConverters`  支持
- `MessageCodesResolver`  支持
- 静态首页`index.html`
- favicon.ico
- `ConfigurableWebBindingInitializer`  bean

###### 1.1扩展Spring MVC配置

如添加view controller，interceptor等：编写实现`WebMvcConfigurer`  接口的配置类，不添加`@EnableWebMvc `注解。

```java
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addStatusController("/hello", HttpStatus.ACCEPTED);
    }


}
```



###### 1.2完全接管Spring MVC配置

不适用自动配置，只需在1.1的类上添加`@EnableWebMvc`注解



##### 2.HttpMessageConverters

Spring MVC使用`HttpMessageConverter`接口转换http请求和响应，包含了一些默认功能，如自动将对象转换为json数据。

##### 3. 静态资源

默认静态资源文件目录：

项目根路径/以及classpath下的

- /static
- /public
- /resources
- /META-INF/resources

参考源代码：

```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	/**
	 * Locations of static resources. Defaults to classpath:[/META-INF/resources/,
	 * /resources/, /static/, /public/].
	 */
	private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
}
```

###### 自定义配置

注意要使用自动配置。

```yaml
spring:
  resources:
    static-locations: classpath:/static/ # 静态资源目录
  mvc:
    static-path-pattern: /static/**  # url映射规则： /static/banner.jpg 路径将访问到 classpath:/static/banner.jpg文件；默认为/**，则/banner.jpg路径映射到classpath:/static/banner.jpg
```

SpringBoot还支持webjars。



##### 4. 模板引擎技术

SpringBoot提供了四种模板引擎的自动配置：

- FreeMarker
- Thymeleaf
- Groovy
- Mustache

如Thymeleaf的starter依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

当使用默认配置时，Spring MVC会在`src/main/resources/templates `目录下寻找模板。



##### 5. 错误处理

###### 1.全局异常处理

Spring MVC提供`@ControllerAdvice, @RestControllerAdvice`注解来进行全局异常处理，示例：

```java
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        return e.toString();
    }
}
```

###### 2.定制错误页面

在静态资源目录下新建error目录，编写404.html，500.html等以状态码命名的页面即可。

##### 

##### 6. 跨域支持

全局跨域配置：

```java
@Configuration(proxyBeanMethods = false)
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }
}
```

#### 4.3.2 嵌入式Servlet容器

##### 1. 修改Servlet容器

SpringBoot默认使用Tomcat作为Servlet容器，如果需要更改为Undertow或者Jetty，配置如下：

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>
    </dependencies>
```

##### 2. 使用外部容器

1. 创建war项目

2. 指定Tomcat为provided

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring‐boot‐starter‐tomcat</artifactId>
       <scope>provided</scope>
   </dependency>
   ```

   

3. 编写**SpringBootServletInitializer** 的子类并实现configure方法

   ```java
   public class ServletInitializer extends SpringBootServletInitializer {
       @Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
           return application.sources(Application.class);
       }
   }
   ```

   

4. 启动Tomcat服务器



### 4.4 SQL数据访问

#### 4.4.1 配置数据源

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

配置数据库信息：

```yaml
spring:
	datasource:
    #    type: com.zaxxer.hikari.HikariDataSource
        url: jdbc:mysql://localhost:3306/myproject?serverTimezone=Asia/Shanghai
        username: root
        password: root
```

SpringBoot将会通过`DataSourceAutoConfiguration`自动配置类结合`DataSourceProperties`类读取配置文件信息注入数据源和数据库连接池的组件。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@EnableConfigurationProperties(DataSourceProperties.class)
@Import({ DataSourcePoolMetadataProvidersConfiguration.class, DataSourceInitializationConfiguration.class })
public class DataSourceAutoConfiguration {
}
```

SpringBoot内置支持 [HikariCP](https://github.com/brettwooldridge/HikariCP) ，Tomcat Pool和DBCP2三种连接池技术。默认使用HikariCP连接池。

测试连接：

```java
@SpringBootTest
public class DataSourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
```

##### 使用Druid数据连接池

导入依赖

```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.23</version>
        </dependency>
```

注入`Datasource`组件：

```java
@Configuration
public class DruidDataSourceConfig {

    @Bean
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
```



#### 4.4.2 整合Mybatis

##### 1.导入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>

```

##### 2.编写Mapper接口

编写一个Java Bean：

```java
@Data
public class Person {
    private Long id;
    private String name;
    private String age;
}
```

Mapper接口：

```java
@Mapper
public interface PersonMapper {

    List<Person> selectAll();
}
```

##### 3.编写sql映射文件

在`src/main/resources/mapper`目录新建PersonMapper.xml文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.tcl.sql.mapper.PersonMapper">
    <select id="selectAll" resultType="com.tcl.sql.Person">
        select * from person
    </select>
</mapper>
```

##### 4.在application.yml编写配置

```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml	# 指定sql映射文件所在位置！！！重要
```

##### 5. 运行测试

```java
@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;

    @Test
    void selectAll() {
        List<Person> people = mapper.selectAll();
        System.out.println(people);

    }
}
```



#### 4.4.3 整合Mybatis Plus

##### 1.创建数据库和表

```sql
create database mybatis_plus;

DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);
```

插入测试数据：

```sql
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@tcl.com'),
(2, 'Jack', 20, 'test2@tcl.com'),
(3, 'Tom', 28, 'test3@tcl.com'),
(4, 'Sandy', 21, 'test4@tcl.com'),
(5, 'Billie', 24, 'test5@tcl.com');
```



##### 2.导入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--mybatis plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.2</version>
        </dependency>
```

##### 3.配置

`application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=Asia/Shanghai
    username: root
    password: root
```

主启动类：

```java
@SpringBootApplication
@MapperScan(basePackages = "com.tcl.mp.mapper")	// MapperScan 扫描Mapper目录
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

}
```

##### 4.编码

`User.java`:

```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

`UserMapper.java`：

```java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcl.mp.entity.User;

public interface UserMapper extends BaseMapper<User> {

}
```

##### 5.运行测试

```java
@Slf4j
@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void selectAll() {
        log.info("测试查询");
        List<User> users = userMapper.selectList(null);
        Assertions.assertEquals(5, users.size());
        users.forEach(System.out::println);

    }

}
```



### 4.5 NoSQL数据访问

#### 4.5.1 整合Redis

##### 1.导入依赖

```xml
        <!--redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

##### 2.配置连接

```yaml
spring:
    redis:
        host: localhost
        port: 6379
```

Spring Boot自动配置默认使用Lettuce客户端连接redis服务器，同时支持jedis。如果添加了commons-pool2，那么将会自动使用连接池。

Spring Boot还自动注入了`RedisTemplate`和`StringRedisTemplate`两个类的实例。如果需要替换默认的`RedisTemplate`，需要指定Bean的名称，这是因为自动配置中设定了当名称为redisTemplate的Bean不存在时都会自动注入。

```java
public class RedisAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
  ...
}
```

##### 3.使用RedisTemplate

```java
@Slf4j
@SpringBootTest
class RedisTemplateTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {
        log.info(redisTemplate.toString());
        redisTemplate.opsForValue().set("key", "value");
        Object key = redisTemplate.opsForValue().get("key");
        Assertions.assertEquals(key, "value");

    }
}
```

##### 4.使用Spring Data

1. 实体类

   ```java
   @RedisHash("User")
   @Data
   public class User {
       @Id
       private Integer id;
       private String username;
       private String password;
   }
   ```

   `@RedisHash("User")`：该注解标记改对象将会被存储为Hash类型

   `@Id`：作为标识符属性

2. Repository接口

   ```java
   import org.springframework.data.repository.CrudRepository;
   
   
   public interface UserRepository extends CrudRepository<User, Integer> {
   
   }
   ```

   继承`CrudRepository`接口获得基本的增删查改功能。

3. 配置

   ```java
   @Configuration
   @EnableRedisRepositories
   public class RedisConfig {
   
       @Bean
       public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
           RedisTemplate<String, Object> template = new RedisTemplate<>();
           template.setConnectionFactory(redisConnectionFactory);
           return template;
       }
   
   }
   ```

   `@EnableRedisRepositories`：

4. 运行测试

   ```java
   @Slf4j
   @SpringBootTest
   class UserRepositoryTest {
   
       @Autowired
       UserRepository repository;
   
       @Test
       void testOperations() {
           log.info("=========Redis Repository 测试========");
           User user = new User();
           user.setId(100);
           user.setUsername("hundanli");
           user.setPassword("password");
           repository.save(user);
           User get = repository.findById(user.getId()).orElse(null);
           System.out.println(get);
           Assertions.assertEquals(user, get);
   
           repository.delete(user);
           Assertions.assertEquals(0, repository.count());
   
       }
   }
   ```

   

#### 4.5.2 整合ElasticSearch

由于Spring Data跟ElasticSearch需要版本对应，存在限制，因此一般使用ES官方提供的rest api操作es服务器。

##### 1.添加依赖

```xml
        <!--elasticsearch-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
```

##### 2.配置连接

```yaml
spring:
	elasticsearch:
    	rest:
      		uris: http://127.0.0.1:9200 # 默认: http://localhost:9200
```

##### 3.运行测试

因为Spring Boot自动配置把`RestClient`和`RestHighLevelClient`实例加入到了ioc容器中，因此使用时只需要注入即可：

```java
SpringBootTest
class ElasticsearchRestClientTest {

    @Autowired
    RestClient restClient;

    @Autowired
    RestHighLevelClient highLevelClient;

    @Test
    void testLowLevel() throws IOException {
        Request request = new Request("GET", "/");
        Response response = restClient.performRequest(request);
        System.out.println(response.getEntity());
    }

    @Test
    void testHighLevel() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "hundanli");
        jsonMap.put("age", 20);
        jsonMap.put("email", "hundanli@tcl.com");
        IndexRequest indexRequest = new IndexRequest("users", "info", "1").source(jsonMap);
        IndexResponse response = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getResult());

        GetRequest getRequest = new GetRequest("users", "info", "1");
        GetResponse getResponse = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());

        DeleteRequest deleteRequest = new DeleteRequest("users", "info", "1");
        DeleteResponse deleteResponse = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
    }
}
```



### 4.6 缓存Cache

Spring Boot如何开启缓存功能：在主启动类添加`@EnableCaching `注解。

支持的缓存技术包括：Simple，Redis，EhCache，JCache等等。

#### 4.6.1 Redis缓存

##### 1.pom依赖：

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
```

##### 2.缓存配置

`application.yml`

```yaml
spring:
  cache:
    type: redis	# 指定缓存技术类型
    redis:
      key-prefix: myproject # key前缀
    cache-names: cache-1,cache-2,cache-3	# 缓存名称空间
```

添加`@EnableCaching`注解

##### 3.定制缓存管理器

创建`CacheManagerCustomizer`的实例以便在缓存管理器完全初始化前进行定制：

```java
@Configuration
public class CacheConfig {
    @Bean
    public CacheManagerCustomizer<RedisCacheManager> cacheManagerCustomizer() {

        return new CacheManagerCustomizer<RedisCacheManager>() {
            @Override
            public void customize(RedisCacheManager cacheManager) {
                System.out.println(cacheManager.getCacheNames());
            }
        };
    }
}
```

更多配置可以使用`RedisCacheManagerBuilderCustomizer `进行设定，如为每个cache名称空间创建不同的CacheManager并设置不同的过期时间：

```java
    @Bean
    public RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer() {
        return new RedisCacheManagerBuilderCustomizer() {
            @Override
            public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
                builder.
                        withCacheConfiguration("cache-1",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));

                builder.
                        withCacheConfiguration("cache-2",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)));

                builder.
                        withCacheConfiguration("cache-3",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)));


            }
        };
    }
```

##### 4.编码测试

`City.java`:

```java
@Data
public class City implements Serializable {
    private Integer id;
    private String cityName;
    private String population;
    private String province;
}
```

`CityService.java`：

```java
@Service
@Slf4j
public class CityService {
    @Cacheable(cacheNames = "cache-1", keyGenerator = "cityListKeyGenerator")
    public List<City> getCityList() {
        log.info("+++++执行查询++++++++");
        List<City> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            City city = new City();
            city.setId(1);
            city.setCityName("city-" + (i + 1));
            city.setProvince("广东");
            list.add(city);
        }
        return list;
    }

    @CacheEvict(cacheNames = "cache-1", keyGenerator = "cityListKeyGenerator")
    public void deleteCity() {

    }
}
```

`CityController.java`：

```java
@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping("/list")
    public List<City> getCityList() {
        return cityService.getCityList();
    }

    @GetMapping("/evict")
    public void deleteCity() {
        cityService.deleteCity();
    }
}
```

`CityListKeyGenerator.java`

```java
@Component
public class CityListKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return method.getClass() + "#CityList";
    }

}
```



### 4.7 消息Messaging

#### 4.7.1 Kafka支持

##### 1. Kafka安装与启动

下载并解压Kafka安装包，使用powershell进入到安装路径根目录：

1. 启动zookeeper

   ```powershell
   > .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   ```

   

2. 启动kafka-server

   ```powershell
   > .\bin\windows\kafka-server-start.bat .\config\server.properties
   ```

   

3. 使用kafka-console-producer发送消息

   ```powershell
   > .\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test
   >Hello Kafka
   >This is TCL SWTC
   ```

   

4. 使用kafka-console-consumer接收消息

   ```powershell
   > .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
   Hello Kafka
   This is TCL SWTC
   ```



##### 2. 导入依赖和配置

```xml
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
```

导入依赖后，Spring Boot将自动配置`KafkaTemplate  `，也可以自主注入该类的实例，可以通过`application.yml`文件配置更多信息：

```yaml
spring:
	kafka:
    	bootstrap-servers: localhost:9092 # Kafka服务器地址
    	consumer:
      		group-id: myproject	# 组id，必需
```

##### 3.编码测试

```java
@Service
public class KafkaService {
    @Autowired
    KafkaTemplate kafkaTemplate;

    String topic = "test";


    public void sendMessage(String msg) {
        kafkaTemplate.send(topic, msg);
    }

    @KafkaListener(topics = "test")
    public void receiveMessage(String content) {
        System.out.println("Kafka receiving msg: " + content);

    }

}

```

```java
@RestController
public class KafkaController {

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/kafka/{msg}")
    public String sendMsg(@PathVariable String msg) {
        kafkaService.sendMessage(msg);
        return "OK";
    }
}
```



#### 4.7.2 RocketMQ支持

##### 1.RocketMQ安装与启动

1. 下载并解压安装包，配置环境变量

   ```
   ROCKETMQ_HOME="D:\rocketmq"
   NAMESRV_ADDR="localhost:9876"
   ```

   

   打开安装根目录，可以编辑`bin\runbroker.cmd`文件修改JVM堆内存大小防止内存溢出。

2. 启动Name服务器

   ```powershell
   .\bin\mqnamesrv.cmd
   ```

   

3. 启动Broker服务器

   ```powershell
   .\bin\mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true
   ```

   

4. 测试producer和consumer

   ```powershell
   .\bin\tool.cmd  org.apache.rocketmq.example.quickstart.Producer
   ```

   ```powershell
   .\bin\tool.cmd  org.apache.rocketmq.example.quickstart.Consumer
   ```

##### 2.导入依赖

```xml
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-client</artifactId>
            <version>4.7.1</version>
        </dependency>
```

##### 3.编写自动配置类

```java
@EnableConfigurationProperties({RocketMqConfig.RocketMqProducerProperties.class,
        RocketMqConfig.RocketMqConsumerProperties.class})
@Configuration
public class RocketMqConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqConfig.class);

    @Bean
    @ConditionalOnMissingBean
    public DefaultMQProducer defaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer();

        producer.setNamesrvAddr(producerProperties.getNamesrvAddr());

        producer.setProducerGroup(producerProperties.getGroupName());

        producer.setMaxMessageSize(producerProperties.getMaxMessageSize());

        producer.setSendMsgTimeout(producerProperties.getSendMsgTimeout());

        producer.setRetryTimesWhenSendFailed(producerProperties.getRetryTimesWhenSendFailed());


        try {
            producer.start();
            LOGGER.info("RocketMQ Producer started. Group-name: " + producer.getProducerGroup() + ", Namesrv-Addr: " + producer.getNamesrvAddr());
        } catch (MQClientException e) {
            LOGGER.error("Fail to start RocketMQ Producer: " + e.getErrorMessage());
            throw new RuntimeException(e);
        }

        return producer;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        consumer.setNamesrvAddr(consumerProperties.namesrvAddr);
        consumer.setConsumerGroup(consumerProperties.groupName);
        consumer.setConsumeThreadMin(consumerProperties.consumeThreadMin);
        consumer.setConsumeThreadMax(consumerProperties.consumeThreadMax);

        consumer.setConsumeMessageBatchMaxSize(consumerProperties.consumeMessageBatchMaxSize);

        try {
            consumer.subscribe(consumerProperties.topic, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            LOGGER.info("RocketMQ Consumer started. Group-name: " + consumer.getConsumerGroup() + ", Namesrv-Addr: " + consumer.getNamesrvAddr());
        } catch (MQClientException e) {
            LOGGER.error("Fail to start RocketMQ Consumer: " + e.getErrorMessage());
            throw new RuntimeException(e);
        }

        return consumer;
    }


    @Autowired
    RocketMqProducerProperties producerProperties;

    @Autowired
    RocketMqConsumerProperties consumerProperties;

    /**
     * groupName: 发送同一类消息的设置为同一个 group，保证唯一， 默认不需要设置，rocketmq 会使用 ip@pid(pid代表jvm名字) 作为唯一标示。
     * namesrvAddr：Name Server 地址
     * maxMessageSize：消息最大限制，默认 4M
     * sendMsgTimeout：消息发送超时时间，默认 3 秒
     * retryTimesWhenSendFailed：消息发送失败重试次数，默认 2 次
     */
    @Data
    @ConfigurationProperties(prefix = "rocketmq.producer")
    public static class RocketMqProducerProperties{
        private String namesrvAddr = "localhost:9876";

        private String groupName;

        private Integer maxMessageSize = 1024 * 1024 * 4; // 4M

        private Integer sendMsgTimeout = 3000;

        private Integer retryTimesWhenSendFailed = 2;

    }

    @Data
    @ConfigurationProperties(prefix = "rocketmq.consumer")
    public static class RocketMqConsumerProperties{
        private String namesrvAddr = "localhost:9876";

        private String groupName;

        private Integer consumeThreadMin = 20;

        private Integer consumeThreadMax = 20;

        private String topic;

        private Integer consumeMessageBatchMaxSize = 1;

    }
}

```

修改配置文件application.yml：

```yaml
rocketmq:
  producer:
    group-name: group1
  consumer:
    group-name: group1
    topic: test
```

##### 4.测试代码

```java
@RestController
@RequestMapping("/rocket")
public class RocketMqController {

    @Autowired
    DefaultMQProducer producer;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @GetMapping("/send/{msg}")
    public String sendMessage(@PathVariable String msg) throws Exception {
        Message message = new Message(topic, "tag", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(message);
        return result.getSendStatus().name();
    }



}
```



### 4.8 邮件发送

Spring Boot提供了`JavaMailSender`接口的自动配置类。方便实现邮件发送功能。配置类源码：

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
class MailSenderPropertiesConfiguration {

	@Bean
	@ConditionalOnMissingBean(JavaMailSender.class)
	JavaMailSenderImpl mailSender(MailProperties properties) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		applyProperties(properties, sender);
		return sender;
	}

	private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
		if (!properties.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(properties.getProperties()));
		}
	}

	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}

}
```



#### 4.8.1 导入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
```

#### 4.8.2 配置邮件服务器

`application.yml`

```yaml
spring:
# java mail 配置
  mail:
    host: # 邮件服务器 如smtp.aliyun.com
    username: # 发送方邮件地址，如zulongli@aliyun.com
    password: # 上述邮件密码
    port: 465 # 邮件服务器端口
    properties:	# 其他配置信息
      mail.smtp.connectiontimeout: 5000
      mail.smtp.timeout: 3000
      mail.smtp.writetimeout: 5000
      mail.smtp.socketFactory.class: javax.net.ssl.SSLSocketFactory
      mail.smtp.socketFactory.port: 465
      mail.smtp.port: 465
```

#### 4.8.3 编码测试

```java
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @GetMapping("/send/{to}/{msg}")
    public String sendMail(@PathVariable String to, @PathVariable String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("Spring Boot Mail Test");
        message.setSentDate(new Date());
        message.setText(msg);
        message.setTo(to);

        mailSender.send(message);

        return "Send successfully.";
    }

}

```



### 4.9 Quartz集成

#### 4.9.1 导入依赖和配置

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-quartz</artifactId>
        </dependency>
```

```yaml
spring:
  # quartz
  quartz:
    job-store-type: jdbc	# 任务持久化类型，默认是in-memory
    jdbc:
      initialize-schema: always	# 设置应用启动时自动初始化数据库表结构
      schema: # 自定义初始化脚本

```

程序启动后，就会自动创建数据库表保存用于存储定时任务信息。

#### 4.9.2 使用独立的数据源

##### 1.多数据源配置

```yaml
spring:
  datasource:	# 默认数据源
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql://localhost:3306/myproject?serverTimezone=Asia/Shanghai
    username: root
    password: root

    quartz:	# 任务数据源
      type: com.zaxxer.hikari.HikariDataSource
      jdbc-url: jdbc:mysql://localhost:3306/quartz?serverTimezone=Asia/Shanghai
      username: root
      password: root
```


##### 2.配置类

```java
@Configuration
public class MyDataSourceConfig {

    @Autowired
    DataSourceProperties properties;	// 读取yml配置的属性类

    @Bean
    @Primary
    public DataSource dataSource() {
        return properties.initializeDataSourceBuilder().build();
    }

    @ConfigurationProperties("spring.datasource.quartz")
    @QuartzDataSource	// 该注解表明这是用于quartz的数据源
    @Bean
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

}
```



### 4.10 测试Testing

#### 4.10.1 依赖导入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <!--使用JUnit 5时，排除Junit 4的支持-->
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```



#### 4.10.2 普通测试

```java
@SpringBootTest
class HelloExampleTest {


    @Test
    void simpleTest() {
        Assertions.assertTrue(true);
    }
}
```

`@SpringBootTest`注解会通过`SpringBootApplication`类和默认配置创建`ApplicationContext`容器，从而可以使用依赖注入功能。



#### 4.10.3 Mock测试

Spring Boot Test 默认不启动Web容器，可以使用MockMvc来测试Web请求和响应。需要通过`@AutoConfigureMockMvc`注解开启MockMvc自动配置。

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class HelloExampleTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void mockTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello TCL!"));
    }
}
```



### 4.11 自定义starter

通常一个starter包含以下两个部分：

1. `autoconfigure` 模块：包含自动配置代码
2. `starter`模块：为`autoconfigure`模块提供依赖，以及其他有用的依赖库

当功能比较简单时，可以合二为一。

#### 1.创建Maven工程

命名为 **acme-sprint-boot-starter**。

#### 2.导入相关依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.tcl</groupId>
    <artifactId>acme-spring-boot-starter</artifactId>
    <version>0.0.1</version>
    <name>acme-sprint-boot-starter</name>
    <description>Demo Starter</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure-processor</artifactId>
            <optional>true</optional>
        </dependency>

    </dependencies>


</project>

```

#### 3.编写自动配置类

1. Properties属性类

   ```java
   @ConfigurationProperties(prefix = "acme")
   public class AcmeProperties {
   
       private String name;
       private boolean enable;
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public boolean isEnable() {
           return enable;
       }
   
       public void setEnable(boolean enable) {
           this.enable = enable;
       }
   }
   
   ```

   

2. 自动配置类

   ```java
   @Configuration
   @EnableConfigurationProperties(value = AcmeProperties.class)
   public class AcmeAutoConfiguration {
   
       final
       AcmeProperties properties;
   
       public AcmeAutoConfiguration(AcmeProperties properties) {
           this.properties = properties;
       }
   
       @Bean
       @ConditionalOnMissingBean
       public Acme acme() {
           Acme acme = new Acme();
   
           acme.setName(properties.getName());
           acme.setEnable(properties.isEnable());
           return acme;
       }
   }
   ```

   

3. 功能实体类

   ```java
   public class Acme {
       private String name;
       private boolean enable;
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public boolean isEnable() {
           return enable;
       }
   
       public void setEnable(boolean enable) {
           this.enable = enable;
       }
   }
   ```

#### 4.创建`spring.factories`

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.tcl.acme.spring.boot.autoconfigure.AcmeAutoConfiguration
```

#### 5.打包安装

```bash
$ mvn clean install
```

执行该命令后，jar将被安装到本地Maven仓库中，其他项目可以通过以下方式引用：

```xml
        <dependency>
            <groupId>com.tcl</groupId>
            <artifactId>acme-spring-boot-starter</artifactId>
            <version>0.0.1</version>
        </dependency>
```





## 5. Actuator监控

[官方文档](https://docs.spring.io/spring-boot/docs/2.2.9.RELEASE/reference/htmlsingle/#production-ready )

### 5.1 依赖导入

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
```

### 5.2 开启和暴露监控信息

```yaml
management:
  endpoints:
    enabled-by-default: true  # 开启所有监控功能，shutdown除外
    web:
      exposure:
        include: "*"  # 暴露所有HTTP访问路径
```

所有可用的监控功能如下，可以通过management.endpoint.ID.enabled=true/false开启或者关闭指定功能。

| ID                 | Description                                                  |
| ------------------ | ------------------------------------------------------------ |
| `auditevents`      | Exposes audit events information for the current application. Requires an `AuditEventRepository` bean. |
| `beans`            | Displays a complete list of all the Spring beans in your application. |
| `caches`           | Exposes available caches.                                    |
| `conditions`       | Shows the conditions that were evaluated on configuration and auto-configuration classes and the reasons why they did or did not match. |
| `configprops`      | Displays a collated list of all `@ConfigurationProperties`.  |
| `env`              | Exposes properties from Spring’s `ConfigurableEnvironment`.  |
| `flyway`           | Shows any Flyway database migrations that have been applied. Requires one or more `Flyway` beans. |
| `health`           | Shows application health information.                        |
| `httptrace`        | Displays HTTP trace information (by default, the last 100 HTTP request-response exchanges). Requires an `HttpTraceRepository` bean. |
| `info`             | Displays arbitrary application info.                         |
| `integrationgraph` | Shows the Spring Integration graph. Requires a dependency on `spring-integration-core`. |
| `loggers`          | Shows and modifies the configuration of loggers in the application. |
| `liquibase`        | Shows any Liquibase database migrations that have been applied. Requires one or more `Liquibase` beans. |
| `metrics`          | Shows ‘metrics’ information for the current application.     |
| `mappings`         | Displays a collated list of all `@RequestMapping` paths.     |
| `scheduledtasks`   | Displays the scheduled tasks in your application.            |
| `sessions`         | Allows retrieval and deletion of user sessions from a Spring Session-backed session store. Requires a Servlet-based web application using Spring Session. |
| `shutdown`         | Lets the application be gracefully shutdown. Disabled by default. |
| `threaddump`       | Performs a thread dump.                                      |





### 5.3 HTTP监控访问

只有同时开启和暴露了相关监控，才能通过`/actuator/ID`进行访问。



#### 5.3.1 自定义Endpoint

只要在`@Bean，@Component`类上添加`@Endpoint，@WebEndpoint`等注解，所有标注`@ReadOperation，@WriteOperation，@DeleteOperation`注解的方法都可以作为endpoint暴露。分别对应GET，POST，DELETE请求`/actuator/my-endpoints`。

```java
@Component
@WebEndpoint(id="my-endpoints", enableByDefault = true)
public class MyEndpoints {

    @ReadOperation
    public String readOps() {
        return "read ops";
    }


    @WriteOperation
    public String writeOps() {
        return "write ops";
    }

    @DeleteOperation
    public String deleteOps() {
        return "delete ops";
    }
}

```



#### 5.3.2 更多配置

##### 1.修改路径前缀

```yaml
management:
  endpoints:
    web:
      base-path: /management
```

这样，Web访问就从原来的 **/actuator/*** 变成了 **/management/***。

##### 2.定义主机和端口

一般情况下，监控端口只能被内部访问，与服务端口分离以确保安全

```yaml
 management:
     server:
        port: 8000
        address: 127.0.0.1
```

关闭ssl

```yaml
 management:
     server:
		ssl:
      		enabled: false
```



































