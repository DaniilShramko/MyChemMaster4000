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
public class RecipeComponentPanel extends MovableJpanel {

    public RecipeComponentPanel(Chemical chemical, Float wantedAmount, JPanel parent, Point startpoint, Dimension parentSize, ArrayList<JPanel> childrenList) {

        HashMap<Chemical, Float> recipe = chemical.getRecipe(wantedAmount);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());

        //Chem data panel
        JPanel data = new JPanel();
        JLabel name = new JLabel("Name: " + chemical.getName());
        JLabel temperature = new JLabel("Temperature: " + chemical.getTemperature());

        float[] hsbValues = new float[3];
        Color color = chemical.getColor();
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);
        //Setting up contrast name color
        if (hsbValues[2] < 0.5f) {
            name.setForeground(Color.white);
            temperature.setForeground(Color.white);
        }

        data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
        data.add(name);
        data.add(temperature);
        data.setOpaque(false);

        //Adding close button
        JPanel close = new JPanel();
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(l -> {
            childrenList.remove(this);
            getParent().remove(this);
            parent.updateUI();

        });
        close.add(closeButton);
        close.setOpaque(false);
        
        //Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(data, BorderLayout.CENTER);
        headerPanel.add(close, BorderLayout.EAST);
        headerPanel.setBackground(chemical.getColor());
        headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        add(headerPanel, BorderLayout.NORTH);
        
        //Recipe panel
        JPanel recipePanel = new JPanel();
        recipePanel.setLayout(new GridLayout(0, 2, 0, 0));
        //Algorithm for filling recipePanel
        recipe.forEach((chem, amount) -> {
            JButton tempButton = new JButton("Show Recipe");
            tempButton.addActionListener(l -> {
                if (childrenList.isEmpty()) {
                    RecipeComponentPanel rcp = new RecipeComponentPanel(chem, amount, parent, getLocation(), getSize(), childrenList);
                    childrenList.add(rcp);
                    getParent().add(rcp);
                    parent.updateUI();
                } else {
                    RecipeComponentPanel rcp = new RecipeComponentPanel(chem, amount, parent, childrenList.getLast().getLocation(), childrenList.getLast().getSize(), childrenList);
                    childrenList.add(rcp);
                    getParent().add(rcp);
                    parent.updateUI();
                }

            });
            tempButton.setMargin(new Insets(-5, 0, -5, 0));
            JLabel tempLabel = new JLabel(chem.getName() + " " + amount);
            recipePanel.add(tempLabel);
            recipePanel.add(tempButton);
            if (chem.getRecipe(10f).isEmpty()) {
                tempButton.setEnabled(false);
            }

        });
        
        //Setting up bounds depending on number of components
        int rcpVerticalSize = headerPanel.getPreferredSize().height + recipePanel.getPreferredSize().height;
        setBounds((int) startpoint.getX(), (int) startpoint.getY() + parentSize.height, 250, rcpVerticalSize);
        add(recipePanel, BorderLayout.CENTER);
        updateUI();

    }

}
