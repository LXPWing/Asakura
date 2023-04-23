package com.asakura.factory;

import cn.hutool.core.thread.GlobalThreadPool;
import com.asakura.common.util.ThreadUtils;
import com.asakura.ui.MainWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.AncestorListenerAdapter;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.AncestorEvent;

/**
 * @author anlingyi
 * @date 2020/5/26
 */
public class MainWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JPanel mainPanel = MainWindow.getInstance().getMainPanel();
        mainPanel.addAncestorListener(new AncestorListenerAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                GlobalThreadPool.execute(() -> {
                    ThreadUtils.spinMoment(800);
                    //InputAction.restCursor();
                });
            }
        });

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

}
