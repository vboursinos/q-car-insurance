package ai.turintech.service;

import ai.turintech.database.CustomerManager;
import ai.turintech.database.ProductManager;
import ai.turintech.reports.ModelCustomer;
import ai.turintech.reports.ProductDetailsPerCustomer;
import ai.turintech.reports.ReportSummary;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartAppServiceImpl implements StartAppService {

  @Autowired private CustomerManager customerManager;

  @Autowired private ProductManager productManager;

  @Autowired private ProductDetailsPerCustomer productDetailsPerCustomer;

  @Autowired private List<ModelCustomer> modelCustomers;

  @Autowired private ReportSummary reportSummary;

  private static final Logger logger = Logger.getLogger(StartAppServiceImpl.class.getName());

  public void startApp() {
    logger.info("Starting the application");
    customerManager.createTable();
    productManager.createTable();
    productDetailsPerCustomer.createReport();
    for (ModelCustomer modelCustomer : modelCustomers) {
      modelCustomer.createModelReport();
      modelCustomer.createModelYoungCustomerReport();
      modelCustomer.createModelAvgPriceReport();
    }
    reportSummary.createReportSummary();
  }
}
