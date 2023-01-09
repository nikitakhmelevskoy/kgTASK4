package com.cgvsuTests.transformations;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.transformations.Transformations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// TODO: 08.01.2023 scale - done; translate - done; rotations - ;
public class TransformationsTest {
    static final float EPS = 1e-5f;

    @Test
    public void setScaleMatrix01() {
        Matrix4f scaledMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Matrix4f SingleMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.setScaleMatrix(scaledMatrix, new Vector3f(1, 1, 1));
        Assertions.assertTrue(scaledMatrix.isEqualMatrix(SingleMatrix));
    }

    @Test
    public void setScaleMatrix02() {
        Matrix4f scaledMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Matrix4f SingleMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.setScaleMatrix(scaledMatrix, new Vector3f(1e-4f, 1e-4f, 1e-4f));
        Assertions.assertFalse(scaledMatrix.isEqualMatrix(SingleMatrix));
    }

    @Test
    public void setScaleMatrix03() {
        Matrix4f scaledMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Matrix4f SingleMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.setScaleMatrix(scaledMatrix, new Vector3f(7, 5, 3));
        Assertions.assertFalse(scaledMatrix.isEqualMatrix(SingleMatrix));
    }

    @Test
    public void setScaleMatrix04() {
        Matrix4f scaledMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Matrix4f expectedMatrix = new Matrix4f(new float[]{-7, 0, 0, 0, 0, -5, 0, 0, 0, 0, -3, 0, 0, 0, 0, 1});
        Transformations.setScaleMatrix(scaledMatrix, new Vector3f(-7, -5, -3));
        Assertions.assertTrue(scaledMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void addTranslation01() {
        Matrix4f translatedMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.addTranslate(translatedMatrix, new Vector3f(new float[]{2, 2, 2}));
        Matrix4f expectedMatrix = new Matrix4f(new float[]{1, 0, 0, 2, 0, 1, 0, 2, 0, 0, 1, 2, 0, 0, 0, 1});
        Assertions.assertTrue(translatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void addTranslation02() {
        Matrix4f translatedMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.addTranslate(translatedMatrix, new Vector3f(new float[]{0, 0, 0}));
        Matrix4f expectedMatrix = new Matrix4f(new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1});
        Assertions.assertTrue(translatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void addTranslation03() {
        Matrix4f translatedMatrix = (Matrix4f) new Matrix4f().createSingleMatrix();
        Transformations.addTranslate(translatedMatrix, new Vector3f(new float[]{-9, 0, 9}));
        Matrix4f expectedMatrix = new Matrix4f(new float[]{1, 0, 0, -9, 0, 1, 0, 0, 0, 0, 9, 1, 0, 0, 0, 1});
        Assertions.assertTrue(translatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix01X() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{90, 0, 0}));
        float alfa = (float) Math.toRadians(90);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1});
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix02X() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        float alfa = (float) Math.toRadians(0);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1});
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix03X() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        Matrix4f expectedMatrix = new Matrix4f(rotatedMatrix);
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix04X() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{-40, 0, 0}));
        float alfa = (float) Math.toRadians(-40);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1});
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix01Y() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 90, 0}));
        float alfa = (float) Math.toRadians(90);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1
        });
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix02Y() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        float alfa = (float) Math.toRadians(0);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1
        });
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix03Y() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        Matrix4f expectedMatrix = new Matrix4f(rotatedMatrix);
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix04Y() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, -45, 0}));
        float alfa = (float) Math.toRadians(-45);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1
        });
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix01Z() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 90}));
        float alfa = (float) Math.toRadians(90);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1});
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix02Z() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        float alfa = (float) Math.toRadians(0);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1});
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix03Z() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, 0}));
        Matrix4f expectedMatrix = new Matrix4f(rotatedMatrix);
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }

    @Test
    public void getRotateMatrix04Z() throws Matrix.MatrixException {
        Matrix4f rotatedMatrix;
        rotatedMatrix = (Matrix4f) Transformations.getRotateMatrix(new Vector3f(new float[]{0, 0, -45}));
        float alfa = (float) Math.toRadians(-45);
        final float cos = (float) Math.cos(alfa);
        final float sin = (float) Math.sin(alfa);
        Matrix4f expectedMatrix = new Matrix4f(new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
        Assertions.assertTrue(rotatedMatrix.isEqualMatrix(expectedMatrix));
    }



}
