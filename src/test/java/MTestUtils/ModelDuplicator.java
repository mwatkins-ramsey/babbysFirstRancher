package MTestUtils;

@FunctionalInterface
public interface ModelDuplicator<M> {
    M duplicate(M toDup);
}
