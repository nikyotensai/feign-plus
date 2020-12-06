#简介
[spring-cloud-feign](https://github.com/spring-cloud/spring-cloud-openfeign)的增强工具，简化了feign的使用，

#解决的问题
1. feign的fallback使用繁琐。通过创建代理简化操作，可以使用提供的PreHandler和PostHandler两个注解来简化流程。
- PreHandler方法fallback返回前的处理，可以有多个。一般用于记录异常。
- PostHandler控制返回，只能有一个。可以指定fallback后的返回，类型需要和正常返回的类型相同。
1. feign的name重复问题。feign的官方设计的时候，是基于同一个远程服务只需要一个FeignClient就够了的设计理念设计的，虽然后期因用户反馈增加了id，但还是会有一些问题。
feign-plus解决了这类问题。

#maven依赖
 ```xml
        <dependency>
            <groupId>com.github.nikyotensai</groupId>
            <artifactId>feign-plus-all</artifactId>
            <version>2.0.0</version>
        </dependency>
``` 

#DEMO
[feign-plus-example](https://github.com/nikyotensai/feign-plus-example)