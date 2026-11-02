package exception;

/**
 * Lançada ao tentar cadastrar um aluno com matrícula que já existe.
 */
public class MatriculaDuplicadaException extends RuntimeException {

    public MatriculaDuplicadaException(String matricula) {
        super("Já existe um aluno com a matrícula " + matricula + ".");
    }
}