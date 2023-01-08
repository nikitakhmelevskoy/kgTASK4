package com.cgvsu.matrix;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix3f;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.math.vector.Vector4f;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Objects;

public class MatrixTest {
    static final float EPS = 1e-7f;
    @Test
    public void createIdentityMatrix() {
        Matrix3f matrix3x = new Matrix3f(new float[]{5.4f, 0, 0, 0, 5.4f, 0, 0, 0, 5.4f});
        Assertions.assertThat(matrix3x.createIdentityMatrix(5.4f).getVector()).
                isEqualTo(matrix3x.getVector());

        Matrix4f matrix4x = new Matrix4f(new float[]{3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3});
        Assertions.assertThat(matrix4x.createIdentityMatrix(3).getVector()).
                isEqualTo(matrix4x.getVector());
    }

    @Test
    public void isIdentityMatrix() {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{-0.5f, 0, 0, 0, -0.5f, 0, 0, 0, -0.5f});
        Assertions.assertThat(Matrix.isIdentityMatrix(matrix3x1, EPS)).isEqualTo(true);

        Matrix3f matrix3x2 = new Matrix3f(new float[]{-0.5f, 6, 0, 0, -0.5f, -0.5f, 0, 0, -0.5f});
        Assertions.assertThat(Matrix.isIdentityMatrix(matrix3x2, EPS)).isEqualTo(false);

        Matrix3f matrix3x3 = new Matrix3f(new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
        Assertions.assertThat(Matrix.isIdentityMatrix(matrix3x3, EPS)).isEqualTo(false);

        Matrix4f matrix4x1 = new Matrix4f(new float[]{3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3});
        Assertions.assertThat(Matrix.isIdentityMatrix(matrix4x1, EPS)).isEqualTo(true);

        Matrix4f matrix4x2 = new Matrix4f(new float[]{3, 0, 0.4f, 0, 0, 3, 0, 0, 6.5f, 0, 3, 0, 0, 0, 0, 3});
        Assertions.assertThat(Matrix.isIdentityMatrix(matrix4x2, EPS)).isEqualTo(false);
    }

