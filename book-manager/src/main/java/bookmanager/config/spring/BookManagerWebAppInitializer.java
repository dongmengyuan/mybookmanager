package bookmanager.config.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by dela on 11/22/17.
 */

//通过AbstractAnnotationConfigDispatcherServletInitializer来配置DispatcherServlet
//这个类是web.xml配置的替代方案
public class BookManagerWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    //创建ContextLoaderListener应用上下文
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }

    //创建DispatcherServlet应用上下文
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    //将DispatcherServlet映射到/
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
