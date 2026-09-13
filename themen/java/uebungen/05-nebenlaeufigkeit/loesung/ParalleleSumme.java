package de.makno.lernen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Summiert mehrere Listen parallel – ein Task je Liste, Pool wird sicher geschlossen. */
public final class ParalleleSumme {

    private ParalleleSumme() {}

    public static long summe(List<List<Integer>> listen) {
        if (listen.isEmpty()) {
            return 0;
        }
        try (ExecutorService pool = Executors.newFixedThreadPool(listen.size())) {
            List<Future<Long>> teilergebnisse = new ArrayList<>();
            for (List<Integer> liste : listen) {
                teilergebnisse.add(pool.submit(() -> liste.stream().mapToLong(Integer::longValue).sum()));
            }
            long summe = 0;
            for (Future<Long> teil : teilergebnisse) {
                summe += teil.get();
            }
            return summe;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Summierung unterbrochen", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Summierung fehlgeschlagen", e.getCause());
        }
    }
}
