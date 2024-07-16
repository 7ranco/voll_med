package med.voll.api.domain.consulta.validacionCancelacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacionCancelacion implements ValidadorCancelamientoConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DatosCancelamientoConsulta datosCancelamientoConsulta) {

        var consulta = repository.getReferenceById(datosCancelamientoConsulta.idConsulta());

        var ahora = LocalDateTime.now();

        var horaDeConsulta = consulta.getFecha();


        var diferenciaDe24h = Duration.between(ahora, horaDeConsulta).toHours() < 24;

        if (diferenciaDe24h){
            throw new ValidationException("Las consultas no pueden cancelarse con antecedencia minima de 24 h");
        }
    }
}
