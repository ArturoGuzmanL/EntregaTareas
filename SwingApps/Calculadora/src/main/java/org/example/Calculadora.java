/*
 * Created by JFormDesigner on Sun Oct 23 15:05:02 CEST 2022
 */

package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Arturo Guzmán Lucena
 */
public class Calculadora extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calculadora ventana = new Calculadora();
                ventana.setSize(new Dimension(410, 680));
                ventana.setMinimumSize(new Dimension(300, 590));
                ventana.setVisible(true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Calculadora() {
        initComponents();
        setContentPane(panelPrincipal);
    }

    private void buttonAction(ActionEvent e) {
        // TODO add your code here
        JButton boton = (JButton) e.getSource();

        switch (boton.getActionCommand()) {
            case "Numero" -> {

                if (resultadoGrande.getText().equals("0") || reiniciar) {
                    resultadoGrande.setText(boton.getText());
                    reiniciar = false;
                } else {
                    resultadoGrande.setText(resultadoGrande.getText() + boton.getText());
                }

            }

            case "Borrar" -> {

                if (resultadoGrande.getText().length() > 0) {
                    resultadoGrande.setText(resultadoGrande.getText().substring(0, resultadoGrande.getText().length() - 1));
                }

            }

            case "BorrarTodo" -> {
                resultadoMiniNoHTML = "";
                resultadoGrande.setText("");
                resultadoMini.setText("");
                listaNumerosCalculo.clear();
                listaOperacionesCalculo.clear();
            }

            case "Sumar" -> {

                listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                listaOperacionesCalculo.add(1);
                String grandetxt = resultadoGrande.getText();

                resultadoMiniNoHTML = resultadoMiniNoHTML + "\n" + grandetxt + " + ";
                resultadoMini.setText(
                        "<html>"
                                + resultadoMiniNoHTML +
                        "</html>"
                );
                resultadoGrande.setText("0");

            }

            case "Restar" -> {

                listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                listaOperacionesCalculo.add(2);
                String grandetxt = resultadoGrande.getText();

                resultadoMiniNoHTML = resultadoMiniNoHTML + "\n" + grandetxt + " - ";
                resultadoMini.setText(
                        "<html>"
                                + resultadoMiniNoHTML +
                        "</html>"
                );
                resultadoGrande.setText("0");

            }

            case "Multiplicar" -> {

                listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                listaOperacionesCalculo.add(3);
                String grandetxt = resultadoGrande.getText();

                resultadoMiniNoHTML = resultadoMiniNoHTML + "\n" + grandetxt + " × ";
                resultadoMini.setText(
                        "<html>"
                                + resultadoMiniNoHTML +
                        "</html>"
                );
                resultadoGrande.setText("");

            }

            case "Dividir" -> {

                listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                listaOperacionesCalculo.add(4);
                File dividir = new File("/Images/divide-sign-24.png");
                String grandetxt = resultadoGrande.getText();

                resultadoMiniNoHTML = resultadoMiniNoHTML + "\n" + grandetxt + " "
                                                                 + "<img src=\""
                                                                 + getClass().getResource("/Images/dividirCalculo.png")
                                                                 + "\">" + " ";
                resultadoMini.setText(
                        "<html>"
                                + resultadoMiniNoHTML +
                        "</html>"
                );
                resultadoGrande.setText("0");

            }

            case "Porcentaje" -> {

                listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                listaOperacionesCalculo.add(5);
                String grandetxt = resultadoGrande.getText();

                resultadoMiniNoHTML = resultadoMiniNoHTML + "\n" + grandetxt + " % ";
                resultadoMini.setText(
                        "<html>"
                                + resultadoMiniNoHTML +
                        "</html>"
                );
                resultadoGrande.setText("0");

            }

            case "Igual" -> {

                try {
                    listaNumerosCalculo.add(Float.valueOf(resultadoGrande.getText()));
                    float resultado = listaNumerosCalculo.get(0);

                    for (int i = 1; i < listaNumerosCalculo.size(); i++) {

                        switch (listaOperacionesCalculo.get(i - 1)) {

                            case 1 -> resultado += listaNumerosCalculo.get(i);
                            case 2 -> resultado -= listaNumerosCalculo.get(i);
                            case 3 -> resultado = resultado * listaNumerosCalculo.get(i);
                            case 4 -> resultado = resultado / listaNumerosCalculo.get(i);
                            case 5 -> resultado = resultado % listaNumerosCalculo.get(i);

                        }
                    }

                    resultadoMiniNoHTML = "";
                    resultadoMini.setText("");

                    resultadoGrande.setText(String.valueOf(resultado));

                    listaNumerosCalculo.clear();
                    listaOperacionesCalculo.clear();
                    reiniciar = true;
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private void initComponents() {

        listaNumerosCalculo = new ArrayList<>();
        listaOperacionesCalculo = new ArrayList<>();
        resultadoMiniNoHTML = "";
        reiniciar = false;

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        frame1 = new JFrame();
        panelPrincipal = new JPanel();
        panelSuperior = new JPanel();
        voidText = new JLabel();
        calcText = new JLabel();
        resultadoMini = new JLabel();
        resultadoGrande = new JLabel();
        panelFila1 = new JPanel();
        porcentaje = new JButton();
        letraC = new JButton();
        dividir = new JButton();
        borrar = new JButton();
        panelFila2 = new JPanel();
        num7 = new JButton();
        num8 = new JButton();
        num9 = new JButton();
        multiplicar = new JButton();
        panelFila3 = new JPanel();
        num4 = new JButton();
        num5 = new JButton();
        num6 = new JButton();
        restar = new JButton();
        panelFila4 = new JPanel();
        num1 = new JButton();
        num2 = new JButton();
        num3 = new JButton();
        sumar = new JButton();
        panelFila5 = new JPanel();
        vacio = new JButton();
        num0 = new JButton();
        punto = new JButton();
        igual = new JButton();

        //======== frame1 ========
        {
            frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            var frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new BorderLayout());

            //======== panelPrincipal ========
            {
                panelPrincipal.setBackground(new Color(0x1e1e1e));
                panelPrincipal.setLayout(new GridLayout(8, 0));

                //======== panelSuperior ========
                {
                    panelSuperior.setBackground(new Color(0x161616));
                    panelSuperior.setLayout(new GridLayout(3, 0, 0, -10));

                    //---- voidText ----
                    voidText.setText("Calculadora");
                    voidText.setForeground(new Color(0x1e1e1e));
                    voidText.setFont(new Font("Inter", Font.PLAIN, 20));
                    voidText.setHorizontalAlignment(SwingConstants.CENTER);
                    panelSuperior.add(voidText);

                    //---- calcText ----
                    calcText.setText("Calculadora");
                    calcText.setForeground(Color.white);
                    calcText.setFont(new Font("Inter", Font.PLAIN, 20));
                    calcText.setHorizontalAlignment(SwingConstants.CENTER);
                    panelSuperior.add(calcText);
                }
                panelPrincipal.add(panelSuperior);

                //---- resultadoMini ----
                resultadoMini.setHorizontalAlignment(SwingConstants.TRAILING);
                resultadoMini.setForeground(new Color(0x868a9a));
                resultadoMini.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                resultadoMini.setVerticalAlignment(SwingConstants.BOTTOM);
                panelPrincipal.add(resultadoMini);

                //---- resultadoGrande ----
                resultadoGrande.setText("0");
                resultadoGrande.setHorizontalAlignment(SwingConstants.TRAILING);
                resultadoGrande.setForeground(new Color(0x868a9a));
                resultadoGrande.setFont(new Font("Segoe UI", Font.PLAIN, 60));
                resultadoGrande.setVerticalAlignment(SwingConstants.TOP);
                resultadoGrande.setAutoscrolls(true);
                panelPrincipal.add(resultadoGrande);

                //======== panelFila1 ========
                {
                    panelFila1.setBackground(new Color(0x161616));
                    panelFila1.setLayout(new GridLayout(1, 4, 2, 2));

                    //---- porcentaje ----
                    porcentaje.setText("%");
                    porcentaje.setBackground(new Color(0x2b3045));
                    porcentaje.setForeground(Color.white);
                    porcentaje.setBorder(null);
                    porcentaje.setFont(new Font("Inter", Font.BOLD, 24));
                    porcentaje.setActionCommand("Porcentaje");
                    porcentaje.addActionListener(e -> buttonAction(e));
                    panelFila1.add(porcentaje);

                    //---- letraC ----
                    letraC.setText("C");
                    letraC.setBackground(new Color(0x2b3045));
                    letraC.setForeground(Color.white);
                    letraC.setBorder(null);
                    letraC.setFont(new Font("Inter", Font.PLAIN, 20));
                    letraC.setActionCommand("BorrarTodo");
                    letraC.addActionListener(e -> buttonAction(e));
                    panelFila1.add(letraC);

                    //---- dividir ----
                    dividir.setBackground(new Color(0x2b3045));
                    dividir.setForeground(Color.white);
                    dividir.setBorder(null);
                    dividir.setFont(new Font("Inter", Font.PLAIN, 20));
                    dividir.setIcon(new ImageIcon(getClass().getResource("/Images/divide-sign-24.png")));
                    dividir.setActionCommand("Dividir");
                    dividir.addActionListener(e -> buttonAction(e));
                    panelFila1.add(dividir);

                    //---- borrar ----
                    borrar.setBackground(new Color(0x2b3045));
                    borrar.setForeground(Color.white);
                    borrar.setBorder(null);
                    borrar.setFont(new Font("Inter", Font.PLAIN, 20));
                    borrar.setIcon(new ImageIcon(getClass().getResource("/Images/delete-3-24.png")));
                    borrar.setActionCommand("Borrar");
                    borrar.addActionListener(e -> buttonAction(e));
                    panelFila1.add(borrar);
                }
                panelPrincipal.add(panelFila1);

                //======== panelFila2 ========
                {
                    panelFila2.setBackground(new Color(0x161616));
                    panelFila2.setLayout(new GridLayout(1, 4, 2, 2));

                    //---- num7 ----
                    num7.setText("7");
                    num7.setBackground(new Color(0x353b4f));
                    num7.setForeground(Color.white);
                    num7.setBorder(null);
                    num7.setFont(new Font("Inter", Font.PLAIN, 20));
                    num7.setActionCommand("Numero");
                    num7.addActionListener(e -> buttonAction(e));
                    panelFila2.add(num7);

                    //---- num8 ----
                    num8.setText("8");
                    num8.setBackground(new Color(0x353b4f));
                    num8.setForeground(Color.white);
                    num8.setBorder(null);
                    num8.setFont(new Font("Inter", Font.PLAIN, 20));
                    num8.setActionCommand("Numero");
                    num8.addActionListener(e -> buttonAction(e));
                    panelFila2.add(num8);

                    //---- num9 ----
                    num9.setText("9");
                    num9.setBackground(new Color(0x353b4f));
                    num9.setForeground(Color.white);
                    num9.setBorder(null);
                    num9.setFont(new Font("Inter", Font.PLAIN, 20));
                    num9.setActionCommand("Numero");
                    num9.addActionListener(e -> buttonAction(e));
                    panelFila2.add(num9);

                    //---- multiplicar ----
                    multiplicar.setText("\u00d7");
                    multiplicar.setBackground(new Color(0x2b3045));
                    multiplicar.setForeground(Color.white);
                    multiplicar.setBorder(null);
                    multiplicar.setFont(new Font("Inter", Font.BOLD, 24));
                    multiplicar.setActionCommand("Multiplicar");
                    multiplicar.addActionListener(e -> buttonAction(e));
                    panelFila2.add(multiplicar);
                }
                panelPrincipal.add(panelFila2);

                //======== panelFila3 ========
                {
                    panelFila3.setBackground(new Color(0x161616));
                    panelFila3.setLayout(new GridLayout(1, 4, 2, 2));

                    //---- num4 ----
                    num4.setText("4");
                    num4.setBackground(new Color(0x353b4f));
                    num4.setForeground(Color.white);
                    num4.setBorder(null);
                    num4.setFont(new Font("Inter", Font.PLAIN, 20));
                    num4.setActionCommand("Numero");
                    num4.addActionListener(e -> buttonAction(e));
                    panelFila3.add(num4);

                    //---- num5 ----
                    num5.setText("5");
                    num5.setBackground(new Color(0x353b4f));
                    num5.setForeground(Color.white);
                    num5.setBorder(null);
                    num5.setFont(new Font("Inter", Font.PLAIN, 20));
                    num5.setActionCommand("Numero");
                    num5.addActionListener(e -> buttonAction(e));
                    panelFila3.add(num5);

                    //---- num6 ----
                    num6.setText("6");
                    num6.setBackground(new Color(0x353b4f));
                    num6.setForeground(Color.white);
                    num6.setBorder(null);
                    num6.setFont(new Font("Inter", Font.PLAIN, 20));
                    num6.setActionCommand("Numero");
                    num6.addActionListener(e -> buttonAction(e));
                    panelFila3.add(num6);

                    //---- restar ----
                    restar.setText("-");
                    restar.setBackground(new Color(0x2b3045));
                    restar.setForeground(Color.white);
                    restar.setBorder(null);
                    restar.setFont(new Font("Inter", Font.BOLD, 24));
                    restar.setActionCommand("Restar");
                    restar.addActionListener(e -> buttonAction(e));
                    panelFila3.add(restar);
                }
                panelPrincipal.add(panelFila3);

                //======== panelFila4 ========
                {
                    panelFila4.setBackground(new Color(0x161616));
                    panelFila4.setLayout(new GridLayout(1, 4, 2, 2));

                    //---- num1 ----
                    num1.setText("1");
                    num1.setBackground(new Color(0x353b4f));
                    num1.setForeground(Color.white);
                    num1.setBorder(null);
                    num1.setFont(new Font("Inter", Font.PLAIN, 20));
                    num1.setActionCommand("Numero");
                    num1.addActionListener(e -> buttonAction(e));
                    panelFila4.add(num1);

                    //---- num2 ----
                    num2.setText("2");
                    num2.setBackground(new Color(0x353b4f));
                    num2.setForeground(Color.white);
                    num2.setBorder(null);
                    num2.setFont(new Font("Inter", Font.PLAIN, 20));
                    num2.setActionCommand("Numero");
                    num2.addActionListener(e -> buttonAction(e));
                    panelFila4.add(num2);

                    //---- num3 ----
                    num3.setText("3");
                    num3.setBackground(new Color(0x353b4f));
                    num3.setForeground(Color.white);
                    num3.setBorder(null);
                    num3.setFont(new Font("Inter", Font.PLAIN, 20));
                    num3.setActionCommand("Numero");
                    num3.addActionListener(e -> buttonAction(e));
                    panelFila4.add(num3);

                    //---- sumar ----
                    sumar.setText("+");
                    sumar.setBackground(new Color(0x2b3045));
                    sumar.setForeground(Color.white);
                    sumar.setBorder(null);
                    sumar.setFont(new Font("Inter", Font.BOLD, 24));
                    sumar.setActionCommand("Sumar");
                    sumar.addActionListener(e -> buttonAction(e));
                    panelFila4.add(sumar);
                }
                panelPrincipal.add(panelFila4);

                //======== panelFila5 ========
                {
                    panelFila5.setBackground(new Color(0x161616));
                    panelFila5.setLayout(new GridLayout(1, 4, 2, 2));

                    //---- vacio ----
                    vacio.setBackground(new Color(0x2b3045));
                    vacio.setForeground(Color.white);
                    vacio.setBorder(null);
                    vacio.setFont(new Font("Inter", Font.PLAIN, 20));
                    vacio.setFocusable(false);
                    panelFila5.add(vacio);

                    //---- num0 ----
                    num0.setText("0");
                    num0.setBackground(new Color(0x353b4f));
                    num0.setForeground(Color.white);
                    num0.setBorder(null);
                    num0.setFont(new Font("Inter", Font.PLAIN, 20));
                    num0.setActionCommand("Numero");
                    num0.addActionListener(e -> buttonAction(e));
                    panelFila5.add(num0);

                    //---- punto ----
                    punto.setText(".");
                    punto.setBackground(new Color(0x2b3045));
                    punto.setForeground(Color.white);
                    punto.setBorder(null);
                    punto.setFont(new Font("Inter", Font.BOLD, 24));
                    punto.setActionCommand("Numero");
                    punto.addActionListener(e -> buttonAction(e));
                    panelFila5.add(punto);

                    //---- igual ----
                    igual.setText("=");
                    igual.setBackground(new Color(0x4d96bc));
                    igual.setBorder(null);
                    igual.setFont(new Font("Inter", Font.BOLD, 26));
                    igual.setActionCommand("Igual");
                    igual.addActionListener(e -> buttonAction(e));
                    panelFila5.add(igual);
                }
                panelPrincipal.add(panelFila5);
            }
            frame1ContentPane.add(panelPrincipal, BorderLayout.CENTER);
            frame1.pack();
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    ArrayList<Float> listaNumerosCalculo;
    ArrayList<Integer> listaOperacionesCalculo;
    String resultadoMiniNoHTML;
    boolean reiniciar;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame frame1;
    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JLabel voidText;
    private JLabel calcText;
    private JLabel resultadoMini;
    private JLabel resultadoGrande;
    private JPanel panelFila1;
    private JButton porcentaje;
    private JButton letraC;
    private JButton dividir;
    private JButton borrar;
    private JPanel panelFila2;
    private JButton num7;
    private JButton num8;
    private JButton num9;
    private JButton multiplicar;
    private JPanel panelFila3;
    private JButton num4;
    private JButton num5;
    private JButton num6;
    private JButton restar;
    private JPanel panelFila4;
    private JButton num1;
    private JButton num2;
    private JButton num3;
    private JButton sumar;
    private JPanel panelFila5;
    private JButton vacio;
    private JButton num0;
    private JButton punto;
    private JButton igual;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
