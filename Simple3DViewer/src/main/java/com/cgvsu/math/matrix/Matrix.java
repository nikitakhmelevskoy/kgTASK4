package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector;

public abstract class Matrix {
    public static class MatrixException extends Exception {
        public MatrixException(String message) {
            super(message);
        }
    }

    protected final int size;
    protected final int length;
    protected float[] vector;
    static final float EPS = 1e-6f;

    public Matrix(float[] vector, final int size) {
        if (vector.length == size * size) {
            this.vector = vector;
            this.size = size;
            this.length = size * size;
        } else if (size > 0) {
            float[] rightVector = new float[size * size];
            System.arraycopy(vector, 0, rightVector, 0, Math.min(vector.length, size * size));
            this.vector = rightVector;
            this.size = size;
            this.length = size * size;
        } else {
            this.vector = new float[0];
            this.size = 0;
            this.length = 0;
        }
    }

    public int getSize() {
        return size;
    }

    public int getLength() {
        return length;
    }

    public float[] getVector() {
        return vector;
    }

    public float get(final int index) {
        float element;

        try {
            element = vector[index];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        
        return element;
    }

    public void set(final int index, final float value) {
        if (index >= 0 && index < getVector().length) {
            vector[index] = value;
        }
    }

    public void setData(float[] data) {
        if (data.length == length) {
            this.vector = data;
        } else {
            float[] rightVector = new float[size];
            System.arraycopy(data, 0, rightVector, 0, Math.min(data.length, size));
            this.vector = rightVector;
        }
    }

    public boolean isEqualSize(final Matrix matrix2) {
        return this.getLength() == matrix2.getLength();
    }

    public boolean isEqualMatrix(final Matrix matrix2) {
        if (isEqualSize(matrix2)) {
            for (int index = 0; index < this.getSize(); index++) {
                if (Math.abs(this.get(index) - matrix2.get(index)) >= EPS) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    // проверяет, является ли матрица единичной
    public static boolean isSingleMatrix(final Matrix matrix, final float eps) {
        float firstElement = matrix.get(0);
        int indexMainDiagonal = 0;

        if (Math.abs(firstElement) < eps) {
            return false;
        }

        for (int index = 0; index < matrix.getLength(); index++) {
            if (index == indexMainDiagonal * matrix.getSize() + indexMainDiagonal) {
                if (Math.abs(matrix.get(index) - firstElement) >= eps) {
                    return false;
                }

                indexMainDiagonal++;
            } else if (Math.abs(matrix.get(index)) >= eps) {
                return false;
            }
        }

        return true;
    }

    public abstract Matrix createSingleMatrix(final float value);

    public abstract Matrix createSingleMatrix();

    public abstract Matrix getZeroMatrix(final int size);

    public abstract Vector getZeroVector(final int size);

    // транспонирует матрицу
    public static Matrix transposeMatrix(Matrix matrix) {
        int indexCol = 0, indexRow = 0;
        int size = matrix.getSize();

        for (int index = 0; index < size - 1; index++) {
            indexCol = index + 1;
            indexRow = index;

            while (indexCol < size) {
                matrix.changeElements(indexRow * size + indexCol, indexCol * size + indexRow);
                indexCol++;
            }
        }
        return matrix;
    }


    // складывает две матрицы
    public static Matrix sumMatrix(final Matrix matrix1, final Matrix matrix2) throws MatrixException {
        Matrix matrix = matrix1.getZeroMatrix(matrix1.size);

        if (matrix1.isEqualSize(matrix2)) {
            for (int index = 0; index < matrix.getLength(); index++) {
                matrix.getVector()[index] = matrix1.get(index) + matrix2.get(index);
            }
        } else {
            throw new MatrixException("Matrices of different sizes can't be summed");
        }

        return matrix;
    }

    public static Matrix minusMatrix(final Matrix matrix1, final Matrix matrix2) throws MatrixException {
        Matrix matrix = matrix1.getZeroMatrix(matrix1.size);

        if (matrix1.isEqualSize(matrix2)) {
            for (int index = 0; index < matrix.getLength(); index++) {
                matrix.getVector()[index] = matrix1.get(index) - matrix2.get(index);
            }
        } else {
            throw new MatrixException("Matrices of different sizes can't be summed");
        }

        return matrix;
    }

    public void multiplyOnValue(final float value) {
        for (int index = 0; index < this.getLength(); index++) {
            this.set(index, this.get(index) * value);
        }
    }

    public void divideOnValue(final float value) throws MatrixException {
        if (Math.abs(value) < EPS) {
            throw new Matrix.MatrixException("Division by zero");
        }

        multiplyOnValue(1.0f / value);
    }

    public Vector multiplyOnVector(final Vector vector) throws MatrixException {
        if (this.getSize() != vector.getSize()) {
            throw new MatrixException("Different sizes can't be multiplyd");
        }

        Vector result = vector.getZeroVector(size);
        int indexShift = 0;

        for (int indexRow = 0; indexRow < size; indexRow++) {
            indexShift = 0;

            while (indexShift < size) {
                result.getVector()[indexRow] += this.get(indexRow * size + indexShift) *
                        vector.get(indexShift);
                indexShift++;
            }
        }

        return result;
    }

    public static Matrix multiplyMatrices(final Matrix matrix1, final Matrix matrix2) throws MatrixException {
        if (!matrix1.isEqualSize(matrix2)) {
            throw new MatrixException("Different sizes can't be multiplyd");
        }

        Matrix matrix = matrix1.getZeroMatrix(matrix1.getSize());
        final int size = matrix.getSize();
        int indexShift = 0, indexCol = 0, indexRow = 0;

        for (int index = 0; index < matrix.getLength(); index++) {
            indexShift = 0;
            indexCol = index % size;
            indexRow = index / size;

            while (indexShift < size) {
                matrix.getVector()[index] +=
                        matrix1.get(indexRow * size + indexShift) * matrix2.get(indexShift * size + indexCol);
                indexShift++;
            }
        }

        return matrix;
    }

    // реализует поиск детерминанта
    public float getDeterminant() {
        final float detRatio = this.getTriangleMatrix();
        float determinant = 1;

        for (int index = 0; index < size; index++) {
            determinant *= this.get(index * size + index);
        }

        if (detRatio != 0) {
            determinant /= detRatio;
        } else {
            determinant = 0;
        }

        return determinant;
    }

    // превращения исходной матрицы в треугольную
    private float getTriangleMatrix() {
        int indexCol = 0;
        int changingIndexRow = 0;
        float detRatio = 1;

        for (int index = 0; index < size - 1; index++) {
            indexCol = 0;
            changingIndexRow = this.getChangedIndexRow(index);

            if (changingIndexRow == -2) {
                detRatio = 0;
                continue;
            } else if (changingIndexRow != -1) {
                while (indexCol < size) {
                    this.changeElements(index * size + indexCol, changingIndexRow * size + indexCol);
                    indexCol++;
                }

                detRatio *= -1;
            }

            detRatio *= this.getZeroCol(index);
        }

        return detRatio;
    }

    // преобразует исходную матрицу путём обнуления столбца по элементу с переданным индексом
    private float getZeroCol(final int index) {
        int indexNextRow = index + 1;
        float ratio, ratioNextRow, ratioActualRow = this.get(index * size + index);
        float detRatio = 1;

        while (indexNextRow < size) {
            ratio = getRatio(this.get(indexNextRow * size + index), this.get(index * size + index));
            ratioNextRow = this.get(indexNextRow * size + index);

            if (ratio % 1 != 0) {
                this.multiplyOnRatio(ratioNextRow, ratioActualRow, index, indexNextRow, 0);
                detRatio *= ratioActualRow;
            } else {
                this.multiplyOnRatio(ratio, 1, index, indexNextRow, 0);
            }

            indexNextRow++;
        }

        return detRatio;
    }

    // преобразует строку
    private void multiplyOnRatio(
            final float ratioNextRow, final float ratioActualRow,
            final int index, final int indexRow, int indexCol) {

        while (indexCol < size) {
            int actualIndex = indexRow * size + indexCol;
            int prevIndex = index * size + indexCol;

            this.getVector()[actualIndex] = this.get(actualIndex) * ratioActualRow -
                    this.get(prevIndex) * ratioNextRow;
            indexCol++;
        }
    }

    private int getChangedIndexRow(final int index) {
        int changingIndexRow = -1;
        int actualIndex = index * size + index;
        float minValue = Math.abs(this.get(actualIndex));

        for (int indexRow = index + 1; indexRow < size; indexRow++) {
            actualIndex = indexRow * size + index;
            if (this.get(actualIndex) != 0 && (minValue == 0 || Math.abs(this.get(actualIndex)) < minValue)) {
                minValue = this.get(actualIndex);
                changingIndexRow = indexRow;
            }
        }
        if (minValue == 0) {
            return -2;
        }

        return changingIndexRow;
    }

    // Метод получения обратной матрицы. Изначальную матрицу умножает на единичную, затем превращает изначальную
    // матрицу в треугольную с параллельным преобразованием единичной, затем вызывает обратный ход метода
    public static Matrix getInvertedMatrix(final Matrix matrix) throws MatrixException {
        Matrix unitMatrix = matrix.createSingleMatrix();
        float ratio, ratioNextRow, ratioActualRow;
        int indexCol, indexRow, changingIndexRow;
        int size = matrix.getSize();

        for (int index = 0; index < size - 1; index++) {
            indexCol = index;
            indexRow = index + 1;
            changingIndexRow = matrix.getChangedIndexRow(index);

            if (changingIndexRow == -2) {
                throw new MatrixException("Matrix hasn't inverse matrix");
            } else if (changingIndexRow != -1) {
                while (indexCol < size) {
                    matrix.changeElements(index * size + indexCol, changingIndexRow * size + indexCol);
                    indexCol++;
                }
            }

            ratioActualRow = matrix.get(index * size + index);

            while (indexRow < size) {
                ratio = getRatio(matrix.get(indexRow * size + index), matrix.get(index * size + index));
                ratioNextRow = matrix.get(indexRow * size + index);

                if (ratio % 1 != 0) {
                    matrix.multiplyOnRatio(ratioNextRow, ratioActualRow, index, indexRow, 0);
                    unitMatrix.multiplyOnRatio(ratioNextRow, ratioActualRow, index, indexRow, 0);
                } else {
                    matrix.multiplyOnRatio(ratio, 1, index, indexRow, 0);
                    unitMatrix.multiplyOnRatio(ratio, 1, index, indexRow, 0);
                }

                indexRow++;
            }
        }

        return reversePassOfInverseMatrixMethod(matrix, unitMatrix);
    }

    // обратный ход matrix преобразует в единичную матрицу, а unitMatrix - в искомую обратную матрицу.
    private static Matrix reversePassOfInverseMatrixMethod(final Matrix matrix, final Matrix unitMatrix) {
        int indexRow, indexCol;
        int size = matrix.getSize();
        float ratio;

        for (int index = matrix.getSize() - 1; index >= 0; index--) {
            indexRow = index - 1;
            indexCol = 0;
            ratio = getRatio(1, matrix.get(index * size + index));

            while (ratio != 1 && indexCol < size) {
                matrix.getVector()[index * size + indexCol] *= ratio;
                unitMatrix.getVector()[index * size + indexCol] *= ratio;
                indexCol++;
            }

            while (indexRow >= 0) {
                indexCol = 0;
                ratio = getRatio(matrix.get(indexRow * size + index), matrix.get(index * size + index));
                matrix.getVector()[indexRow * size + index] -= ratio;

                while (indexCol < matrix.getSize()) {
                    unitMatrix.getVector()[indexRow * size + indexCol] -= unitMatrix.get(index * size + indexCol) * ratio;
                    indexCol++;
                }

                indexRow--;
            }
        }

        return unitMatrix;
    }

    private void changeElements(final int index, final int changingIndex) {
        final float changingValue = this.get(index);
        this.getVector()[index] = this.get(changingIndex);
        this.getVector()[changingIndex] = changingValue;
    }

    // решает систему линейных уравнений методом Гаусса
    public static Vector solveByGaussMethod(final Matrix matrix, final Vector vector) throws MatrixException {
        float ratio, ratioNextRow, ratioActualRow;
        int indexCol, indexRow, changingIndexRow;
        int size = matrix.getSize();

        for (int index = 0; index < size - 1; index++) {
            indexCol = index;
            indexRow = index + 1;
            changingIndexRow = matrix.getChangedIndexRow(index);

            if (changingIndexRow == -2) {
                continue;
            } else if (changingIndexRow != -1) {
                while (indexCol < size) {
                    matrix.changeElements(index * size + indexCol, changingIndexRow * size + indexCol);
                    indexCol++;
                }

                vector.changeElements(index, changingIndexRow);
            }

            while (indexRow < size) {
                ratio = getRatio(matrix.get(indexRow * size + index), matrix.get(index * size + index));
                ratioNextRow = matrix.get(indexRow * size + index);
                ratioActualRow = matrix.get(index * size + index);

                if (ratio % 1 == 0) {
                    matrix.multiplyOnRatio(ratio, 1, index, indexRow, index);
                    vector.getVector()[indexRow] = vector.get(indexRow) - vector.get(index) * ratio;
                } else {
                    matrix.multiplyOnRatio(ratioNextRow, ratioActualRow, index, indexRow, index);
                    vector.getVector()[indexRow] = vector.get(indexRow) * ratioActualRow -
                            vector.get(index) * ratioNextRow;

                }

                indexRow++;
            }
        }

        return reversePassOfGaussMethod(matrix, vector);
    }

    // обратный ход метода Гаусса
    private static Vector reversePassOfGaussMethod(final Matrix matrix, final Vector vector) throws MatrixException {
        Vector solveVector = vector.getZeroVector(vector.getSize());
        int indexCol;
        int size = matrix.getSize();
        float sum, matrixValue, vectorValue;

        for (int index = size - 1; index >= 0; index--) {
            sum = 0;
            indexCol = size - 1;

            while (indexCol > index) {
                matrixValue = matrix.get(index * size + indexCol);
                vectorValue = solveVector.get(indexCol);
                sum += matrixValue * vectorValue;
                indexCol--;
            }

            if (matrix.get(index * size + index) == 0 && ((sum == 0 && vector.get(index) != 0)
                    || (sum != 0 && vector.get(index) == 0))) {
                throw new MatrixException("There are no solves");
            } else if (matrix.get(index * size + index) == 0) {
                solveVector.set(index, 1);
                continue;
            }

            solveVector.getVector()[index] = (vector.get(index) - sum) / matrix.get(index * size + index);
        }

        return solveVector;
    }

    private static float getRatio(final float value1, final float value2) {
        if (Math.abs(value2) < EPS) {
            throw new ArithmeticException("Division by zero");
        }

        return value1 / value2;
    }
}
