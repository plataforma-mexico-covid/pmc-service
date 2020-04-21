package mx.mexicocovid19.plataforma.controller.mapper;

import mx.mexicocovid19.plataforma.controller.dto.DisciplinaDTO;
import mx.mexicocovid19.plataforma.model.entity.Disciplina;

import java.util.List;
import java.util.stream.Collectors;

public class DisciplinaMapper {
    public static DisciplinaDTO from(final Disciplina disciplina) {
        final DisciplinaDTO disciplinaDTO = new DisciplinaDTO();
        disciplinaDTO.setId(disciplina.getId());
        disciplinaDTO.setNombre(disciplina.getNombre());
        return disciplinaDTO;
    }

    public static Disciplina from(final DisciplinaDTO disciplinaDTO) {
        final Disciplina disciplina = new Disciplina();
        disciplina.setId(disciplinaDTO.getId());
        disciplina.setNombre(disciplinaDTO.getNombre());
        return disciplina;
    }

    public static List<DisciplinaDTO> from(final List<Disciplina> disciplinas) {
        return disciplinas.stream().map(DisciplinaMapper::from).collect(Collectors.toList());
    }
}
