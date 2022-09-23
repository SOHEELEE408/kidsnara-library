package com.kidsnara.library.controller;

import com.kidsnara.library.constant.EnumMapper;
import com.kidsnara.library.constant.EnumModel;
import com.kidsnara.library.constant.EnumValue;
import com.kidsnara.library.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @GetMapping("/enum")
    public Map<String, Object> getEnum(){
        Map<String, Object> enums = new LinkedHashMap<>();
        Class role = Role.class;
        enums.put("Role", role.getEnumConstants());
        return enums;
    }

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper(){
        return enumMapper.getAll();
    }
}
