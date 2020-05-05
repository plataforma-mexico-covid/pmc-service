package mx.mexicocovid19.plataforma.controller.dto.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private int draw;
    private long recordsFiltered;
    private long recordsTotal;
    private List<T> data;
}
