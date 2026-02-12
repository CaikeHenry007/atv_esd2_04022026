public class SistemaPostsBlog {
    
    // GARGALO 1:
    // Lista para armazenar usuários.
    // Toda busca por ID exige percorrer toda a lista → O(N)
    private List<Usuario> usuarios;
    
    // GARGALO 2:
    // Todos os posts ficam em uma única lista.
    // Para achar posts de um usuário é necessário percorrer TODOS os posts → O(N*M)
    private List<Post> todosOsPosts;
    
    public SistemaPostsBlog() {
        this.usuarios = new ArrayList<>();
        this.todosOsPosts = new ArrayList<>();
    }
    
    public void adicionarPost(int usuarioId, String conteudo) {
        Usuario usuario = null;

        // GARGALO 3:
        // Busca linear por usuário → O(N)
        for (Usuario u : usuarios) {
            if (u.getId() == usuarioId) {
                usuario = u;
                break;
            }
        }
        
        if (usuario != null) {
            Post novoPost = new Post(usuarioId, conteudo, System.currentTimeMillis());
            todosOsPosts.add(novoPost);
            usuario.incrementarContadorPosts();
        }
    }
    
    public PostsExtremos obterPrimeiroEUltimoPost(int usuarioId) {
        List<Post> postsDoUsuario = new ArrayList<>();
        
        // GARGALO 4:
        // Percorre TODOS os posts do sistema para filtrar apenas os de um usuário → O(N*M)
        for (Post post : todosOsPosts) {
            if (post.getUsuarioId() == usuarioId) {
                postsDoUsuario.add(post);
            }
        }
        
        // GARGALO 5 (CRÍTICO):
        // Bubble Sort → Complexidade O(K²)
        // Onde K = quantidade de posts do usuário
        // Ordenação totalmente desnecessária apenas para pegar primeiro e último
        for (int i = 0; i < postsDoUsuario.size() - 1; i++) {
            for (int j = 0; j < postsDoUsuario.size() - i - 1; j++) {
                if (postsDoUsuario.get(j).getTimestamp() > 
                    postsDoUsuario.get(j + 1).getTimestamp()) {

                    Post temp = postsDoUsuario.get(j);
                    postsDoUsuario.set(j, postsDoUsuario.get(j + 1));
                    postsDoUsuario.set(j + 1, temp);
                }
            }
        }
        
        if (postsDoUsuario.isEmpty()) {
            return null;
        }
        
        // Depois de toda essa operação pesada,
        // só precisamos do primeiro e último elemento
        Post primeiro = postsDoUsuario.get(0);
        Post ultimo = postsDoUsuario.get(postsDoUsuario.size() - 1);
        
        return new PostsExtremos(primeiro, ultimo);
    }
    
    public void adicionarUsuario(Usuario usuario) {
        boolean existe = false;

        // GARGALO 6:
        // Verificação de duplicidade com busca linear → O(N)
        for (Usuario u : usuarios) {
            if (u.getId() == usuario.getId()) {
                existe = true;
                break;
            }
        }
        
        if (!existe) {
            usuarios.add(usuario);
        }
    }
    
    public int contarPostsDoUsuario(int usuarioId) {
        int contador = 0;

        // GARGALO 7:
        // Percorre TODOS os posts do sistema apenas para contar
        // → O(N*M)
        for (Post post : todosOsPosts) {
            if (post.getUsuarioId() == usuarioId) {
                contador++;
            }
        }
        return contador;
    }
    
    public String gerarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DO BLOG ===\n\n");
        
        for (Usuario usuario : usuarios) {

            // PROBLEMA GRAVE:
            // Para CADA usuário:
            // - contarPostsDoUsuario → percorre todos os posts
            // - obterPrimeiroEUltimoPost → percorre todos os posts + ordena
            // Isso gera uma explosão de complexidade
            
            int totalPosts = contarPostsDoUsuario(usuario.getId());
            
            PostsExtremos extremos = obterPrimeiroEUltimoPost(usuario.getId());
            
            // Complexidade total aproximada:
            // O(N × (N*M + K²))
        }
        
        return relatorio.toString();
    }
}
