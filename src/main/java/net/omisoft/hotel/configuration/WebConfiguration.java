package net.omisoft.hotel.configuration;

import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import net.omisoft.ApplicationConstants;
import net.omisoft.hotel.interceptor.DateRangeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final DateRangeInterceptor dateRangeInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dateRangeInterceptor)
                .addPathPatterns(ApplicationConstants.API_V1_BASE_PATH + "reservations");
    }

}
