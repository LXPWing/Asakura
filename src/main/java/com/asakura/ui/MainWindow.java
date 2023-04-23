package com.asakura.ui;

import cn.hutool.core.collection.CollUtil;
import com.asakura.common.util.ClassUtils;
import com.asakura.listener.MainWindowInitializedEventListener;
import com.intellij.util.PathUtil;

import javax.swing.*;
import java.util.Set;

/**
 * @author anlingyi
 * @date 2020/5/26
 */
public class MainWindow {
    private JPanel rootPanel;
    private JTextPane console;
    private JTextArea contentArea;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel contentPanel;
    private JScrollPane consoleScroll;
    private JPanel leftTopPanel;

    private static final MainWindow MAIN_WINDOW;

    static {
        MAIN_WINDOW = new MainWindow();
        MAIN_WINDOW.afterInit();
    }

    private MainWindow() {

    }

    private void afterInit() {
        Set<Class<?>> initClasses = ClassUtils.scanSubClass(PathUtil.getJarPathForClass(MainWindow.class), null,
                MainWindowInitializedEventListener.class);

        if (CollUtil.isNotEmpty(initClasses)) {
            try {
                for (Class<?> initClass : initClasses) {
                    MainWindowInitializedEventListener obj = (MainWindowInitializedEventListener) initClass
                            .getDeclaredConstructor().newInstance();
                    obj.afterInit(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Command.HELP.exec();
    }

    public static MainWindow getInstance() {
        return MAIN_WINDOW;
    }

    public JPanel getMainPanel() {
        return rootPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public JTextArea getContentArea() {
        return contentArea;
    }

    public JPanel getLeftTopPanel() {
        return leftTopPanel;
    }

    public JTextPane getConsoleTextPane() {
        return console;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public JScrollPane getConsoleScrollPane() {
        return consoleScroll;
    }

}
