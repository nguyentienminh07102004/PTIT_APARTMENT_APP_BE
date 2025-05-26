package com.apartmentbuilding.PTIT.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    public static Pageable pagination(Integer page, Integer limit) {
        if (page == null || page < 1) page = 1;
        if (limit == null || limit < 0) limit = 10;
        return PageRequest.of(page - 1, limit);
    }
    public static Pageable pagination(Integer page, Integer limit, Sort sort) {
        return ((PageRequest)(pagination(page, limit))).withSort(sort);
    }
}
