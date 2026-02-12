import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SistemaPostsBlog {

    private Map<Integer, Usuario> usuarios;
    private Map<Integer, List<Post>> postsPorUsuario;

    public SistemaPostsBlog() {
        this.usuarios = new HashMap<>();
        this.postsPorUsuario = new HashMap<>();
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.putIfAbsent(usuario.getId(), usuario);
        postsPorUsuario.putIfAbsent(usuario.getId(), new ArrayList<>());
    }

    public void adicionarPost(int usuarioId, String conteudo) {
        Usuario usuario = usuarios.get(usuarioId);
        if (usuario != null) {
            Post novoPost = new Post(usuarioId, conteudo, System.currentTimeMillis());
            postsPorUsuario.get(usuarioId).add(novoPost);
            usuario.incrementarContadorPosts();
        }
    }

    public PostsExtremos obterPrimeiroEUltimoPost(int usuarioId) {
        List<Post> posts = postsPorUsuario.get(usuarioId);

        if (posts == null || posts.isEmpty()) {
            return null;
        }

        Post primeiro = posts.get(0);
        Post ultimo = posts.get(posts.size() - 1);

        return new PostsExtremos(primeiro, ultimo);
    }

    public int contarPostsDoUsuario(int usuarioId) {
        List<Post> posts = postsPorUsuario.get(usuarioId);
        return posts == null ? 0 : posts.size();
    }

    public String gerarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DO BLOG ===\n\n");

        for (Usuario usuario : usuarios.values()) {

            relatorio.append("Usuário: ").append(usuario.getNome()).append("\n");

            int totalPosts = contarPostsDoUsuario(usuario.getId());
            relatorio.append("Total de posts: ").append(totalPosts).append("\n");

            PostsExtremos extremos = obterPrimeiroEUltimoPost(usuario.getId());

            if (extremos != null) {
                relatorio.append("Primeiro post: ")
                        .append(extremos.getPrimeiro().getConteudo())
                        .append("\n");

                relatorio.append("Último post: ")
                        .append(extremos.getUltimo().getConteudo())
                        .append("\n");
            }

            relatorio.append("\n");
        }

        return relatorio.toString();
    }
}
