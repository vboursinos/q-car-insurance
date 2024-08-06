package ai.turintech;

import ai.turintech.configuration.PackageScanningConfig;
import ai.turintech.service.StartAppService;
import ai.turintech.service.StartAppServiceImpl;
import java.io.IOException;
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
  public static void main(String[] args) throws IOException {
    SpringApplication app = new SpringApplication(QApplication.class);
    ConfigurableApplicationContext context = app.run(args);
    StartAppService startAppService = context.getBean(StartAppServiceImpl.class);
    startAppService.startApp();
    context.close();
  }
}
