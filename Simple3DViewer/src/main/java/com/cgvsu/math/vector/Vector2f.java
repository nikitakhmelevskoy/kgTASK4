package com.cgvsu.math.vector;

public class Vector2f extends Vector {
    private static final int size = 2;

    public Vector2f(float[] vector) {
        super(vector, size);
        this.vector = vector;
    }

    public Vector2f(float v1, float v2) {
        super(v1, v2, size);
    }

    @Override
    public Vector getZeroVector(int size) {
        if (size != this.getSize()) {
            size = this.getSize();
        }

        return new Vector2f(new float[size]);
    }
}