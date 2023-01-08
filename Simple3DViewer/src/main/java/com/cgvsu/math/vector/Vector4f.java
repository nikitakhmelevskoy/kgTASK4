package com.cgvsu.math.vector;

public class Vector4f extends Vector {
    private static final int size = 4;

    public Vector4f(float[] vector) {
        super(vector, size);
        this.vector = vector;
    }

    public Vector4f(float v1, float v2, float v3, float v4) {
        super(v1, v2, v3, v4, size);
    }

    @Override
    public Vector getZeroVector(int size) {
        if (size != this.getSize()) {
            size = this.getSize();
        }

        return new Vector4f(new float[size]);
    }
}