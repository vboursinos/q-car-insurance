package ai.turintech.executor;

import ai.turintech.database.CustomerManager;
import com.kx.c;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class ScriptExecutorImpl implements ScriptExecutor {
  private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());

  public void executeQScript(String qScript, String kdbHost, String kdbPort) {
    c con = null;
    try {
      con = new c(kdbHost, Integer.parseInt(kdbPort));
      logger.info("Executing Q script...");
      logger.info(qScript);
      con.k(qScript);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to execute Q script", e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Failed to close kdb+ connection", e);
        }
      }
    }
  }

  public Object executeQScriptWithReturn(String qScript, String kdbHost, String kdbPort) {
    c con = null;
    try {
      con = new c(kdbHost, Integer.parseInt(kdbPort));
      logger.info("Executing Q script...");
      logger.info(qScript);
      return con.k(qScript);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to execute Q script", e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Failed to close kdb+ connection", e);
        }
      }
    }
    return null;
  }
}
