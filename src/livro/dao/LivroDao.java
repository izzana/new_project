
package livro.dao;

import livro.model.Livros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author angelo
 */

public class LivroDao {
    public static List <Livros> listaLivros = new ArrayList<>();
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    
    
    private void conectar() throws ClassNotFoundException, SQLException{
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        this.conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/dbagenda");
    }
    
    
    private void criarStatement() throws SQLException{
        this.stmt = this.conn.createStatement();
    }
    
    private void criarPrepareStatement(String query) throws SQLException{
        this.pstmt = this.conn.prepareStatement(query);
    }
    
    private void desconectar() throws SQLException{
        if(this.pstmt != null)
            this.pstmt.close();
        if(this.stmt != null)
            this.stmt.close();
        if(this.conn != null)
            this.conn.close();
    }
    
    private Livros buscaLivro(int id){
        for(Livros livro: LivroDao.listaLivros){
            if(livro.getId()== id){
                return livro;
            }        
        }
    return null;
    }

    public void atualizarLivro(Livros livro) throws ClassNotFoundException, SQLException{
        this.conectar();
        String query = "UPDATE livros"
                + "set titulo=?, autor=?, ano_publicacao=?, editora=?, "
                + "impresso=?, localizacao=?"
                + "WHERE id = ?";
        this.criarPrepareStatement(query);
        this.pstmt.setString(1, livro.getTitulo());
        this.pstmt.setString(2, livro.getAutor());
        this.pstmt.setInt(3, livro.getAnoPublicacao());
        this.pstmt.setString(4, livro.getEditora());
        this.pstmt.setBoolean(5, livro.isImpresso());
        this.pstmt.setString(6, livro.getLocal());
        this.pstmt.setInt(7, livro.getId());
        this.pstmt.execute();
    }
    
    public void salvar(Livros livro) throws ClassNotFoundException, SQLException{
        this.conectar();
        String query = "INSERT INTO livros"
                + "(titulo, autor, ano_publicacao, editora, impresso, localizacao)"
                +"VALUES(?,?,?,?,?,?)";
        this.criarPrepareStatement(query);
        this.pstmt.setString(1, livro.getTitulo());
        this.pstmt.setString(2, livro.getAutor());
        this.pstmt.setInt(3, livro.getAnoPublicacao());
        this.pstmt.setString(4, livro.getEditora());
        this.pstmt.setBoolean(5, livro.isImpresso());
        this.pstmt.setString(6, livro.getLocal());
        this.pstmt.execute();
    }
}
