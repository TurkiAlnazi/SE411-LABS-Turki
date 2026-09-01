package lab02;

import java.util.ArrayList;
import java.util.List;

public class Pipeline<T, R> {

    private List<Transformer<?, ?>> transformers;

    public Pipeline() {
        transformers = new ArrayList<>();
    }

    private Pipeline(List<Transformer<?, ?>> transformers) {
        this.transformers = transformers;
    }

    public <V> Pipeline<T, V> add(Transformer<R, V> transformer) {

        List<Transformer<?, ?>> newTransformers =
                new ArrayList<>(transformers);

        newTransformers.add(transformer);

        return new Pipeline<>(newTransformers);
    }

    @SuppressWarnings("unchecked")
    public R execute(T input) {

        Object result = input;

        for (Transformer<?, ?> transformer : transformers) {

            Transformer<Object, Object> current =
                    (Transformer<Object, Object>) transformer;

            result = current.transform(result);
        }

        return (R) result;
    }
}
