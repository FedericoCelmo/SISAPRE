/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Federico
 */
public class ControlBD {
    // esta clase es para controlar los tipos de archivo de la base de datos
    JFileChooser selector = new JFileChooser(System.getProperty("user.dir"));
    Filtros filtrotxt;
    Filtros filtrolist;
    
    public ControlBD() {
        filtrolist = new Filtros("sql", "Archivos De SQL");
        filtrotxt = new Filtros("txt", "Archivos De Texto");
        selector.setAcceptAllFileFilterUsed(false);
        selector.addChoosableFileFilter(filtrolist);
        selector.addChoosableFileFilter(filtrotxt);
    }
    public void ResetFiltros(){
        selector.resetChoosableFileFilters();
        selector.setAcceptAllFileFilterUsed(true);       
    }

    public File Abrir(Component parent) {
        selector.setDialogTitle("Abrir Query de Base de Datos");
        File f;
        int returnVal = selector.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = selector.getSelectedFile();
            f = file;           
        } else {
            return null;
        }
        return f;
    }

    public File guardar(Component parent) {
        selector.setDialogTitle("Guardar Base de Datos");
        File g;
        int returnVal = selector.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = selector.getSelectedFile();            
            g = file;
        } else {     
            return null;
        }
        return g;
    }   
}