    @Test
    public void getZeroMatrix() {
        Assertions.assertThat(Objects.requireNonNull(Matrix3f.getZeroMatrix()).getVector()).
                isEqualTo(new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Test
    public void transposeMatrix() {
        Matrix3f matrix3x = new Matrix3f(new float[]{0, 1.1f, -3, 0, -4.5f, 7.3f, 6, 0.78f, 1});
        Assertions.assertThat(Matrix.transposeMatrix(matrix3x).getVector()).
                isEqualTo(new float[]{0, 0, 6, 1.1f, -4.5f, 0.78f, -3, 7.3f, 1});

        Matrix4f matrix4x = new Matrix4f(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Assertions.assertThat(Matrix.transposeMatrix(matrix4x).getVector()).
                isEqualTo(new float[]{0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15});
    }

    @Test
    public void sumMatrix() throws Matrix.MatrixException {
        Matrix4f matrix4x1 = new Matrix4f(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Matrix4f matrix4x2 = new Matrix4f(
                new float[]{0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15});

        Assertions.assertThat(Matrix.sumMatrix(matrix4x1, Matrix4f.getZeroMatrix()).getVector()).
                isEqualTo(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Assertions.assertThat(Matrix.sumMatrix(matrix4x1, matrix4x1).getVector()).
                isEqualTo(new float[]{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30});
        Assertions.assertThat(Matrix.sumMatrix(matrix4x1, matrix4x2).getVector()).
                isEqualTo(Matrix4f.getZeroMatrix().getVector());
    }

    @Test
    public void minusMatrix() throws Matrix.MatrixException {
        Matrix4f matrix4x1 = new Matrix4f(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Matrix4f matrix4x3 = new Matrix4f(
                new float[]{0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15});

        Assertions.assertThat(Matrix.minusMatrix(matrix4x1, Matrix4f.getZeroMatrix()).getVector()).
                isEqualTo(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Assertions.assertThat(Matrix.minusMatrix(matrix4x1, matrix4x1).getVector()).
                isEqualTo(Matrix4f.getZeroMatrix().getVector());
        Assertions.assertThat(Matrix.minusMatrix(matrix4x1, matrix4x3).getVector()).
                isEqualTo(new float[]{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30});
    }

    @Test
    public void multiplicateOnValue() {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{1, 2, 3, 4, 3, 2, 1, 0, 0});
        matrix3x1.multiplicateOnValue(3);
        Assertions.assertThat(matrix3x1.getVector()).isEqualTo(new float[]{3, 6, 9, 12, 9, 6, 3, 0, 0});

        Matrix3f matrix3x2 = new Matrix3f(new float[]{1, 2, 3, 4, 3, 2, 1, 0, 0});
        matrix3x2.multiplicateOnValue(0);
        Assertions.assertThat(matrix3x2.getVector()).isEqualTo(new float[matrix3x2.getLength()]);

        Matrix3f matrix3x3 = new Matrix3f(new float[]{1, 2, 3, 4, 3, -2, -1, 0, 0});
        Matrix3f result3x3 = new Matrix3f(new float[]{-0.73f, -1.46f, -2.19f, -2.92f, -2.19f, 1.46f, 0.73f, 0, 0});
        matrix3x3.multiplicateOnValue(-0.73f);
        Assertions.assertThat(matrix3x3.isEqualMatrix(result3x3)).isEqualTo(true);

        Matrix4f matrix4x1 = new Matrix4f(new float[]{1, 2, 3, 4, 3, 2, 1, 0, 0, 4, 3, 2, 1, 8, 4, 3});
        matrix4x1.multiplicateOnValue(3);
        Assertions.assertThat(matrix4x1.getVector()).
                isEqualTo(new float[]{3, 6, 9, 12, 9, 6, 3, 0, 0, 12, 9, 6, 3, 24, 12, 9});

        Matrix4f matrix4x2 = new Matrix4f(new float[]{1, 2, 3, 4, 3, 2, 1, 0, 0, 4, 3, 2, 1, 8, 4, 3});
        matrix4x2.multiplicateOnValue(0);
        Assertions.assertThat(matrix4x2.getVector()).isEqualTo(new float[matrix4x2.getLength()]);

        Matrix4f matrix4x3 = new Matrix4f(new float[]{1, 2, 3, 4, 3, -2, -1, 0, 0, 4, 3, 2, 1, 0, 4, 3});
        Matrix4f result4x3 = new Matrix4f(new float[]
                {-0.73f, -1.46f, -2.19f, -2.92f, -2.19f, 1.46f, 0.73f, 0, 0, -2.92f, -2.19f, -1.46f, -0.73f,
                        0, -2.92f, -2.19f});
        matrix4x3.multiplicateOnValue(-0.73f);
        Assertions.assertThat(matrix4x3.isEqualMatrix(result4x3)).isEqualTo(true);
    }

    @Test
    public void divideOnValue() throws Matrix.MatrixException {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{3, 6, 9, 12, 9, 6, 3, 0, 0});
        matrix3x1.divideOnValue(3);
        Assertions.assertThat(matrix3x1.getVector()).
                isEqualTo(new float[]{1, 2, 3, 4, 3, 2, 1, 0, 0});

        Matrix3f matrix3x2 = new Matrix3f(new float[]{3, 6, 9, 12, 9, 6, 3, 0, 0});
        Throwable thrown2 = Assertions.catchThrowable(() -> {
            matrix3x2.divideOnValue(0);
        });
        Assertions.assertThat(thrown2).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown2.getMessage()).isNotBlank();
        Assertions.assertThat(thrown2.getMessage()).isEqualTo("Division by zero");

        Matrix4f matrix4x1 = new Matrix4f(new float[]{3, 6, 9, 12, 9, 6, -3, 0, 0, 2, 3, -4, 5, 1, 7, 3});
        Matrix4f result4x1 = new Matrix4f(new float[]
                {-15, -30, -45, -60, -45, -30, 15, 0, 0, -10, -15, 20, -25, -5, -35, -15});
        matrix4x1.divideOnValue(-0.2f);
        Assertions.assertThat(matrix4x1.isEqualMatrix(result4x1)).isEqualTo(true);
    }

    @Test
    public void multiplicateOnVector() throws Matrix.MatrixException {
        Matrix3f matrix3x = new Matrix3f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Vector3f vector3f = new Vector3f(new float[]{0.0f, -0.5f, 1.7f});
        Vector3f result3f = new Vector3f(new float[]{4.1f, 7.7f, 11.3f});
        Assertions.assertThat(matrix3x.multiplicateOnVector(vector3f).isEqual(result3f)).isEqualTo(true);

        Matrix4f matrix4x = new Matrix4f(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Vector4f vector4f = new Vector4f(new float[]{0, 1, 2, 3});
        Assertions.assertThat(matrix4x.multiplicateOnVector(vector4f).getVector()).
                isEqualTo(new float[]{14, 38, 62, 86});
        Assertions.assertThat(matrix4x.multiplicateOnVector(matrix4x.getZeroVector(matrix4x.getSize())).getVector()).
                isEqualTo(matrix4x.getZeroVector(matrix4x.getSize()).getVector());

        Throwable thrown2 = Assertions.catchThrowable(() -> {
            matrix4x.multiplicateOnVector(vector3f);
        });
        Assertions.assertThat(thrown2).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown2.getMessage()).isNotBlank();
        Assertions.assertThat(thrown2.getMessage()).isEqualTo("Different sizes can't be multiplicated");
    }

    @Test
    public void multiplicateMatrices() throws Matrix.MatrixException {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        Matrix3f matrix3x2 = new Matrix3f(new float[]{9, 1, 2, 3, 4, 5, 6, 7, 8});

        Assertions.assertThat(Matrix.multiplicateMatrices(matrix3x1, matrix3x2).getVector()).
                isEqualTo(new float[]{15, 18, 21, 69, 54, 66, 123, 90, 111});

        Matrix4f matrix4x1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Throwable thrown2 = Assertions.catchThrowable(() -> {
            Matrix.multiplicateMatrices(matrix3x1, matrix4x1);
        });
        Assertions.assertThat(thrown2).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown2.getMessage()).isNotBlank();
        Assertions.assertThat(thrown2.getMessage()).isEqualTo("Different sizes can't be multiplicated");

        Matrix4f matrix4x2 = new Matrix4f(
                new float[]{0, 1.5f, 0, -3.8f, 4, 0, 6, -1.7f, 8, 9, 1, 11, -12, 0, 4, 0.5f});
        Matrix4f result = new Matrix4f(
                new float[]{-16, 28.5f, 31, 27.8f, -16, 70.5f, 75, 51.8f, -16, 112.5f, 119, 75.8f,
                        -16, 154.5f, 163, 99.8f});
        Assertions.assertThat(Matrix.multiplicateMatrices(matrix4x1, matrix4x2).isEqualMatrix(result)).
                isEqualTo(true);
    }

    @Test
    public void getMatrixDeterminant() {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{1, -2, 3, 4, 0, 6, -7, 8, 9});
        Assertions.assertThat(Matrix3f.getMatrixDeterminant(matrix3x1)).isEqualTo(204);

        Matrix3f matrix3x2 = new Matrix3f(new float[]{1, 0, 3, 4, 0, 6, -7, 0, 9});
        Assertions.assertThat(Matrix3f.getMatrixDeterminant(matrix3x2)).isEqualTo(0);

        Matrix4f matrix4x = new Matrix4f(new float[]{10, 0, 0, 0, 0, 4, 5, 2, 6, 2, 3, 3, 4, 1, 2, 1});
        Assertions.assertThat(Matrix4f.getMatrixDeterminant(matrix4x)).isEqualTo(-50);
    }

    @Test
    public void getDeterminant() {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{2, 5, 7, 6, 3, 4, 5, -2, -3});
        Matrix3f matrix3x2 = new Matrix3f(new float[]{2, 0, 7, 6, 0, 4, 5, 0, -3});

        Assertions.assertThat(matrix3x1.getDeterminant()).isEqualTo(-1);
        Assertions.assertThat(matrix3x2.getDeterminant()).isEqualTo(0);
    }

    @Test
    public void getInverseMatrix() throws Exception {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{2, 5, 7, 6, 3, 4, 5, -2, -3});
        Matrix3f inverseMatrix3x1 = new Matrix3f(new float[]{1, -1, 1, -38, 41, -34, 27, -29, 24});
        Assertions.assertThat(Matrix.getInverseMatrix(matrix3x1).isEqualMatrix(inverseMatrix3x1)).isEqualTo(true);
        Assertions.assertThat(Matrix.multiplicateMatrices(
                new Matrix3f(new float[]{2, 5, 7, 6, 3, 4, 5, -2, -3}), inverseMatrix3x1).isEqualMatrix(matrix3x1)).
                isEqualTo(true);

        Matrix3f matrix3x2 = new Matrix3f(new float[]{2, 0, 7, 6, 0, 4, 5, 0, -3});
        Throwable thrown = Assertions.catchThrowable(() -> {
            Matrix.getInverseMatrix(matrix3x2);
        });
        Assertions.assertThat(thrown).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown.getMessage()).isNotBlank();
        Assertions.assertThat(thrown.getMessage()).isEqualTo("Matrix hasn't inverse matrix");
    }

    @Test
    public void solutionByGaussMethod() throws Matrix.MatrixException {
        Matrix3f matrix3x1 = new Matrix3f(new float[]{3, 2, -5, 2, -1, 3, 1, 2, -1});
        Vector3f vector3f1 = new Vector3f(new float[]{-1, 13, 9});
        Assertions.assertThat(Matrix.solutionByGaussMethod(matrix3x1, vector3f1).getVector()).
                isEqualTo(new float[]{3, 5, 4});

        Matrix3f matrix3x = new Matrix3f(new float[]{2, 1, -1, -3, -1, 2, -2, 1, 2});
        Vector3f vector3f = new Vector3f(new float[]{8, -11, -3});
        Assertions.assertThat(Matrix.solutionByGaussMethod(matrix3x, vector3f).getVector()).
                isEqualTo(new float[]{2, 3, -1});

        //Бесконечно много решений, но найдём частное решение
        Matrix3f matrix3x2 = new Matrix3f(new float[]{1, 1, -1, 3, 2, -5, 3, 1, -7});
        Vector3f vector3f2 = new Vector3f(new float[]{4, 7, 2});
        Assertions.assertThat(Matrix.solutionByGaussMethod(matrix3x2, vector3f2).getVector()).
                isEqualTo(new float[]{2, 3, 1});

        //Нет решений
        Matrix3f matrix3x3 = new Matrix3f(new float[]{0, 1, 0, 0, 1, 0, 0, 0, 0});
        Vector3f vector3f3 = new Vector3f(new float[]{8, 6, 3});
        Throwable thrown = Assertions.catchThrowable(() -> {
            Matrix.solutionByGaussMethod(matrix3x3, vector3f3);
        });
        Assertions.assertThat(thrown).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown.getMessage()).isNotBlank();
        Assertions.assertThat(thrown.getMessage()).isEqualTo("There are no solutions");

        Matrix3f matrix3x4 = new Matrix3f(new float[]{1, 0, 0, 0, 0, 0, 2, 0, 0});
        Vector3f vector3f4 = new Vector3f(new float[]{8, 0, 0});
        Throwable thrown2 = Assertions.catchThrowable(() -> {
            Matrix.solutionByGaussMethod(matrix3x4, vector3f4);
        });
        Assertions.assertThat(thrown2).isInstanceOf(Matrix.MatrixException.class);
        Assertions.assertThat(thrown2.getMessage()).isNotBlank();
        Assertions.assertThat(thrown2.getMessage()).isEqualTo("There are no solutions");

        Matrix4f matrix4x  = new Matrix4f(new float[]{1, -1, 2, -3, 1, 4, -1, -2, 1, -4, 3, -2, 1, -8, 5, -2});
        Vector4f vector4f = new Vector4f(new float[]{1, -2, -2, -2});
        Assertions.assertThat(Matrix.solutionByGaussMethod(matrix4x, vector4f).getVector()).
                isEqualTo(new float[]{-8, 4, 8, 1});
    }
}

