package com.kidsnara.library.constant;

/**
 * EnumController에서 Response로 사용할 DTO
 */
public class EnumValue {

    private String key;
    private String value;

    public EnumValue(EnumModel enumModel){
        key = enumModel.getKey();
        value = enumModel.getValue();
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
