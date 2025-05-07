package com.apartmentbuilding.PTIT.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaginationUtils {
    public static Pageable pagination(Integer page, Integer limit) {
        if (page == null) page = 1;
        if (limit == null) limit = 10;
        return PageRequest.of(page - 1, limit);
    }
    public static Pageable pagination(Integer page, Integer limit, Map<String, Sort.Direction> sort) {
        List<Sort.Order> sorts = new ArrayList<>();
        for (Map.Entry<String, Sort.Direction> entry : sort.entrySet()) {
            Sort.Order order = new Sort.Order(entry.getValue(), entry.getKey());
            sorts.add(order);
        }
        return ((PageRequest)(pagination(page, limit))).withSort(Sort.by(sorts));
    }
}
