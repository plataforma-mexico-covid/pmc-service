package mx.mexicocovid19.plataforma.controller.dto.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSearchRequest {
    private String value;
    private boolean regex;
}
