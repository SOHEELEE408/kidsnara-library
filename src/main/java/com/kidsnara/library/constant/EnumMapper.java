package com.kidsnara.library.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * enum을 DTO(EnumValue)로 변환하는 작업
 * (애플리케이션 실행 시점에 한 번만 실행될 수 있도록 함)
 */
public class EnumMapper {

    private Map<String, List<EnumValue>> factory = new HashMap<>();

    private List<EnumValue> toEnumValue(Class<? extends EnumModel> e) {
        List<EnumValue> enumValues = Arrays.stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
        return enumValues;
    }

    public void put(String key, Class<? extends EnumModel> e){
        factory.put(key, toEnumValue(e));
    }

    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }

    public Map<String, List<EnumValue>> get(String keys){
        Map<String, List<EnumValue>> roleMap = Arrays.stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));

        return roleMap;
    }

}
