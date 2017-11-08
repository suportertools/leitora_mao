/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitora_mao;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Claudemir Rtools
 */
public class GravaConfiguracao implements KeyListener {
    
    private JTextField field_leitora = new JTextField();
    private String leitora = "";
    private String caminho = "";
    private String catraca = "";
    private String cliente = "";
    private JLabel label_cartao = new JLabel("Número do Cartão");
    private JLabel label_ativa = new JLabel("");
    
    public GravaConfiguracao() {
        
    }
    
    public ActionListener Action_Tray() {
        ActionListener action_tray = (ActionEvent e) -> {
            JFrame frame = new JFrame();
            frame.setTitle("Leitora de Mão");
            frame.setAutoRequestFocus(true);
            frame.setLayout(null);
            frame.setBounds(0, 0, 450, 180);
            frame.setResizable(false);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // centralizar frame
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
            
            JButton button_esconder = new JButton("Esconder");
            button_esconder.addActionListener(Action_Esconder(frame));
            button_esconder.setBounds(10, 100, 200, 30);
            
            JButton button_sair = new JButton("Sair do Sistema");
            button_sair.addActionListener(Action_Sair());
            button_sair.setBounds(220, 100, 200, 30);
            
            field_leitora.setBounds(10, 35, 300, 30);
            field_leitora.requestFocus();
            field_leitora.addKeyListener(this);
            
            label_ativa.setBounds(10, 50, 500, 50);
            label_ativa.setFont(new Font(null, Font.PLAIN, 20));
            
            label_cartao.setBounds(10, 0, 500, 50);
            
            frame.add(button_esconder);
            frame.add(button_sair);
            frame.add(field_leitora);
            frame.add(label_ativa);
            frame.add(label_cartao);
            
            frame.setVisible(true);
        };
        return action_tray;
    }
    
    public ActionListener Action_Sair() {
        ActionListener action_sair = (ActionEvent e) -> {
            System.exit(0);
        };
        return action_sair;
    }
    
    public ActionListener Action_Esconder(JFrame frame) {
        ActionListener action_esconder = (ActionEvent e) -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        };
        return action_esconder;
    }
    
    public void criar_SystemTray() {
        try {
            //PopupMenu popupMenu = new PopupMenu();

            SystemTray tray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(new ImageIcon("C:/rtools/leitora_mao/leitora_mao-icon.png").getImage(), "Leitora de Mão");
            
            trayIcon.addActionListener(Action_Tray());
            //trayIcon.setPopupMenu(popupMenu);

            tray.add(trayIcon);
        } catch (HeadlessException | AWTException e) {
            e.getMessage();
        }
    }
    
    public Boolean conf() {
        File file = new File("C:/rtools/leitora_mao");
        file.mkdirs();
        
        File file_exists = new File("C:/rtools/leitora_mao/conf.json");
        if (file_exists.exists()) {
            String str_json;
            try {
                str_json = new String(Files.readAllBytes(Paths.get(file_exists.getPath())));
                JSONObject json = new JSONObject(str_json);
                
                caminho = json.getString("caminho");
                catraca = json.getString("catraca");
                cliente = json.getString("cliente");
                label_ativa.setText("Leitora da Catraca n° " + catraca);
            } catch (IOException | JSONException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return false;
            }
            return true;
        }
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Leitora"));
        panel.add(field_leitora);
        return true;
    }
    
    public boolean loadJson() {
        return true;
    }
    
    public JTextField getField_leitora() {
        return field_leitora;
    }
    
    public void setField_leitora(JTextField field_leitora) {
        this.field_leitora = field_leitora;
    }
    
    public JLabel getLabel_ativa() {
        return label_ativa;
    }
    
    public void setLabel_ativa(JLabel label_ativa) {
        this.label_ativa = label_ativa;
    }
    
    public String getCaminho() {
        return caminho;
    }
    
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    
    public String getCatraca() {
        return catraca;
    }
    
    public void setCatraca(String catraca) {
        this.catraca = catraca;
    }
    
    public String getLeitora() {
        return leitora;
    }
    
    public void setLeitora(String leitora) {
        this.leitora = leitora;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        JComponent c = (JComponent) e.getSource();
        if (c == field_leitora) {
            if (e.getKeyCode() == 10) {
                if (!field_leitora.getText().isEmpty()) {
                    setLeitora(field_leitora.getText());
                }
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
