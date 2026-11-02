package studentmanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um aluno do sistema escolar.
 */
public class Aluno {

    public static final int MIN_NOTAS = 2;
    public static final int MAX_NOTAS = 3;
    public static final double NOTA_MINIMA = 0.0;
    public static final double NOTA_MAXIMA = 10.0;

    private String nome;
    private int idade;
    private final String matricula;
    private String curso;
    private final List<Double> notas;

    public Aluno(String nome, int idade, String matricula, String curso) {
        setNome(nome);
        setIdade(idade);
        setCurso(curso);
        this.matricula = validarTexto(matricula, "Matrícula");
        this.notas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = validarTexto(nome, "Nome");
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 1 || idade > 120) {
            throw new IllegalArgumentException("Idade deve estar entre 1 e 120.");
        }
        this.idade = idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = validarTexto(curso, "Curso/turma");
    }

    public List<Double> getNotas() {
        return Collections.unmodifiableList(notas);
    }

    public void registrarNota(double nota) {
        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
            throw new IllegalArgumentException(
                "A nota deve estar entre " + NOTA_MINIMA + " e " + NOTA_MAXIMA + ".");
        }
        if (notas.size() >= MAX_NOTAS) {
            throw new IllegalStateException(
                "O aluno já possui o máximo de " + MAX_NOTAS + " notas.");
        }
        notas.add(nota);
    }

    public boolean possuiNotas() {
        return !notas.isEmpty();
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio.");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return String.format("Matrícula: %s | Nome: %s | Idade: %d | Curso: %s | Notas: %s",
                matricula, nome, idade, curso, notas);
    }
}