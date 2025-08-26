/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


/**
 *
 * @author shram
 */

import javax.swing.JPanel;

public class MovableJpanel extends JPanel {
    private int mouseX, mouseY;

    MovableJpanel() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Container parent = getParent();
                if (parent != null) {

                    int newX = e.getXOnScreen() - parent.getLocationOnScreen().x - mouseX;
                    int newY = e.getYOnScreen() - parent.getLocationOnScreen().y - mouseY;

                    setLocation(newX, newY);
                }
            }
        });
    }
}

