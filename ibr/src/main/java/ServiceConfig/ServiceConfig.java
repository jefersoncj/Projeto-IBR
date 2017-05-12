package ServiceConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.abia.ibr.service.CadastroItemService;

@Configuration
@ComponentScan(basePackageClasses = CadastroItemService.class)
public class ServiceConfig {

}