package studentmanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import studentmanager.exception.MatriculaDuplicadaException;
import studentmanager.model.Aluno;
import studentmanager.model.Desempenho;
import studentmanager.model.RelatorioTurma;
import studentmanager.model.SituacaoAcademica;
import studentmanager.util.CalculadoraMedia;

/**
 * Regras de negócio e armazenamento dos alunos.
 */
@Service
public class AlunoService {

    private final List<Aluno> alunos = new ArrayList<>();

    public void cadastrar(String nome, int idade, String matricula, String curso) {
        if (buscarPorMatricula(matricula) != null) {
            throw new MatriculaDuplicadaException(matricula.trim());
        }
        alunos.add(new Aluno(nome, idade, matricula, curso));
    }

    public List<Aluno> listarTodos() {
        return Collections.unmodifiableList(alunos);
    }

    public Aluno buscarPorMatricula(String matricula) {
        if (matricula == null) {
            return null;
        }
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(matricula.trim())) {
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> buscarPorNome(String trecho) {
        List<Aluno> encontrados = new ArrayList<>();
        if (trecho == null || trecho.isBlank()) {
            return encontrados;
        }
        String termo = trecho.trim().toLowerCase();
        for (Aluno aluno : alunos) {
            if (aluno.getNome().toLowerCase().contains(termo)) {
                encontrados.add(aluno);
            }
        }
        return encontrados;
    }

    public void editar(String matricula, String novoNome, int novaIdade, String novoCurso) {
        Aluno aluno = obterOuFalhar(matricula);
        aluno.setNome(novoNome);
        aluno.setIdade(novaIdade);
        aluno.setCurso(novoCurso);
    }

    public void remover(String matricula) {
        Aluno aluno = obterOuFalhar(matricula);
        alunos.remove(aluno);
    }

    public void registrarNota(String matricula, double nota) {
        Aluno aluno = obterOuFalhar(matricula);
        aluno.registrarNota(nota);
    }

    public Desempenho consultarDesempenho(String matricula) {
        Aluno aluno = obterOuFalhar(matricula);
        if (aluno.getNotas().size() < Aluno.MIN_NOTAS) {
            throw new IllegalStateException(
                "São necessárias pelo menos " + Aluno.MIN_NOTAS + " notas para calcular o desempenho.");
        }
        double media = CalculadoraMedia.calcularMedia(aluno.getNotas());
        SituacaoAcademica situacao = CalculadoraMedia.definirSituacao(media);
        return new Desempenho(aluno, media, situacao);
    }

    public RelatorioTurma gerarRelatorio() {
        int aprovados = 0;
        int emRecuperacao = 0;
        int reprovados = 0;
        List<Double> medias = new ArrayList<>();

        for (Aluno aluno : alunos) {
            if (aluno.getNotas().size() < Aluno.MIN_NOTAS) {
                continue; // ainda sem desempenho
            }
            Desempenho desempenho = consultarDesempenho(aluno.getMatricula());
            medias.add(desempenho.media());

            switch (desempenho.situacao()) {
                case APROVADO -> aprovados++;
                case RECUPERACAO -> emRecuperacao++;
                case REPROVADO -> reprovados++;
            }
        }

        double mediaGeral = medias.isEmpty() ? 0.0 : CalculadoraMedia.calcularMediaGeral(medias);
        return new RelatorioTurma(alunos.size(), medias.size(),
                aprovados, emRecuperacao, reprovados, mediaGeral);
    }

    public int quantidade() {
        return alunos.size();
    }

    // Evita repetir "buscar e lançar erro se não achar"
    private Aluno obterOuFalhar(String matricula) {
        Aluno aluno = buscarPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado: " + matricula);
        }
        return aluno;
    }
}