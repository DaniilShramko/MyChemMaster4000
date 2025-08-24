/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author shram
 */
public class IngredientsComboBox extends JComboBox {

    public IngredientsComboBox() throws Exception {
        ChemIO chemio = new ChemIO();
        ArrayList<Chemical> list = chemio.readChem();
        list.forEach(chemical -> {
            addItem(chemical.getName());
        });
    }

}
