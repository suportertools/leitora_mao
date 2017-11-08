/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitora_mao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author Claudemir Rtools
 */
public class Leitora_mao {

    private static final GravaConfiguracao GC = new GravaConfiguracao();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!GC.conf()) {
            return;
        }
        try {
            GC.criar_SystemTray();

            Boolean parar = false;

            while (!parar) {
                if (GC.getLeitora().isEmpty()) {
                    continue;
                }

                StringBuilder response = new StringBuilder();
                //URL url = new URL("http://localhost:8084/monitorCatraca");
                URL url = new URL(GC.getCaminho() + "/ws/liberar_cartao.xhtml?cliente=" + GC.getCliente() + "&cartao=" + GC.getLeitora() + "&" + "catraca=" + GC.getCatraca() + "&" + "servidor=" + GC.getServidor());
                Charset charset = Charset.forName("UTF8");

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
                con.setRequestMethod("GET");
                con.connect();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
                    response.append(in.readLine());
                    in.close();
                }
                con.disconnect();
                Thread.sleep(1000);

                GC.getField_leitora().setText("");
                GC.setLeitora("");
            }

        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }

    }

}
