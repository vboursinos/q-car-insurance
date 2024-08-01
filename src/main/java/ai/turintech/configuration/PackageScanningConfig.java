package ai.turintech.configuration;

import ai.turintech.database.DatabasePackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackageClasses = {
                DatabasePackage.class
        })
public class PackageScanningConfig {
}
