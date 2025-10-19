package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.*;

public class DrawingToolBar extends JToolBar {
    
    public DrawingToolBar(ActionController actionController) {
        super("Drawing Tools");
        setFloatable(false);
        setRollover(true);
        
        JLabel logoLabel = new JLabel("✏️ Drawing App");
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(logoLabel);
        
        addSeparator();
        
        JButton undoButton = createToolbarButton("↶", ActionCommand.UNDO, "Undo last action", actionController);
        undoButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(undoButton);
        
        JButton redoButton = createToolbarButton("↷", ActionCommand.REDO, "Redo last undone action", actionController);
        redoButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(redoButton);
        
        addSeparator();
        
        ButtonGroup shapeGroup = new ButtonGroup();
        
        JToggleButton lineButton = createToggleButton("📏", ActionCommand.LINE, "Draw lines", actionController);
        shapeGroup.add(lineButton);
        add(lineButton);
        
        JToggleButton rectButton = createToggleButton("⬜", ActionCommand.RECT, "Draw rectangles", actionController);
        shapeGroup.add(rectButton);
        add(rectButton);
        
        JToggleButton ellipseButton = createToggleButton("⭕", ActionCommand.ELLIPSE, "Draw ellipses", actionController);
        ellipseButton.setSelected(true); // Default selection
        shapeGroup.add(ellipseButton);
        add(ellipseButton);
        
        addSeparator();
        
        JButton colorButton = createToolbarButton("🎨", ActionCommand.SET_COLOR, "Choose drawing color", actionController);
        add(colorButton);
        
        addSeparator();
        
        JButton clearButton = createToolbarButton("🗑️", ActionCommand.CLEAR_ALL, "Clear all shapes", actionController);
        add(clearButton);
        
        add(Box.createHorizontalGlue());
        
        JButton exitButton = createToolbarButton("❌", ActionCommand.EXIT, "Exit application", actionController);
        add(exitButton);
    }
    
    private JButton createToolbarButton(String icon, String actionCommand, String tooltip, ActionController actionController) {
        JButton button = new JButton(icon);
        button.setActionCommand(actionCommand);
        button.setToolTipText(tooltip);
        button.addActionListener(actionController);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(40, 40));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        actionController.registerToolbarButton(button);
        return button;
    }
    
    private JToggleButton createToggleButton(String icon, String actionCommand, String tooltip, ActionController actionController) {
        JToggleButton button = new JToggleButton(icon);
        button.setActionCommand(actionCommand);
        button.setToolTipText(tooltip);
        button.addActionListener(actionController);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(40, 40));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        actionController.registerToolbarButton(button);
        return button;
    }
}