package ru.lanit.research.graphql.app.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import ru.lanit.research.graphql.domain.DomainObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BeanMerger {

    /**
     * Shallow merge полей POJO by BeanUtils
     * Принцип слияния:
     * base берется как основа для результата, потом в результат копируются все not-null поля из overrides;
     * копирование - неглубокое (т.е. без вложенных объектов).
     *
     * @param base      - исходные данные, которые берутся за основу для результата
     * @param overrides - источник переопределяющих значений.
     */
    public static void shallowMerge(Object overrides, Object base) {
        BeanUtils.copyProperties(overrides, base, getNullPropertyNames(overrides));
    }

    /**
     * Возвращает перечень имен свойств, у которых значение = null
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Deep merge by Jackson через JSON
     * Принцип слияния:
     * base берется как основа для результата, потом в результат копируются все not-null поля из overrides;
     * копирование - глубокое (по всем вложенным объектам).
     *
     * @param base      - исходные данные, которые берутся за основу для результата
     * @param overrides - источник переопределяющих значений.
     */
    public static Object deepMerge(Object overrides, DomainObject base) throws IOException, CloneNotSupportedException {
        assert overrides != null && base != null;

        log.debug("base: {}", base);
        log.debug("overrides: {}", overrides);

        // делаем копию, чтобы не испортить base
        ObjectMapper baseMapper = new ObjectMapper();
        DomainObject baseClone = baseMapper.readValue(baseMapper.writeValueAsString(base), base.getClass());

        ObjectReader baseReader = baseMapper.readerForUpdating(baseClone);
        ObjectMapper overridesMapper = new ObjectMapper();
        JsonNode overridesNode = overridesMapper.valueToTree(overrides);
        Object updated = baseReader.readValue(overridesNode);

        log.debug("updated: {}", updated);

        return updated;
    }
}
