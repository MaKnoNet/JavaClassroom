package de.makno.lernen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ZaehlerTest {

    private static final int THREADS = 8;
    private static final int ERHOEHUNGEN_PRO_THREAD = 100_000;

    @Test
    void verliertKeineErhoehungenUnterLast() throws Exception {
        Zaehler zaehler = new Zaehler();
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        List<Future<?>> laeufe = new ArrayList<>();
        for (int t = 0; t < THREADS; t++) {
            laeufe.add(pool.submit(() -> {
                for (int i = 0; i < ERHOEHUNGEN_PRO_THREAD; i++) {
                    zaehler.erhoehe();
                }
            }));
        }
        for (Future<?> lauf : laeufe) {
            lauf.get();
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(THREADS * ERHOEHUNGEN_PRO_THREAD, zaehler.wert());
    }
}
