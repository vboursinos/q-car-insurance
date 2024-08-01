package ai.turintech;

import ai.turintech.configuration.PackageScanningConfig;
import ai.turintech.database.CustomerManager;
import ai.turintech.database.CustomerManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(
        value = {
                PackageScanningConfig.class,
        })
public class QApplication {

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QApplication.class);
        ConfigurableApplicationContext context = app.run(args);
        CustomerManager customerManager =
                context.getBean(CustomerManagerImpl.class);
        customerManager.main();
        context.close();
    }
}
