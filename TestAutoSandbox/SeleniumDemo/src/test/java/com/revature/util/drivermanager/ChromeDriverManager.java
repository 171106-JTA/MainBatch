package com.revature.util.drivermanager;

import java.io.File;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import com.revature.util.os.ExecutableMapper;

public class ChromeDriverManager extends DriverManager {

    private ChromeDriverService chService;
    private ChromeOptions options = new ChromeOptions();
    
    public void addOptions(List<String> arguments) {
    		this.options.addArguments(arguments);
    }

    @Override
    public void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(ExecutableMapper.getExecutable(dir + "chromedriver")))
                    .usingAnyFreePort()
                    .build();
                chService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void createDriver() {
        options.addArguments("test-type");
        driver = new ChromeDriver(chService, options);
    }
		
}
