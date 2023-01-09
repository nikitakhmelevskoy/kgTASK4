package com.cgvsu.transformations;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector3f;

public class Transformations {
    private static final float EPS = 1e-5f;

    public static void addTranslate(Matrix4f matrix4f, Vector3f translateVector) {
        int indexRow = 0;
        int size = matrix4f.getSize();

        for (float value : translateVector.getVector()) {
            matrix4f.set(indexRow * size + (size - 1), value);
            indexRow++;
        }
    }

    public static Matrix getRotateMatrix(Vector3f rotateVector) throws Matrix.MatrixException {
        Matrix4f matrix4f = (Matrix4f) new Matrix4f().createSingleMatrix();

        if (Math.abs(rotateVector.get(0)) > EPS) {
            matrix4f = (Matrix4f) Matrix4f.multiplyMatrices(getXRotationMatrix(rotateVector.get(0)), matrix4f);
        }

        if (Math.abs(rotateVector.get(1)) > EPS) {
            matrix4f = (Matrix4f) Matrix4f.multiplyMatrices(getYRotationMatrix(rotateVector.get(1)), matrix4f);
        }

        if (Math.abs(rotateVector.get(2)) > EPS) {
            matrix4f = (Matrix4f) Matrix4f.multiplyMatrices(getZRotationMatrix(rotateVector.get(2)), matrix4f);
        }

        return matrix4f;
    }

    public static Matrix getXRotationMatrix(float alfa) {
        alfa = (float) Math.toRadians(alfa);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);

        return new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1}
        );
    }

    public static Matrix getYRotationMatrix(float alfa) {
        alfa = (float) Math.toRadians(alfa);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);

        return new Matrix4f(new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1}
        );
    }

    public static Matrix getZRotationMatrix(float alfa) {
        alfa = (float) Math.toRadians(alfa);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);

        return new Matrix4f(new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1}
        );
    }

    public static void setScaleMatrix(Matrix4f matrix4f, Vector3f scaleVector) {
        int index = 0;
        int size = matrix4f.getSize();

        for (float value : scaleVector.getVector()) {
            if (Math.abs(value) > EPS) {
                matrix4f.set(index * size + index, value);
            }

            index++;
        }
    }
}
