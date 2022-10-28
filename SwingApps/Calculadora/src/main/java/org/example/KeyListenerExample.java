package org.example;

// importing awt libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// class which inherits Frame class and implements KeyListener interface
public class KeyListenerExample extends Frame implements KeyListener {
    // creating object of Label class   and TextArea class
    JFrame frame1;
    Label l;
    TextArea area;
    // class constructor
    KeyListenerExample() {

        frame1 = new JFrame("Key Listener Example");
        // creating the label
        l = new Label();
        // setting the location of the label in frame
        l.setBounds (20, 50, 100, 20);
        // creating the text area
        area = new TextArea();
        // setting the location of text area
        area.setBounds (20, 80, 300, 300);
        // adding the KeyListener to the text area
        area.addKeyListener(this);
        // adding the label and text area to the frame
        frame1.add(l);
        frame1.add(area);
        // setting the size, layout and visibility of frame
        frame1.setSize (400, 400);
        frame1.setLayout (null);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible (true);
    }
    // overriding the keyPressed() method of KeyListener interface where we set the text of the label when key is pressed
    public void keyPressed (KeyEvent e) {
        l.setText (e.getKeyCode() + " Key Typed");
        KeyStroke ks2 = KeyStroke.getKeyStroke(e.getKeyCode(), 0);
        System.out.println( ks2 );
    }
    // overriding the keyReleased() method of KeyListener interface where we set the text of the label when key is released
    public void keyReleased (KeyEvent e) {
        l.setText (e.getKeyChar() + " Key Typed");
    }
    // overriding the keyTyped() method of KeyListener interface where we set the text of the label when a key is typed
    public void keyTyped (KeyEvent e) {

    }
    // main method
    public static void main(String[] args) {
        new KeyListenerExample();
    }
}