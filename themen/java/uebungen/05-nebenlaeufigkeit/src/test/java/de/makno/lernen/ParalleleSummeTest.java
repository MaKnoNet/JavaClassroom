package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class ParalleleSummeTest {

    @Test
    void summiertMehrereListen() {
        List<List<Integer>> listen = List.of(List.of(1, 2, 3), List.of(10, 20), List.of(), List.of(100));
        assertEquals(136, ParalleleSumme.summe(listen));
    }

    @Test
    void summiertGrosseListenRichtig() {
        List<List<Integer>> listen = IntStream.range(0, 4)
                .mapToObj(i -> IntStream.rangeClosed(1, 1_000_000).boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());
        assertEquals(4L * 500_000_500_000L, ParalleleSumme.summe(listen));
    }
}
