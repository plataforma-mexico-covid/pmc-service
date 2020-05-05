package mx.mexicocovid19.plataforma.controller.dto.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageRequest {
    private int draw;
    private int length;
    private int start;
    private List<PageOrderRequest> order;
    private PageSearchRequest search;
}
