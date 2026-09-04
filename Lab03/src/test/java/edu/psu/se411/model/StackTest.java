package edu.psu.se411.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class StackTest {

    // Test normal push and pop behavior
    @Test
    void testPushAndPop() {

        Stack<String> stringStack = new Stack<>();

        stringStack.push("Z");
        stringStack.push("A");

        assertEquals("A", stringStack.pop());
    }

    // Test popping from an empty stack
    @Test
    void pop_empty_stack() {

        Stack<String> stringStack = new Stack<>();

        NoSuchElementException thrown =
                assertThrows(
                        NoSuchElementException.class,
                        () -> stringStack.pop(),
                        "Expected pop from empty Stack to throw, but it didn't"
                );

        assertEquals("Stack is empty, cannot pop", thrown.getMessage());
    }

    // Test LIFO: elements should be popped in reverse order
    @Test
    void elementsArePoppedInReverseOrder() {

        Stack<String> stringStack = new Stack<>();

        stringStack.push("A");
        stringStack.push("B");
        stringStack.push("C");

        assertEquals("C", stringStack.pop());
        assertEquals("B", stringStack.pop());
        assertEquals("A", stringStack.pop());
    }

    // Edge case: null value
    @Test
    void pushNullValue() {

        Stack<String> stringStack = new Stack<>();

        stringStack.push(null);

        assertNull(stringStack.pop());
    }

    // Edge case: empty string
    @Test
    void pushEmptyString() {

        Stack<String> stringStack = new Stack<>();

        stringStack.push("");

        assertEquals("", stringStack.pop());
    }

    // Test state changes across multiple calls
    @Test
    void stackStateChangesAcrossMultipleCalls() {

        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.pop());

        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(10, stack.pop());
    }

    // Test constructor with positive capacity
    @Test
    void positiveCapacity() {

        Stack<String> stack = new Stack<>(2);

        stack.push("A");
        stack.push("B");

        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
    }

    // Edge case: zero capacity
    @Test
    void zeroCapacity() {

        Stack<String> stack = new Stack<>(0);

        stack.push("A");

        assertEquals("A", stack.pop());
    }

    // Edge case: negative capacity
    @Test
    void negativeCapacity() {

        Stack<String> stack = new Stack<>(-5);

        stack.push("A");

        assertEquals("A", stack.pop());
    }

    // Boundary case: initial capacity does not limit stack size
    @Test
    void stackCanGrowBeyondInitialCapacity() {

        Stack<String> stack = new Stack<>(1);

        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
    }

    // Additional: pushing multiple nulls and interleaving values preserves order
    @Test
    void multipleNullsAndValuesPreserveOrder() {
        Stack<String> stack = new Stack<>();

        stack.push("first");
        stack.push(null);
        stack.push("second");
        stack.push(null);

        assertNull(stack.pop()); // last pushed null
        assertEquals("second", stack.pop());
        assertNull(stack.pop()); // earlier null
        assertEquals("first", stack.pop());
    }

    // Additional: after a pop attempt that throws, stack remains usable
    @Test
    void popThrowsThenStackStillUsable() {
        Stack<Integer> stack = new Stack<>();

        assertThrows(NoSuchElementException.class, () -> stack.pop());

        stack.push(42);
        assertEquals(42, stack.pop());
    }

    // Additional: pushing a large number of elements to ensure growth doesn't fail
    @Test
    void pushManyElements() {
        Stack<Integer> stack = new Stack<>(1);
        final int COUNT = 1000;
        for (int i = 0; i < COUNT; i++) {
            stack.push(i);
        }

        for (int i = COUNT - 1; i >= 0; i--) {
            assertEquals(i, stack.pop());
        }
    }
}