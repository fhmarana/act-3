package com.gabriel.draw;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.view.DrawingMenuBar;
import com.gabriel.draw.service.DeawingCommandAppService;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.view.DrawingToolBar;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.draw.view.DrawingFrame;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> {
            AppService drawingAppService = new DrawingAppService();
            AppService appService = new DeawingCommandAppService(drawingAppService);

            ActionController actionController = new ActionController(appService);

            DrawingFrame drawingFrame = new DrawingFrame(appService);
            DrawingMenuBar drawingMenuBar = new DrawingMenuBar(actionController);
            DrawingToolBar drawingToolBar = new DrawingToolBar(actionController);
            DrawingView drawingView = new DrawingView(appService);

            DrawingController drawingController = new DrawingController(appService, drawingView, actionController);

            drawingFrame.setContentPane(drawingView);
            drawingFrame.setJMenuBar(drawingMenuBar);
            drawingFrame.getContentPane().add(drawingToolBar, BorderLayout.PAGE_START);

            drawingFrame.setTitle("Drawing App");
            drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            drawingFrame.setSize(1000, 700);
            drawingFrame.setLocationRelativeTo(null);

            actionController.updateUIState();

            drawingFrame.setVisible(true);
        });
    }
}