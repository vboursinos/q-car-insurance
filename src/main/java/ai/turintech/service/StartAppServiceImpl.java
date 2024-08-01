package ai.turintech.service;

import ai.turintech.database.CustomerManager;
import ai.turintech.database.CustomerManagerImpl;
import ai.turintech.database.ProductManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class StartAppServiceImpl implements StartAppService{

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ProductManager productManager;

    private static final Logger logger = Logger.getLogger(StartAppServiceImpl.class.getName());


    public void startApp() {
        logger.info("Starting the application");
        customerManager.createTable();
        productManager.createTable();
    }

}
