package ai.turintech.database;

import ai.turintech.executor.ScriptExecutor;
import com.kx.c;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface CustomerManager {
    public void main();
    public void initializeDatabase();
    public void printAllCustomers();
}