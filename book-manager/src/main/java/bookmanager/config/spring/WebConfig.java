package bookmanager.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by dela on 11/22/17.
 */

//配置DispatcherServlet应用上下文的JavaConfig
//@EnableWebMvc注解开启Spring MVC
@Configuration
@EnableWebMvc
@ComponentScan("bookmanager.web") //启用组件扫描, 组件扫描只会扫描到这里设置的包及其子包
public class WebConfig extends WebMvcConfigurerAdapter {
    //配置ViewResolver视图解析器具体解析view名字的规则
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);

        return resolver;
    }

    //配置对静态资源的处理
    //通过调用DefaultServletHandlerConfigurer的enable()方法,
    //要求DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上,
    //而不是DispatcherServelt本身来处理这类请求.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
