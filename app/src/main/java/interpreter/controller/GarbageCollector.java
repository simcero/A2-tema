package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}
    /** The addresses that occur in the RefValues of the SymTable. */
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }
    /** UNSAFE: keeps only the heap entries referred to directly from the SymTable. */
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /** All heap addresses reachable transitively starting from the given root addresses. */
    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed;
        do {
            changed = false;
            Set<Integer> discovered = reachable.stream()
                    .map(heap::get)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).address())
                    .filter(addr -> !reachable.contains(addr))
                    .collect(Collectors.toSet());
            if (!discovered.isEmpty()) {
                reachable.addAll(discovered);
                changed = true;
            }
        } while (changed);
        return reachable;
    }
    /** SAFE: keeps all heap entries reachable directly from SymTable or transitively through heap RefValues. */
    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr,
                                                            Map<Integer, IValue> heap) {
        Set<Integer> liveAddresses = reachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> liveAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
