

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
        cadastroSucesso = false; 
        
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            cadastroSucesso = true; 
            
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            cadastroSucesso = false; 
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
     String sql = "SELECT * FROM produtos";
        
        
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while(resultset.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        
        return listagem;
    }
    
    public boolean venderProduto(int id){
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    
    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        prep.setString(1, "Vendido");
        prep.setInt(2, id);
        
        int linhasAfetadas = prep.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("Produto vendido com sucesso!");
            return true;
        } else {
            System.out.println("Produto com ID " + id + " não encontrado!");
            return false;
        }
        
    } catch (Exception e) {
        System.out.println("Erro ao vender produto: " + e.getMessage());
        return false;
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
    }
    
    
   

