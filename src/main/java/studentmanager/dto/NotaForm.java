package studentmanager.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Nota digitada no formulário de registro de notas.
 */
public class NotaForm {

    @NotNull(message = "A nota é obrigatória.")
    @DecimalMin(value = "0.0", message = "A nota deve ser de no mínimo 0.")
    @DecimalMax(value = "10.0", message = "A nota deve ser de no máximo 10.")
    private Double nota;

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}