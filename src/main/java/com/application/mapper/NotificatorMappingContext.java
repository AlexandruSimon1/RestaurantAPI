package com.application.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

//Is used for mapping the data before every mapping request that is made and can be used only by abstract classes
public class NotificatorMappingContext {
    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType){
        return targetType.cast(knownInstances.get(source));
    }

    @BeforeMapping
    public void storedMappedInstance(Object source, @MappingTarget Object target){
        knownInstances.put(source, target);
    }
}
