/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author shram
 */
public class RecipieComponentPanel extends JPanel {

    private int mouseX, mouseY; // Stores the initial mouse coordinates relative to the panel

    public RecipieComponentPanel(Chemical chemical, Float wantedAmount, JPanel parent, Point startpoint, Dimension parentSize, ArrayList<JPanel> childrenList) {
        setName(chemical.getName());
        HashMap<Chemical, Float> recipie = chemical.getRecipe(wantedAmount);
        JLabel name = new JLabel("Name: " + chemical.getName());
        JLabel temperature = new JLabel("Temperature: " + chemical.getTemperature());
        float[] hsbValues = new float[3];
        Color color = chemical.getColor();
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);

        if (hsbValues[2] < 0.5f) {
            name.setForeground(Color.white);
            temperature.setForeground(Color.white);
        }
        // Set a preferred size for the panel

        // Set a background color for visibility
        // Add a border for better visual separation
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        //headerPanel.setPreferredSize(new Dimension(15, 32));
        headerPanel.setLayout(new BorderLayout());
        JPanel data = new JPanel();
        data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
        JPanel close = new JPanel();
        JButton closeButton = new JButton("X");

        closeButton.addActionListener(l -> {
            childrenList.remove(this);
            getParent().remove(this);
            parent.updateUI();

        });
        close.add(closeButton);
        data.add(name);
        data.add(temperature);
        data.setOpaque(false);
        close.setOpaque(false);

        headerPanel.add(data, BorderLayout.CENTER);
        headerPanel.add(close, BorderLayout.EAST);
        headerPanel.setBackground(chemical.getColor());
        headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 2, 0, 0));

        recipie.forEach((chem, amount) -> {
            JButton tempButton = new JButton("Show Recipie");
            tempButton.addActionListener(l -> {
                if (childrenList.isEmpty()) {
                    RecipieComponentPanel rcp = new RecipieComponentPanel(chem, amount, parent, getLocation(), getSize(), childrenList);
                    childrenList.add(rcp);
                    getParent().add(rcp);
                    parent.updateUI();
                } else {
                    RecipieComponentPanel rcp = new RecipieComponentPanel(chem, amount, parent, childrenList.getLast().getLocation(), childrenList.getLast().getSize(), childrenList);
                    childrenList.add(rcp);
                    getParent().add(rcp);
                    parent.updateUI();
                }

            });
            tempButton.setMargin(new Insets(-5, 0, -5, 0));
            JLabel tempLabel = new JLabel(chem.getName() + " " + amount);
            centerPanel.add(tempLabel);
            centerPanel.add(tempButton);
            if (chem.getRecipe(10f).isEmpty()) {
                tempButton.setEnabled(false);
            }

        });

        int rcpVerticalSize = headerPanel.getPreferredSize().height + centerPanel.getPreferredSize().height;
        setBounds((int) startpoint.getX(), (int) startpoint.getY() + parentSize.height, 250, rcpVerticalSize);
        add(centerPanel, BorderLayout.CENTER);
        updateUI();

        // Add MouseListener for mouse pressed event
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Get the initial mouse coordinates relative to the panel
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        // Add MouseMotionListener for mouse dragged event
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Get the parent container
                Container parent = getParent();
                if (parent != null) {
                    // Calculate new panel location based on current mouse position and initial offset
                    int newX = e.getXOnScreen() - parent.getLocationOnScreen().x - mouseX;
                    int newY = e.getYOnScreen() - parent.getLocationOnScreen().y - mouseY;

                    // Set the new location of the panel
                    setLocation(newX, newY);
                }
            }
        });
    }

}
