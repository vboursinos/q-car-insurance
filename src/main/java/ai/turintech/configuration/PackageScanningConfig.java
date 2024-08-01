package ai.turintech.configuration;

import ai.turintech.database.DatabasePackage;
import ai.turintech.service.ServicePackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackageClasses = {
                DatabasePackage.class,
                ServicePackage.class
        })
public class PackageScanningConfig {
}
