/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chemmastergui;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author shram
 */
public class ChemIO {

    public void ChemIO() {
    }

    private ArrayList<Chemical> chemsRead = new ArrayList<>();

    public void writeChem(Chemical chemical) throws Exception {

        if (getChem(chemical.getName(), readChem()) == null) {
            ArrayList<Chemical> temp = readChem();
            temp.add(chemical);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ChemsList.dat"));
            out.writeObject(temp);
            System.out.println("Chem Added");
        } else {
            System.out.println("Chem Already Exists");
        }

    }

    public void deleteChem(String name) throws Exception {
        ArrayList<Chemical> temp = readChem();
        temp.remove(getChem(name, temp));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ChemsList.dat"));
        out.writeObject(temp);
    }

    public ArrayList readChem() throws Exception {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("ChemsList.dat"));
            chemsRead = (ArrayList<Chemical>) in.readObject();
        } catch (IOException ioe) {
            System.out.println("Read err chemio " + ioe);
        }
        return chemsRead;
    }

    public Chemical getChem(String name, ArrayList<Chemical> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }
}
