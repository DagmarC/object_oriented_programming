package com.cervenkova.mapper;

import java.util.Map;

@FunctionalInterface
public interface RowMapper<T> {
    T map(Map<String, Object> row);
}
