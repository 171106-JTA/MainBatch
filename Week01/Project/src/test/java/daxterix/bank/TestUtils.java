package daxterix.bank;

import java.util.List;

import static org.junit.Assert.*;

public class TestUtils {
    public static <T> void assertMatchingContents(List<T> list1, List<T> list2) {
        assertEquals(list1.size(), list2.size());
        for (T o: list2)
            assertTrue(list1.contains(o));
    }
}
