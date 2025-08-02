

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    private boolean cadastroSucesso = false;
    
    public void cadastrarProduto (ProdutosDTO produto){
    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        cadastroSucesso = false; // Reset
        
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            cadastroSucesso = true; // Sucesso
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            cadastroSucesso = false; // Erro
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
}
    
    public boolean isCadastroSucesso() {
        return cadastroSucesso;
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}

