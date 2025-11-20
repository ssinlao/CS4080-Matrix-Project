import java.util.*;

class Matrix {
    private int rows;
    private int cols;
    private float[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new float[rows][cols];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public float[][] getData() { return data; }

    public void setElement(int i, int j, float value) {
        data[i][j] = value;
    }

    public float getElement(int i, int j) {
        return data[i][j];
    }

    public static Matrix add(Matrix m1, Matrix m2) throws IllegalArgumentException {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException(
                "Matrix dimensions must match for addition. " +
                "Matrix 1: " + m1.rows + "x" + m1.cols + ", " +
                "Matrix 2: " + m2.rows + "x" + m2.cols
            );
        }

        Matrix result = new Matrix(m1.rows, m1.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                result.data[i][j] = m1.data[i][j] + m2.data[i][j];
            }
        }
        return result;
    }

    public static Matrix subtract(Matrix m1, Matrix m2) throws IllegalArgumentException {
        if (m1.rows != m2.rows || m1.cols != m2.cols) {
            throw new IllegalArgumentException(
                "Matrix dimensions must match for subtraction. " +
                "Matrix 1: " + m1.rows + "x" + m1.cols + ", " +
                "Matrix 2: " + m2.rows + "x" + m2.cols
            );
        }

        Matrix result = new Matrix(m1.rows, m1.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                result.data[i][j] = m1.data[i][j] - m2.data[i][j];
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) throws IllegalArgumentException {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException(
                "Matrix 1 columns must equal Matrix 2 rows for multiplication. " +
                "Matrix 1: " + m1.rows + "x" + m1.cols + ", " +
                "Matrix 2: " + m2.rows + "x" + m2.cols
            );
        }

        Matrix result = new Matrix(m1.rows, m2.cols);
        for (int i = 0; i < m1.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                float sum = 0;
                for (int k = 0; k < m1.cols; k++) {
                    sum += m1.data[i][k] * m2.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public void fillRandom() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = rand.nextFloat() * 100;
            }
        }
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%.2f\t", data[i][j]);
            }
            System.out.println();
        }
    }
}