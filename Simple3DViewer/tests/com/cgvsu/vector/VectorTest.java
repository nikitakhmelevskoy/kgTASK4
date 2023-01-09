package com.cgvsu.vector;

import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector2f;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.math.vector.Vector4f;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class VectorTest {
    static final float EPS = 1e-6f;

    @Test
    public void isEqual() {
        Vector2f vector2f1 = new Vector2f(new float[]{1, 2});
        Vector2f vector2f2 = new Vector2f(new float[]{1, 3});
        Vector2f vector2f3 = new Vector2f(new float[]{1, 2});

        Assertions.assertThat(vector2f1.isEqual(vector2f2)).isEqualTo(false);
        Assertions.assertThat(vector2f1.isEqual(vector2f3)).isEqualTo(true);

        Vector3f vector3f1 = new Vector3f(new float[]{1, 2.2f, 3});
        Vector3f vector3f2 = new Vector3f(new float[]{1, 2, 4});
        Vector3f vector3f3 = new Vector3f(new float[]{1, 2.2000001f, 3});

        Assertions.assertThat(vector3f1.isEqual(vector3f2)).isEqualTo(false);
        Assertions.assertThat(vector3f1.isEqual(vector3f3)).isEqualTo(true);

        Vector3f vector4f1 = new Vector3f(new float[]{1, 2, 3, 4});
        Vector3f vector4f2 = new Vector3f(new float[]{1, 2, 4, 3});
        Vector3f vector4f3 = new Vector3f(new float[]{1, 2, 3, 4});

        Assertions.assertThat(vector4f1.isEqual(vector4f2)).isEqualTo(false);
        Assertions.assertThat(vector4f1.isEqual(vector4f3)).isEqualTo(true);
    }

    @Test
    public void sumVector() throws Vector.VectorException {
        Vector2f vector2f1 = new Vector2f(new float[]{3, 5});
        Vector2f vector2f2 = new Vector2f(new float[]{-3, 5});
        Vector3f vector3f1 = new Vector3f(new float[]{1, 2, 3});
        Vector3f vector3f2 = new Vector3f(new float[]{1, 2, 4});
        Vector4f vector4f1 = new Vector4f(new float[]{1, 2, 3, 4});

        Assertions.assertThat(Vector.sumVector(vector2f1, vector2f2).getVector()).
                isEqualTo(new float[]{0, 10});

        Throwable thrown = Assertions.catchThrowable(() -> {
            Vector.sumVector(vector3f1, vector4f1);
        });
        Assertions.assertThat(thrown).isInstanceOf(Vector.VectorException.class);
        Assertions.assertThat(thrown.getMessage()).isNotBlank();
        Assertions.assertThat(thrown.getMessage()).
                isEqualTo("Vectors of different sizes can't be summed");

        Assertions.assertThat(Vector.sumVector(vector3f1, vector3f2).getVector()).
                isEqualTo(new float[]{2, 4, 7});
        Assertions.assertThat(vector3f2.minusVector(vector3f2, vector3f1).getVector()).
                isEqualTo(new float[]{0, 0, 1});
    }

    @Test
    public void sumWithConstant() {
        Vector2f vector2f = new Vector2f(new float[]{3, 5});
        Vector3f vector3f = new Vector3f(new float[]{1, 2, 3});
        Vector4f vector4f = new Vector4f(new float[]{1, 2, 3, 4});

        Assertions.assertThat(vector2f.sumWithConstant(5.3f).getVector()).isEqualTo(new float[]{8.3f, 10.3f});
        Assertions.assertThat(vector3f.sumWithConstant(0).getVector()).isEqualTo(new float[]{1, 2, 3});
        Assertions.assertThat(vector4f.sumWithConstant(-2).getVector()).isEqualTo(new float[]{-1, 0, 1, 2});
    }

    @Test
    public void minusWithConstant() {
        Vector2f vector2f = new Vector2f(new float[]{3, 5});
        Vector2f resultVector = new Vector2f(new float[]{-0.3f, 1.7f});
        Assertions.assertThat(vector2f.minusWithConstant(3.3f).isEqual(resultVector)).isEqualTo(true);

        Vector3f vector3f = new Vector3f(new float[]{1, 2, 3});
        Assertions.assertThat(vector3f.minusWithConstant(0).getVector()).isEqualTo(new float[]{1, 2, 3});

        Vector4f vector4f = new Vector4f(new float[]{1, 2, 3, 4});
        Assertions.assertThat(vector4f.minusWithConstant(-2).getVector()).isEqualTo(new float[]{3, 4, 5, 6});
    }

    @Test
    public void multiplicateVectorOnConstant() {
        Vector2f vector2f = new Vector2f(new float[]{3, 4});
        Assertions.assertThat(vector2f.multiplyVectorOnConstant(0).getVector()).
                isEqualTo(new float[]{0, 0});

        Vector3f vector3f = new Vector3f(new float[]{2, 3, 4});
        Vector3f resultVector = new Vector3f(new float[]{4.8f, 7.2f, 9.6f});
        Assertions.assertThat(vector3f.multiplyVectorOnConstant(2.4f).isEqual(resultVector)).
                isEqualTo(true);

        Vector4f vector4f = new Vector4f(new float[]{-4, 3, 4, -5});
        Assertions.assertThat(vector4f.multiplyVectorOnConstant(-3).getVector()).
                isEqualTo(new float[]{12, -9, -12, 15});
    }

    @Test
    public void divideVectorOnConstant() throws Vector.VectorException {
        Vector2f vector2f = new Vector2f(new float[]{3, 4});
        Throwable thrown = Assertions.catchThrowable(() -> {
            vector2f.divideVectorOnConstant(0);
        });
        Assertions.assertThat(thrown).isInstanceOf(Vector.VectorException.class);
        Assertions.assertThat(thrown.getMessage()).isNotBlank();
        Assertions.assertThat(thrown.getMessage()).isEqualTo("Division by zero");

        Vector3f vector3f = new Vector3f(new float[]{2, 3, 4});
        Vector3f resultVector1 = new Vector3f(new float[]{0.8333333f, 1.25f, 1.6666666f});
        Assertions.assertThat(vector3f.divideVectorOnConstant(2.4f).isEqual(resultVector1)).isEqualTo(true);

        Vector4f vector4f = new Vector4f(new float[]{-4, 3, 4, -5});
        Vector4f resultVector2 = new Vector4f(new float[]{0.4f, -0.3f, -0.4f, 0.5f});
        Assertions.assertThat(vector4f.divideVectorOnConstant(-10).isEqual(resultVector2)).
                isEqualTo(true);
    }

    @Test
    public void getVectorLength() {
        Vector2f vector2f = new Vector2f(new float[]{3, 4});
        Assertions.assertThat(vector2f.getVectorLength()).isEqualTo(5);

        Vector3f vector3f = new Vector3f(new float[]{0, 3.3f, 4.1f});
        float result1 = 5.2630789f;
        Assertions.assertThat(Math.abs(vector3f.getVectorLength() - result1) < EPS).isEqualTo(true);

        Vector4f vector4f = new Vector4f(new float[]{-4, 3, 4, -5});
        float result2 = 8.1240384f;
        Assertions.assertThat(Math.abs(vector4f.getVectorLength() - result2) < EPS).isEqualTo(true);
    }

    @Test
    public void normalizeVector() throws Vector.VectorException {
        Vector2f vector2f = new Vector2f(new float[]{0, 0});
        Throwable thrown = Assertions.catchThrowable(vector2f::normalizeVector);
        Assertions.assertThat(thrown).isInstanceOf(Vector.VectorException.class);
        Assertions.assertThat(thrown.getMessage()).isNotBlank();
        Assertions.assertThat(thrown.getMessage()).isEqualTo("Division by zero");

        Vector3f vector3f = new Vector3f(new float[]{0, 3.3f, 4.1f});
        Vector3f resultVector1 = new Vector3f(new float[]{0, 0.6270094f, 0.7790116f});
        Assertions.assertThat(vector3f.normalizeVector().isEqual(resultVector1)).isEqualTo(true);

        Vector4f vector4f = new Vector4f(new float[]{-8, 3, 2, -2});
        Vector4f resultVector2 = new Vector4f(new float[]{-0.8888888f, 0.3333333f, 0.2222222f, -0.2222222f});
        Assertions.assertThat(vector4f.normalizeVector().isEqual(resultVector2)).isEqualTo(true);
    }

    @Test
    public void dotProduct() {
        Vector2f vector2f1 = new Vector2f(new float[]{0, 1});
        Vector2f vector2f2 = new Vector2f(new float[]{5, 6});
        Assertions.assertThat(Vector.dotProduct(vector2f1, vector2f2)).
                isEqualTo(6);

        Vector3f vector3f1 = new Vector3f(new float[]{0, 3.3f, 4.1f});
        Vector3f vector3f2 = new Vector3f(new float[]{-7.3f, -1, 3.4f});
        float result1 = 10.64f;
        Assertions.assertThat(Math.abs(Vector.dotProduct(vector3f1, vector3f2) - result1) < EPS).
                isEqualTo(true);

        Vector4f vector4f1 = new Vector4f(new float[]{-8, 3, 2, -2});
        Vector4f vector4f2 = new Vector4f(new float[]{0.7f, 3, 0, 1});
        float result2 = 1.4f;
        Assertions.assertThat(Math.abs(Vector.dotProduct(vector4f1, vector4f2) - result2) < EPS).
                isEqualTo(true);
    }

    @Test
    public void crossProduct() {
        Vector3f vector3f1 = new Vector3f(new float[]{-2, 3, 0});
        Vector3f vector3f2 = new Vector3f(new float[]{-2, 0, 6});
        Vector3f vector3d = new Vector3f(new float[3]);
        vector3d.crossProduct(vector3f1, vector3f2);

        Assertions.assertThat(vector3d.getVector()).
                isEqualTo(new float[]{18, 12, 6});
    }
}
