package studentmanager.model;

/**
 * Possíveis situações acadêmicas de um aluno.
 */
public enum SituacaoAcademica {

    APROVADO("Aprovado"),
    RECUPERACAO("Recuperação"),
    REPROVADO("Reprovado");

    private final String descricao;

    SituacaoAcademica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}