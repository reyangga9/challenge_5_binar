package com.example.Challenge_4.mvc.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SimpleStringUtils {

//    simplestring ada yang dari springboot juga

    public Pageable getShort(String orderby, String ordertype, Integer page, Integer size) {
        Pageable show_data;
        if (orderby != null) {
            if (ordertype != null) {
                if (ordertype.equals("desc")) {
                    return show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
                } else {
                    return    show_data = PageRequest.of(page, size, Sort.by(orderby).ascending());
                }
            } else {
                return  show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
            }

        } else {
            return  show_data = PageRequest.of(page, size, Sort.by("id").descending());
        }

    }

}
