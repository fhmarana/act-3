package com.gabriel.draw.controller;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class DrawingController implements MouseListener, MouseMotionListener {
    private Point startPoint;
    private Point endPoint;
    private final DrawingView drawingView;
    private Shape currentShape;
    private final AppService appService;
    private final ActionController actionController;
    
    public DrawingController(AppService appService, DrawingView drawingView, ActionController actionController) {
        this.appService = appService;
        this.drawingView = drawingView;
        this.actionController = actionController;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.isShiftDown()) {
            createShapeAtPoint(e.getPoint());
        }
    }
    
    private void createShapeAtPoint(Point point) {
        Point endPoint = new Point(point.x + 50, point.y + 50);
        
        switch (appService.getShapeMode()) {
            case Line:
                currentShape = new Line(point, endPoint);
                break;
            case Rectangle:
                currentShape = new Rectangle(point, endPoint);
                break;
            case Ellipse:
                currentShape = new Ellipse(point, endPoint);
                break;
            default:
                return;
        }
        
        currentShape.setColor(appService.getColor());
        appService.create(currentShape);
        actionController.updateUIState();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.Idle && !e.isShiftDown()) {
            startPoint = e.getPoint();
            
            switch (appService.getShapeMode()) {
                case Line:
                    currentShape = new Line(startPoint, startPoint);
                    break;
                case Rectangle:
                    currentShape = new Rectangle(startPoint, startPoint);
                    break;
                case Ellipse:
                    currentShape = new Ellipse(startPoint, startPoint);
                    break;
                default:
                    return;
            }
            
            currentShape.setColor(appService.getColor());
            drawingView.setPreviewShape(currentShape);
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed && !e.isShiftDown()) {
            endPoint = e.getPoint();
            
            drawingView.clearPreviewShape();
            
            appService.scale(currentShape, endPoint);
            appService.create(currentShape);
            appService.setDrawMode(DrawMode.Idle);
            actionController.updateUIState();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateCursor();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawingView.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed && !e.isShiftDown()) {
            endPoint = e.getPoint();
            
            appService.scale(currentShape, endPoint);
            drawingView.setPreviewShape(currentShape);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateCursor();
    }
    
    private void updateCursor() {
        Cursor cursor;
        switch (appService.getShapeMode()) {
            case Line:
                cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                break;
            case Rectangle:
            case Ellipse:
                cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                break;
            default:
                cursor = Cursor.getDefaultCursor();
        }
        drawingView.setCursor(cursor);
    }
    
    private void updateStatusInfo() {
        if (startPoint != null && endPoint != null) {
            int width = Math.abs(endPoint.x - startPoint.x);
            int height = Math.abs(endPoint.y - startPoint.y);
            
            String info = String.format("Size: %dx%d, Start: (%d,%d), End: (%d,%d)",
                width, height, startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            
            drawingView.setToolTipText(info);
        }
    }
}