package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class Model2D extends JPanel {

    //коды операций с моделью
    static final int CODE_MIRROR_X          = 1;
    static final int CODE_MIRROR_Y          = 2;
    static final int CODE_MIRROR_OXY        = 3;
    static final int CODE_ROTATE            = 4;
    static final int CODE_SCALE_UP          = 5;
    static final int CODE_SCALE_DOWN        = 51;
    static final int CODE_MOVE_VECTOR_UP    = 6;
    static final int CODE_MOVE_VECTOR_DOWN  = 61;
    static final int CODE_MOVE_UP           = 10;
    static final int CODE_MOVE_DOWN         = 11;
    static final int CODE_MOVE_LEFT         = 12;
    static final int CODE_MOVE_RIGHT        = 13;
    static final int CODE_COMPOSITE_AFFINE  = 123;
    static final int CODE_COMPOSITE_AFFINE2 = 124;

    private JFrame parentFrame;

    private double L = -20;
    private double B = -20;
    private double R =  20;
    private double T =  20;

    private int W;
    private int H;

    private Matrix verts;
    private Matrix edges;

    private HashMap<Object, Object> vertEdgesMap;

    private int anchorId = 0;
    private int anchorId2 = 0;

    private double angle;

    Model2D(JFrame frame) {
        parentFrame = frame;

        //инициализация W и H, а так же одинаковый масштаб
        setResolution();
        //загружаем карты вершин и ребер
        loadVertices();
        loadEdges();
    }

    private int worldToScreenX(double X) {
        return (int) (W * ((X - L) / (R - L)));
    }

    private int worldToScreenY(double Y) {
        return (int) (H * (1 - ((Y - B) / (T - B))));
    }

    private void loadVertices() {
        File file = new File("VERTICES.txt");

        BufferedReader bFileReader;
        String lineX = null;
        String lineY = null;
        String lineD = null;
        try {
            String completeString;

            bFileReader = new BufferedReader(new FileReader(file));
            while ((completeString = bFileReader.readLine()) != null) {
                if (lineX == null) {
                    lineX = completeString;
                } else if (lineY == null) {
                    lineY = completeString;
                } else if (lineD == null) {
                    lineD = completeString;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] completeStringSplit = lineX != null ? (lineX + " " + lineY + " " +lineD).split(" ") : null;

        assert completeStringSplit != null;
        double[] v = new double[completeStringSplit.length];
        int i = 0;

        for (String value : completeStringSplit) {
            v[i] = Integer.parseInt(value);
            i++;
        }

        verts = new Matrix(3, lineX.split(" ").length, v);
    }

    private void loadEdges() {
        File file = new File("EDGES.txt");

        BufferedReader bFileReader;
        StringBuilder line = new StringBuilder();
        int lineCount = 0;

        try {
            String completeString;
            bFileReader = new BufferedReader(new FileReader(file));

            completeString = bFileReader.readLine();
            do {
                line.append(completeString).append(" ");
                lineCount++;
            } while ((completeString = bFileReader.readLine()) != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] completeStringSplit = line.toString().split(" ");

        int i = 0;
        double[] v = new double[completeStringSplit.length];

        for (String value : completeStringSplit) {
            v[i] = Integer.parseInt(value);
            i++;
        }

        edges = new Matrix(lineCount, 2, v);
    }

    //метод в котором происходит рисование (любое, и только в этом методе. Его можно вызвать только вызвав repaint();
    //в любом месте кода)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //рисуем координатные оси
        g.drawLine(worldToScreenX(L), worldToScreenY(0), worldToScreenX(R), worldToScreenY(0));
        g.drawLine(worldToScreenX(0), worldToScreenY(B), worldToScreenX(0), worldToScreenY(T));

        //рисуем рюшечки
        drawAdditionalAxes(g);

        // присваиваем каждой вершине номер по порядку
        vertEdgesMap = new HashMap<>();
        for (int j = 0; j < verts.getCol(); j++) {
            vertEdgesMap.put(j + 1, new double[] { verts.getMatrix()[0][j], verts.getMatrix()[1][j] });

            if (verts.getMatrix()[0][j] == 1 && verts.getMatrix()[1][j] == 3 && anchorId == 0) {
                anchorId = j + 1;
            }

            if (verts.getMatrix()[0][j] == 1 && verts.getMatrix()[1][j] == 5 && anchorId2 == 0) {
                anchorId2 = j + 1;
            }
        }

        MapHelper verticesAndEdgesMap = new MapHelper(vertEdgesMap);
        //рисуем фигуру по карте вершин и карте ребер
        for (int i = 0; i < edges.getRow(); i++) {
            g.drawLine(
                    worldToScreenX(verticesAndEdgesMap.getValueByKeyAndIndex((int) edges.getMatrix()[i][0], 0)),
                    worldToScreenY(verticesAndEdgesMap.getValueByKeyAndIndex((int) edges.getMatrix()[i][0], 1)),
                    worldToScreenX(verticesAndEdgesMap.getValueByKeyAndIndex((int) edges.getMatrix()[i][1], 0)),
                    worldToScreenY(verticesAndEdgesMap.getValueByKeyAndIndex((int) edges.getMatrix()[i][1], 1)));
        }
    }

    private void drawAdditionalAxes(Graphics g) {
        //насечки для оси Х
        for (int i = (int) L - 1; i <= (int) R; i++) {
            g.drawLine(worldToScreenX(i),
                    worldToScreenY(0.2),
                    worldToScreenX(i),
                    worldToScreenY(-0.2));

            if (i != 0) {
                g.drawString(String.valueOf(i), worldToScreenX(i), worldToScreenY(-0.75));
            }
        }

        //насечки для оси У
        for (int i = (int) B - 1; i <= (int) T; i++) {
            g.drawLine(worldToScreenX(0.2),
                    worldToScreenY(i),
                    worldToScreenX(-0.2),
                    worldToScreenY(i));

            if (i != 0) {
                g.drawString(String.valueOf(i), worldToScreenX(0.25), worldToScreenY(i));
            }
        }

        //ноль
        g.drawString("0", worldToScreenX(0), worldToScreenY(0));
    }

    void makeAffineTranslation(int code) {
        Matrix verticesMatrix = new Matrix(verts);

        switch (code) {
            case CODE_MIRROR_X : {
                verts = new Matrix(AffineBasic.mirroringX().pow(verticesMatrix));
                break;
            }

            case CODE_MIRROR_Y : {
                verts = new Matrix(AffineBasic.mirroringY().pow(verticesMatrix));
                break;
            }

            case CODE_MIRROR_OXY : {
                verts = new Matrix(AffineBasic.mirroringCenter().pow(verticesMatrix));
                break;
            }

            case CODE_ROTATE : {
                verts = new Matrix(AffineBasic.rotating(10.0).pow(verticesMatrix));
                angle += 10.0;
                break;
            }

            case CODE_SCALE_UP: {
                verts = new Matrix(AffineBasic.scaling(2, 2).pow(verticesMatrix));
                break;
            }

            case CODE_SCALE_DOWN : {
                verts = new Matrix(AffineBasic.scaling(0.5, 0.5).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_VECTOR_UP: {
                verts = new Matrix(AffineBasic.vector(2, 2).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_VECTOR_DOWN : {
                verts = new Matrix(AffineBasic.vector(-2, -2).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_UP : {
                verts = new Matrix(AffineBasic.vector(0, .1).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_DOWN : {
                verts = new Matrix(AffineBasic.vector(0, -.1).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_LEFT : {
                verts = new Matrix(AffineBasic.vector(-.1, 0).pow(verticesMatrix));
                break;
            }

            case CODE_MOVE_RIGHT : {
                verts = new Matrix(AffineBasic.vector(.1, 0).pow(verticesMatrix));
                break;
            }

            case CODE_COMPOSITE_AFFINE : {
                verts = new Matrix(compositeAffine().pow(verticesMatrix));
                break;
            }

            case CODE_COMPOSITE_AFFINE2 : {
                verts = new Matrix(compositeAfffine().pow(verticesMatrix));
            }
        }

        repaint();
    }

    private Matrix compositeAffine() {
        Matrix affine = new Matrix(3, 3, new double[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 });

        boolean flag = false;

        double[] vertex1 = (double[]) vertEdgesMap.get(anchorId);
        double[] vertex2 = (double[]) vertEdgesMap.get(anchorId2);

        double[] basis1 = new double[] { 0.0, 0.0 };
        double[] basis2 = new double[] { 0.0, 1.1 };

        //I and IV
        if (((vertex2[0] - vertex1[0]) >= 0 && (vertex2[1] - vertex1[1]) >= 0) ||
                ((vertex2[0] - vertex1[0]) >= 0 && (vertex2[1] - vertex1[1]) <= 0)) {
            flag = true;
        }

        double[] vector1 = new double[] { vertex2[0] - vertex1[0], vertex2[1] - vertex1[1] };
        double[] vector2 = new double[] { basis2[0] - basis1[0], basis2[1] - basis1[1] };

        double scalarMultiply = (vector1[0] * vector2[0]) + (vector1[1] * vector2[1]);
        double vector1Size = Math.sqrt(Math.pow(vector1[0], 2) + Math.pow(vector1[1], 2));
        double vector2Size = Math.sqrt(Math.pow(vector2[0], 2) + Math.pow(vector2[1], 2));

        double cosPhiValue = scalarMultiply / (vector1Size * vector2Size);
        double sinPhiValue = Math.sqrt(1.0 - Math.pow(cosPhiValue, 2));

        affine = affine.
                pow(AffineBasic.vector(vertex1[0], vertex1[1])).
                pow(flag
                        ? AffineBasic.rotatingPo(sinPhiValue, cosPhiValue)
                        : AffineBasic.rotatingProtiv(sinPhiValue, cosPhiValue)).
                pow(AffineBasic.mirroringY()).
                pow(flag
                        ? AffineBasic.rotatingProtiv(sinPhiValue, cosPhiValue)
                        : AffineBasic.rotatingPo(sinPhiValue, cosPhiValue)).
                pow(AffineBasic.vector(-vertex1[0], -vertex1[1]));

        return affine;
    }

    private Matrix compositeAfffine() {
        Matrix affine = new Matrix(3, 3, new double[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 });
        double[] vertex = (double[]) vertEdgesMap.get(anchorId);

        affine = affine.
                pow(AffineBasic.vector(vertex[0], vertex[1])).
                pow(AffineBasic.rotating(10.0)).
                pow(AffineBasic.vector(-vertex[0], -vertex[1]));

        return affine;
    }

    //вызывается при изменении размеров окна. Делает одинаковый мастштаб по осям
    private void setResolution() {
        W = parentFrame.getContentPane().getWidth();
        H = parentFrame.getContentPane().getHeight();

        //одинаковый масштаб по осям
        T = (((R - L) * H / W) / 2);
        B = ((-(R - L) * H / W) / 2);

        // тоже самое что и выше
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                W = parentFrame.getContentPane().getWidth();
                H = parentFrame.getContentPane().getHeight();

                T = (((R - L) * H / W) / 2);
                B = ((-(R - L) * H / W) / 2);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }
}
