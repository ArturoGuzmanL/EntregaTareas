/*
 * Created by JFormDesigner on Wed Oct 26 11:56:51 CEST 2022
 */

package org.example;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class ProcesadoArchivos extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ProcesadoArchivos ventana = new ProcesadoArchivos();
                ventana.setSize(new Dimension(410, 680));
                ventana.setMinimumSize(new Dimension(300, 590));

                ventana.setVisible(true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ProcesadoArchivos() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        frame1 = new JFrame();
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menu2 = new JMenu();
        button3 = new JButton();
        panel1 = new JPanel();
        label1 = new JLabel();

        //======== frame1 ========
        {
            var frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new GridLayout(3, 0));

            //======== menuBar1 ========
            {

                //======== menu1 ========
                {
                    menu1.setText("Archivo");
                    menu1.setBackground(new Color(0x414450));

                    //---- menuItem1 ----
                    menuItem1.setText("Abrir");
                    menu1.add(menuItem1);

                    //---- menuItem2 ----
                    menuItem2.setText("Guardar");
                    menu1.add(menuItem2);

                    //---- menuItem3 ----
                    menuItem3.setText("Salir");
                    menu1.add(menuItem3);
                }
                menuBar1.add(menu1);

                //======== menu2 ========
                {
                    menu2.setText("Dise\u00f1o");
                    menu2.setBackground(new Color(0x414450));
                }
                menuBar1.add(menu2);

                //---- button3 ----
                button3.setText("Acerca de");
                button3.setBorderPainted(false);
                button3.setBackground(new Color(0x414450));
                button3.setForeground(Color.white);
                menuBar1.add(button3);
            }
            frame1.setJMenuBar(menuBar1);

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayout());
            }
            frame1ContentPane.add(panel1);

            //---- label1 ----
            label1.setText("text");
            frame1ContentPane.add(label1);
            frame1.pack();
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame frame1;
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenu menu2;
    private JButton button3;
    private JPanel panel1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
