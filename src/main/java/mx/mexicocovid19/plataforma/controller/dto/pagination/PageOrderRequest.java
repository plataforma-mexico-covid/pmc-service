package mx.mexicocovid19.plataforma.controller.dto.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageOrderRequest {
    private int column;
    private String dir;
}
