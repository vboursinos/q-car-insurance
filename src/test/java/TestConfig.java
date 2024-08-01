import ai.turintech.configuration.PackageScanningConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(
    value = {
      PackageScanningConfig.class
    })
public class TestConfig {
}
