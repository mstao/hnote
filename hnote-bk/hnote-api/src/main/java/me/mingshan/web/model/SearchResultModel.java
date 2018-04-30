package me.mingshan.web.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchResultModel<T> implements Serializable {
    private static final long serialVersionUID = -8259078539400546306L;

    private List<T> items;
    private Long total;
}
