/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author shram
 */
public class UpdateListUI {

    public UpdateListUI(JList chemsListUI) {

        try {
            ChemIO chemio = new ChemIO();
            ArrayList<Chemical> chemsList = chemio.readChem();
            DefaultListModel listModel = (DefaultListModel) chemsListUI.getModel();
            listModel.clear();
            chemsList.forEach(chemical -> {
                listModel.addElement(chemical.getName());
            });
        } catch (Exception e) {
        }
    }

}
