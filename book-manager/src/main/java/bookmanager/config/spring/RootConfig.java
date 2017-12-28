package bookmanager.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by dela on 11/22/17.
 */

//配置加载非Web组件的Bean的ContextLoaderListener应用上下文的JavaConfig
@Configuration
@ComponentScan(basePackages = {"bookmanager"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class RootConfig {
}
