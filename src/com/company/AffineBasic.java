package com.company;

/**
 * Created by benz on 17.11.2016.
 * LAB_2D
 */

class AffineBasic {

    private AffineBasic() {

    }

    static Matrix mirroringX() {
        return new Matrix(3, 3, new double[] {
                1, 0, 0,
                0, -1, 0,
                0, 0, 1});
    }

    static Matrix mirroringY() {
        return new Matrix(3, 3, new double[] {
                -1, 0, 0,
                0, 1, 0,
                0, 0, 1});
    }

    static Matrix rotating(double phi) {
        return new Matrix(3, 3, new double[] {
                Math.cos(Math.toRadians(phi)), -Math.sin(Math.toRadians(phi)), 0,
                Math.sin(Math.toRadians(phi)), Math.cos(Math.toRadians(phi)), 0,
                0, 0, 1});
    }

    static Matrix rotatingPo(double phiSin, double phiCos) {
        return new Matrix(3, 3, new double[] {
                phiCos, phiSin, 0,
                -phiSin, phiCos, 0,
                0, 0, 1});
    }

    static Matrix rotatingProtiv(double phiSin, double phiCos) {
        return new Matrix(3, 3, new double[] {
                phiCos, -phiSin, 0,
                phiSin, phiCos, 0,
                0, 0, 1});
    }

    static Matrix scaling(double kx, double ky) {
        return new Matrix(3, 3, new double[] {
                kx, 0, 0,
                0, ky, 0,
                0, 0, 1});
    }

    static Matrix mirroringCenter() {
        return new Matrix(3, 3, new double[] {
                -1, 0, 0,
                0, -1, 0,
                0, 0, 1});
    }

    static Matrix vector(double ax, double ay) {
        return new Matrix(3, 3, new double[] {
                1, 0, ax,
                0, 1, ay,
                0, 0, 1});
    }
}
