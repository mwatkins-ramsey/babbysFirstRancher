package MTestUtils;

@FunctionalInterface
public interface KeyExtractor<K, V> {
    K extract(V hasKey);
}
