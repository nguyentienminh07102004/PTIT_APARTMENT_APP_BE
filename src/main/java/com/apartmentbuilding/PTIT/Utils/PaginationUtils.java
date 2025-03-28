package com.apartmentbuilding.PTIT.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {
    public static Pageable pagination(Integer page, Integer limit) {
        if (page == null) page = 1;
        if (limit == null) limit = 10;
        return PageRequest.of(page - 1, limit);
    }
}
