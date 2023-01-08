package com.cgvsu.transformations;

import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector3f;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransformationsTest {
    static final float EPS = 1e-7f;

    @Test
    public void setScaleMatrix() {
        Matrix4f scaledMatrix = new Matrix4f();
        scaledMatrix.createIdentityMatrix();
        Matrix4f identityMatrix = new Matrix4f();
        identityMatrix.createIdentityMatrix();
        Transformations.setScaleMatrix(scaledMatrix, new Vector3f(1, 1, 1));
        Assertions.assertThat(scaledMatrix.isEqualMatrix(identityMatrix));
    }
}
