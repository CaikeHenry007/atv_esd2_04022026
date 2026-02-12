
public class Usuario {
    private int id;
    private String nome;
    private int contadorPosts;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.contadorPosts = 0;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void incrementarContadorPosts() {
        contadorPosts++;
    }
}