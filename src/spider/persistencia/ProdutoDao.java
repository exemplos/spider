/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import spider.entidade.Produto;
import spider.entidade.SpiderException;

/**
 *
 * @author chico
 */
public class ProdutoDao {
    
    public void inserir(Produto produto) throws SpiderException{
        
        try (Connection con = Conexao.getConnection()){
            PreparedStatement pstmt = con.prepareStatement("insert into produto (codigo, descricao) values (?, ?)");
            pstmt.setInt(1, produto.getCodigo());
            pstmt.setString(2, produto.getDescricao());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new SpiderException("Erro ao incluir:\n" + ex.getMessage());
        }
    }

    public List<Produto> listar(){
        List<Produto> resultado = new ArrayList<>();
        try (Connection con = Conexao.getConnection();){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from produto");
            while(rs.next()){
                Produto p = new Produto();
                p.setCodigo(rs.getInt("codigo"));
                p.setDescricao(rs.getString("descricao"));
                resultado.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
}
