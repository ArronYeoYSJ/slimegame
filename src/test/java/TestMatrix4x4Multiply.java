import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import com.arronyeoman.maths.Matrix4x4;
import java.util.Arrays;

public class TestMatrix4x4Multiply {

    @Test
    public void testMultiply() {
        float[] values1 = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f};
        float[] values2 = {16.0f, 15.0f, 14.0f, 13.0f, 12.0f, 11.0f, 10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f};
        Matrix4x4 matrix1 = new Matrix4x4(values1);
        Matrix4x4 matrix2 = new Matrix4x4(values2);
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.multiply(matrix1, matrix2);

        float[] expectedValues = {80.0f, 70.0f, 60.0f, 50.0f, 240.0f, 214.0f, 188.0f, 162.0f, 400.0f, 358.0f, 316.0f, 274.0f, 560.0f, 502.0f, 444.0f, 386.0f};
        Matrix4x4 expected = new Matrix4x4(expectedValues);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals((double)expected.get(i, j), (double)result.get(i, j), 0.0001);
            }
        }
    }
}
