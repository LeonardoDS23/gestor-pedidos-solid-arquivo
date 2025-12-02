package com.tecdes.pedido.config;

// Importações necessárias para manipulação de arquivos e conexões JDBC
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe responsável por criar e gerenciar conexões com o banco de dados.
 * 
 * Essa classe segue o padrão "Factory", fornecendo um ponto centralizado
 * para obter conexões de forma consistente e reutilizável.
 */
public class ConnectionFactory {

    // Objeto Properties usado para armazenar as configurações do arquivo
    // db.properties
    private static final Properties PROPERTIES = new Properties();

    /**
     * Bloco estático executado apenas uma vez quando a classe é carregada.
     * Aqui ocorre o carregamento do arquivo de configuração do banco
     * (db.properties) e o registro do driver JDBC.
     */
    static {
        try {
            // Tenta carregar o arquivo de configuração
            loadConfiguration();
            
            // Carrega o driver JDBC
            loadDriver();
            
            System.out.println("✅ ConnectionFactory configurada com sucesso");
            
        } catch (Exception e) {
            System.err.println("❌ Erro crítico ao configurar ConnectionFactory");
            throw new RuntimeException("Falha na configuração do banco de dados", e);
        }
    }

    /**
     * Carrega as configurações do banco de dados
     */
    private static void loadConfiguration() {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            
            if (input != null) {
                PROPERTIES.load(input);
                System.out.println("📁 Configurações carregadas de db.properties");
            } else {
                // Arquivo não encontrado, usa configurações padrão
                System.out.println("⚠️  Arquivo db.properties não encontrado. Usando configurações padrão.");
                setDefaultProperties();
            }
            
        } catch (IOException e) {
            System.err.println("⚠️  Erro ao ler db.properties: " + e.getMessage());
            System.out.println("🔄 Usando configurações padrão...");
            setDefaultProperties();
        }
    }

    /**
     * Define as configurações padrão para desenvolvimento
     */
    private static void setDefaultProperties() {
        // Configurações padrão para MySQL
        PROPERTIES.setProperty("db.url", "jdbc:mysql://localhost:3306/gestor_pedidos");
        PROPERTIES.setProperty("db.user", "root");
        PROPERTIES.setProperty("db.password", "");
        PROPERTIES.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        
        System.out.println("🔧 Configurações padrão definidas para MySQL");
    }

    /**
     * Carrega o driver JDBC
     */
    private static void loadDriver() throws ClassNotFoundException {
        String driver = PROPERTIES.getProperty("db.driver");
        
        if (driver == null || driver.trim().isEmpty()) {
            throw new ClassNotFoundException("Driver não especificado nas configurações");
        }
        
        Class.forName(driver);
        System.out.println("🚀 Driver carregado: " + driver);
    }

    /**
     * Cria e retorna uma nova conexão com o banco de dados.
     * 
     * Os parâmetros (URL, usuário e senha) são obtidos do arquivo db.properties.
     * 
     * @return Um objeto Connection válido e aberto.
     * @throws SQLException Caso ocorra um erro ao tentar conectar.
     */
    public static Connection getConnection() throws SQLException {
        // Recupera os valores de configuração do arquivo de propriedades
        String url = PROPERTIES.getProperty("db.url");
        String user = PROPERTIES.getProperty("db.user");
        String password = PROPERTIES.getProperty("db.password");

        // Valida as configurações
        if (url == null || user == null || password == null) {
            throw new SQLException("Configurações de conexão incompletas");
        }

        System.out.println("🔗 Conectando ao banco: " + url);
        
        // Cria e retorna a conexão usando o DriverManager
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Método sobrecarregado para obter conexão com timeout personalizado
     */
    public static Connection getConnection(int timeoutSeconds) throws SQLException {
        Connection conn = getConnection();
        conn.setNetworkTimeout(
            java.util.concurrent.Executors.newFixedThreadPool(1),
            timeoutSeconds * 1000
        );
        return conn;
    }

    /**
     * Testa a conexão com o banco de dados
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2); // Testa com timeout de 2 segundos
        } catch (SQLException e) {
            System.err.println("❌ Falha no teste de conexão: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fecha a conexão de forma segura.
     * 
     * Verifica se a conexão não é nula antes de tentar fechá-la,
     * evitando NullPointerException.
     * 
     * @param conn Objeto Connection a ser fechado.
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("🔌 Conexão fechada com sucesso");
                }
            } catch (SQLException e) {
                System.err.println("⚠️  Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    /**
     * Fecha outros recursos do banco de dados
     */
    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("⚠️  Erro ao fechar recurso: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Método main para testes rápidos
     */
    public static void main(String[] args) {
        System.out.println("\n=== TESTE DE CONEXÃO ===");
        
        if (testConnection()) {
            System.out.println("✅ Conexão com banco de dados estabelecida com sucesso!");
        } else {
            System.err.println("❌ Falha ao conectar ao banco de dados");
            System.err.println("Verifique:");
            System.err.println("1. Se o banco de dados está rodando");
            System.err.println("2. As configurações em db.properties");
            System.err.println("3. As dependências no pom.xml");
        }
    }
}