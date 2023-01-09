package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.Matrix;
import com.cgvsu.math.matrix.Matrix4f;
import com.cgvsu.math.vector.Vector;
import com.cgvsu.math.vector.Vector3f;
import com.cgvsu.math.vector.Vector4f;

import javax.vecmath.Point2f;

import static com.cgvsu.transformations.Transformations.*;

// TODO: 05.01.2023  TODO: 04.01.2023  cross - векторное произведение - ; sub - вычитание - ; normalize - нормализация - ; -- DONE
// TODO: 06.01.2023 тяжело понять почему чайник пьяный, проблема в одном из трёх классов рендера, надо разобраться -разобрано, дело в конвейере, в render engine поменял порядок умножения

public class GraphicConveyor {
    private static final float EPS = 1e-5f;

    public static Matrix4f rotateScaleTranslate(Vector3f rotateVector, Vector3f scaleVector, Vector3f translateVector)
            throws Matrix.MatrixException {
        Matrix4f matrix4f = (Matrix4f) new Matrix4f().createSingleMatrix();
        setScaleMatrix(matrix4f, scaleVector);
        matrix4f = (Matrix4f) Matrix4f.multiplyMatrices(getRotateMatrix(rotateVector), matrix4f);
        addTranslate(matrix4f, translateVector);
        return matrix4f;
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) throws Vector.VectorException {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) throws Vector.VectorException {
        Vector3f resultX = new Vector3f(0, 0, 0);
        Vector3f resultY = new Vector3f(0, 0, 0);
        Vector3f resultZ;

        resultZ = (Vector3f) Vector.minusVector(target, eye);
        resultX.crossProduct(up, resultZ);
        resultY.crossProduct(resultZ, resultX);

        resultX.normalizeVector();
        resultY.normalizeVector();
        resultZ.normalizeVector();

        float[] matrix = new float[]{
                resultX.getX(), resultY.getX(), resultZ.getX(), -resultX.dotProduct(eye),
                resultX.getY(), resultY.getY(), resultZ.getY(), -resultY.dotProduct(eye),
                resultX.getZ(), resultY.getZ(), resultZ.getZ(), -resultZ.dotProduct(eye),
                0, 0, 0, 1
        };
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.set(0, tangentMinusOnDegree / aspectRatio);
        result.set(5, tangentMinusOnDegree);
        result.set(10, (farPlane + nearPlane) / (farPlane - nearPlane));
        result.set(11, 2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        result.set(14, 1.0F);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex)
            throws Matrix.MatrixException {
        Vector4f newVector = new Vector4f(new float[]{vertex.getX(), vertex.getY(), vertex.getZ(), 1});
        Vector4f resultVector = (Vector4f) matrix.multiplyOnVector(newVector);
        return new Vector3f(
                resultVector.getX() / resultVector.get(3),
                resultVector.getY() / resultVector.get(3),
                resultVector.getZ() / resultVector.get(3)
        );
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
