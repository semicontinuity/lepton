package gearbox.swing.adapter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.swing.*;

@Resource
public class SimpleFrame extends JFrame {

    @PostConstruct
    public void start () throws InterruptedException {
        pack();
        setVisible(true);
    }

    @PreDestroy
    public void stop () {
        setVisible(false);
        dispose();
    }
}
