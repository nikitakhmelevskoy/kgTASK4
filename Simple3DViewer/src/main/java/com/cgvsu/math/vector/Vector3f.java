package com.cgvsu.math.vector;

public class Vector3f extends Vector {
    private static final int size = 3;

    public Vector3f(float[] vector) {
        super(vector, size);
        this.vector = vector;
    }

    public Vector3f(float v1, float v2, float v3) {
        super(v1, v2, v3, size);
    }

    @Override
    public Vector getZeroVector(int size) {
        if (size != this.getSize()) {
            size = this.getSize();
        }

        return new Vector3f(new float[size]);
    }

    public void crossProduct(final Vector3f vector1, final Vector3f vector2) {
        if (isEqualSize(vector1, vector2)) {
            this.getVector()[0] = vector1.get(1) * vector2.get(2) - vector1.get(2) * vector2.get(1);
            this.getVector()[1] = vector1.get(2) * vector2.get(0) - vector1.get(0) * vector2.get(2);
            this.getVector()[2] = vector1.get(0) * vector2.get(1) - vector1.get(1) * vector2.get(0);
        }
    }

}