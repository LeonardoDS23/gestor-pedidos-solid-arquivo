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
     * (db.properties)
     * e o registro do driver JDBC.
     */
    static {
        try (
                // Obtém o arquivo db.properties do diretório de recursos (src/main/resources)
                InputStream input = ConnectionFactory.class
                        .getClassLoader()
                        .getResourceAsStream("db.properties")) {
            if (input == null) {
                // Caso o arquivo não seja encontrado, mostra uma mensagem de erro
                System.err.println("Erro: Arquivo 'db.properties' não encontrado.");
                // Poderia ser lançada uma exceção RuntimeException aqui, se desejado
            }

            // Carrega as propriedades do arquivo (url, usuário, senha, driver)
            PROPERTIES.load(input);

            // Carrega dinamicamente o driver JDBC definido no arquivo de propriedades
            // Exemplo: com.mysql.cj.jdbc.Driver
            Class.forName(PROPERTIES.getProperty("db.driver"));

        } catch (IOException e) {
            // Captura erros ao ler o arquivo db.properties
            System.err.println("Erro ao carregar o arquivo de propriedades: " + e.getMessage());
            throw new RuntimeException("Falha ao configurar a conexão.", e);

        } catch (ClassNotFoundException e) {
            // Captura erro se o driver JDBC não for encontrado no classpath
            System.err.println("Erro: Driver JDBC não encontrado: " + e.getMessage());
            throw new RuntimeException("Driver MySQL não encontrado.", e);
        }
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

        // Cria e retorna a conexão usando o DriverManager
        return DriverManager.getConnection(url, user, password);
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
                conn.close(); // Libera os recursos da conexão
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
