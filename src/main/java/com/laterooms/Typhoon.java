package com.laterooms;

import org.apache.camel.spring.Main;

/**
 * Created by andrew on 17/04/2014.
 */
public class Typhoon {
    private Main main;

    public static void main(String[] args) throws Exception {
        Typhoon typhoon = new Typhoon();
        typhoon.boot();
    }

    private void boot() throws Exception {
        main = new Main();

        main.enableHangupSupport();
        main.setApplicationContextUri("src/main/webapp/WEB-INF/applicationContext.xml");

        System.out.println("Unleashing the typhoon. Use ctrl + c to end the storm.\n");
        main.run();
    }
}
