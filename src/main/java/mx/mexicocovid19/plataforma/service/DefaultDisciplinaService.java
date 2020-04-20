package mx.mexicocovid19.plataforma.service;

import mx.mexicocovid19.plataforma.model.entity.Disciplina;
import mx.mexicocovid19.plataforma.model.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultDisciplinaService implements DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Override
    public List<Disciplina> readDisciplina() {
        return disciplinaRepository.findAll();
    }
}
