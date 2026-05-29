package day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathTestJUnit {

    @Test
    void testAdd() {
        assertEquals(5.0f, MathTest.add(2.0f, 3.0f));
    }

    @Test
    void testSubtract() {
        assertEquals(2.0f, MathTest.subtract(5.0f, 3.0f));
    }

    @Test
    void testMultiply() {
        assertEquals(15.0f, MathTest.multiply(5.0f, 3.0f));
    }

    @Test
    void testDivide() {
        assertEquals(2.0f, MathTest.divide(6.0f, 3.0f));
    }

    @Test
    void testDivideByZero() {
        assertThrows(
                ArithmeticException.class,
                () -> MathTest.divide(5.0f, 0.0f)
        );
    }
}
