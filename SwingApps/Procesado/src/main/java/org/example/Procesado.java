/*
 * Created by JFormDesigner on Wed Oct 26 12:20:21 CEST 2022
 */

package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class Procesado extends JFrame {

    public static void main(String[] args) {
        Procesado procesado = new Procesado();

        procesado.setVisible(true);
    }

    public Procesado() {
        initComponents();
    }

    private void BotonAcercaDe(ActionEvent e) {
        // TODO add your code here
        String message = "lo que quieras";
        String title = "Acerca de";
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.PLAIN_MESSAGE);
    }

    private void initComponents() {
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            menuBar1 = new JMenuBar();
            menu1 = new JMenu();
            menuItem1 = new JMenuItem();
            menuItem2 = new JMenuItem();
            menuItem3 = new JMenuItem();
            menu3 = new JMenu();
            button1 = new JButton();

            //======== this ========
            var contentPane = getContentPane();
            contentPane.setLayout(new GridLayout());

            //======== menuBar1 ========
            {
                menuBar1.setBorder(new LineBorder(new Color(0x414450), 1, true));

                //======== menu1 ========
                {
                    menu1.setText("Archivo");
                    menu1.setBackground(new Color(0x144050));

                    //---- menuItem1 ----
                    menuItem1.setText("text");
                    menu1.add(menuItem1);

                    //---- menuItem2 ----
                    menuItem2.setText("text");
                    menu1.add(menuItem2);

                    //---- menuItem3 ----
                    menuItem3.setText("text");
                    menu1.add(menuItem3);
                }
                menuBar1.add(menu1);

                //======== menu3 ========
                {
                    menu3.setText("Dise\u00f1o");
                }
                menuBar1.add(menu3);

                //---- button1 ----
                button1.setText("Acerca de");
                button1.setBorderPainted(false);
                button1.setContentAreaFilled(false);
                button1.addActionListener(e -> BotonAcercaDe(e));
                menuBar1.add(button1);
            }
            setJMenuBar(menuBar1);
            pack();
            setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenu menu3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
