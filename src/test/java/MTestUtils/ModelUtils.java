package MTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelUtils {
    static ObjectMapper objectMapper;
    static boolean objectMapperInitted = false;

    private ModelUtils(){}

    public static void initObjectMapper(){
        if(objectMapperInitted){
            return;
        }
        objectMapper = new ObjectMapper();

    }
    public static <T> String jsonify(T toJsonify) throws JsonProcessingException {
        initObjectMapper();
        return objectMapper.writeValueAsString(toJsonify);
    }

    public static <M> List<M> duplicateList(List<M> toDup, ModelDuplicator<M> dupSingleLogic) {
        return toDup.stream().map(dupSingleLogic::duplicate).collect(Collectors.toList());
    }
    public static <K,V> Map<K,V> convertListToMap(List<V> items, KeyExtractor<K,V> keyExtractor){
        return items.stream().collect(Collectors.toMap(keyExtractor::extract, Function.identity()));
    }

    public static <K,V> Map<K,V> duplicateMap(Map<K,V> toDup, ModelDuplicator<V> dupSingleLogic, KeyExtractor<K,V> keyExtractor){
        return toDup.entrySet().stream().collect(Collectors.toMap(i -> keyExtractor.extract(i.getValue()), i -> dupSingleLogic.duplicate(i.getValue())));
    }


}
