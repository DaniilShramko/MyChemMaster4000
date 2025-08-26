/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author shram
 */
public class ChemicalListUpdater {

    ChemIO chemio = new ChemIO();

    public ChemicalListUpdater() {

    }

    public DefaultListModel update(JList<String> jList) {
        DefaultListModel listModel = (DefaultListModel) jList.getModel();
        listModel.clear();
        try {
            ArrayList<Chemical> chemsList = chemio.readChem();
            chemsList.forEach(chemical -> {
                listModel.addElement(chemical.getName());
            });
        } catch (Exception e) {
            System.out.println("Err in ChemicalListUpdater " + e);

        }
        return listModel;
    }

    public DefaultListModel searchInList(String wanted, JList<String> jList) {
        DefaultListModel listModel = (DefaultListModel) jList.getModel();

        
        Pattern p = Pattern.compile(wanted,Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        if (wanted.isEmpty()) {
            return update(jList);
        }
        listModel.clear();
        try {
            ArrayList<Chemical> chemsList = chemio.readChem();
            for (Chemical chemical : chemsList) {
                Matcher m = p.matcher(chemical.getName());
                if (m.find()) {
                    listModel.addElement(chemical.getName());
                }
            }

        } catch (Exception e) {
            System.out.println("Err in ChemicalListUpdater " + e);

        }

        return listModel;
    }
}
