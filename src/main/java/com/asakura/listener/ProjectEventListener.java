package com.asakura.listener;

import cn.hutool.core.io.IoUtil;
import com.asakura.factory.MainWindowFactory;
import com.asakura.ui.MainWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.wm.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.InputStream;

public class ProjectEventListener implements ProjectManagerListener {

    private static final String WINDOW_ID = "AsakuraChat";

    @Override
    public void projectOpened(@NotNull Project project) {
        MainWindow.getInstance();
    }

    @Override
    public void projectClosed(@NotNull Project project) {
        IdeFrame[] allProjectFrames = WindowManager.getInstance().getAllProjectFrames();
        Project otherProject = allProjectFrames[0].getProject();
        if (otherProject != null) {
            ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(otherProject);
            toolWindowManager.invokeLater(() -> {
                ToolWindow toolWindow = toolWindowManager.getToolWindow(WINDOW_ID);
                if (toolWindow != null) {
                    try {
                        Icon icon = toolWindow.getIcon();
                        if (icon == null) {
                            InputStream inputStream = ProjectEventListener.class.getResourceAsStream("/images/logo.png");
                            icon = new ImageIcon(IoUtil.readBytes(inputStream));
                        }
                        RegisterToolWindowTask xeChat = RegisterToolWindowTask.notClosable(WINDOW_ID, icon);
                        toolWindow.remove();
                        toolWindow = toolWindowManager.registerToolWindow(xeChat);
                        new MainWindowFactory().createToolWindowContent(otherProject, toolWindow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
