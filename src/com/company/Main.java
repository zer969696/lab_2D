package com.company;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    private static Model2D model2D;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyJFrame f = new MyJFrame();
            f.setTitle("Lab_2");
            f.setSize(600, 600);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setVisible(true);

            model2D = new Model2D(f);
            f.add(model2D);
        });
    }

    private static class MyJFrame extends JFrame {

        MyJFrame() {
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                    switch (KeyEvent.getKeyText(e.getKeyCode())) {
                        case "1" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MIRROR_X);
                            break;
                        }

                        case "2" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MIRROR_Y);
                            break;
                        }

                        case "3" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MIRROR_OXY);
                            break;
                        }

                        case "4" : {
                            model2D.makeAffineTranslation(Model2D.CODE_ROTATE);
                            break;
                        }

                        case "=" : {
                            model2D.makeAffineTranslation(Model2D.CODE_SCALE_UP);
                            break;
                        }

                        case "Equals" : {
                            model2D.makeAffineTranslation(Model2D.CODE_SCALE_UP);
                            break;
                        }

                        case "-" : {
                            model2D.makeAffineTranslation(Model2D.CODE_SCALE_DOWN);
                            break;
                        }

                        case "Minus" : {
                            model2D.makeAffineTranslation(Model2D.CODE_SCALE_DOWN);
                            break;
                        }

                        case "5" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_VECTOR_DOWN);
                            break;
                        }

                        case "6" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_VECTOR_UP);
                            break;
                        }

                        case "↑": {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_UP);
                            break;
                        }

                        case "Up": {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_UP);
                            break;
                        }

                        case "↓" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_DOWN);
                            break;
                        }

                        case "Down" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_DOWN);
                            break;
                        }

                        case "←" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_LEFT);
                            break;
                        }

                        case "Left" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_LEFT);
                            break;
                        }

                        case "→" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_RIGHT);
                            break;
                        }

                        case "Right" : {
                            model2D.makeAffineTranslation(Model2D.CODE_MOVE_RIGHT);
                            break;
                        }

                        case "Z" : {
                            model2D.makeAffineTranslation(Model2D.CODE_COMPOSITE_AFFINE);
                            break;
                        }

                        case "X" : {
                            model2D.makeAffineTranslation(Model2D.CODE_COMPOSITE_AFFINE2);
                            break;
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
    }
}
