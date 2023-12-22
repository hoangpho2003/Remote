/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zCLIENT;

import MODEL.PacketTin;
import MODEL.PacketRemoteDesktop;
import MODEL.PacketChat;
import MODEL.PacketTheoDoiClient;
import UTILS.DataUtils;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import zCLIENT.REMOTE.GuiManHinh;
import zCLIENT.REMOTE.ThaoTacManHinh;

/**
 *
 * @author Nhom 1142014_1142066
 */
public class ClientGUI extends javax.swing.JFrame implements Runnable {

    Socket socketFromServer;
    ChatVoiServer dialogChatServer;
    boolean continueThread = true;
    String ipServer;
    final int mainPortServer = 999;
    Socket socketNhanFile;
    ScreenCapture screenCapture;
    
    public ClientGUI() {
        try {
            initComponents();
            this.setLocationRelativeTo(null);
            setVisible(true);
            ipServer = txtIP.getText();
            lblClientName.setText(InetAddress.getLocalHost().getHostName()
                    + " (" + InetAddress.getLocalHost().getHostAddress() + ")");
            lblIPAddress.setText(ipServer);
            lblStatus.setText("Đang chờ kết nối tới server...");
        } catch (Exception ex) {
        }
    }

    @Override
    public void run() {
        while (continueThread) {
            try {
                String msg = DataUtils.nhanDuLieu(socketFromServer);
                if (msg != null && !msg.isEmpty()) {
                    xuLyDuLieuTrungTam(msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }
    //<editor-fold defaultstate="collapsed" desc="Xá»­ lÃ½ dá»¯ liá»‡u trung tÃ¢m">

    private void xuLyDuLieuTrungTam(String msg) throws UnknownHostException, IOException {
        PacketTin pkTin = new PacketTin();
        pkTin.phanTichMessage(msg);
        // Thá»±c hiá»‡n cÃ¡c cÃ¢u lá»‡nh tá»« Server
        if (pkTin.isId(PacketChat.ID)) {
            if (dialogChatServer == null) {
                // Khá»Ÿi táº¡o
                dialogChatServer = new ChatVoiServer(socketFromServer);
            }
            // Gá»Ÿi dá»¯ liá»‡u Ä‘Ã£ phÃ¢n tÃ­ch
            dialogChatServer.nhanDuLieu(pkTin.getCmd(), pkTin.getMessage().toString());
            if (!dialogChatServer.isVisible()) {
                dialogChatServer.setVisible(true);
            }
        } else if (pkTin.isId(PacketRemoteDesktop.ID)) {
            xuLyRemoteDesktop(pkTin);
        } else if (pkTin.isId(PacketTheoDoiClient.ID)) {
            xuLyTheoDoiClient(pkTin);
        }
        System.err.println(pkTin.toString());
    }
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Remote desktop">
    private void xuLyRemoteDesktop(PacketTin pkTin) {
        int port = Integer.parseInt(pkTin.getMessage().toString());
        // DÃ¹ng Ä‘á»ƒ xá»­ lÃ½ mÃ n hÃ¬nh
        Robot robot;
        // DÃ¹ng dá»ƒ tÃ­nh Ä‘á»™ phÃ¢n giáº£i vÃ  kÃ­ch thÆ°á»›c mÃ n hÃ¬nh cho client
        Rectangle rectangle;
        try {
            // Táº¡o socket nháº­n remote vá»›i port Ä‘Ã£ gá»Ÿi qua
            final Socket socketRemote =
                    new Socket(ipServer, port);
            try {
                // Láº¥y mÃ n hÃ¬nh máº·c Ä‘á»‹nh cá»§a há»‡ thá»‘ng
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

                // Láº¥y dimension mÃ n hÃ¬nh
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                rectangle = new Rectangle(dim);

                // Chuáº©n bá»‹ robot thao tÃ¡c mÃ n hÃ¬nh
                robot = new Robot(gDev);

                // Gá»Ÿi mÃ n hÃ¬nh
                new GuiManHinh(socketRemote, robot, rectangle);
                // Gá»Ÿi event chuá»™t/phÃ­m thao tÃ¡c mÃ n hÃ¬nh
                new ThaoTacManHinh(socketRemote, robot);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Theo dÃµi client">
    private void xuLyTheoDoiClient(PacketTin pkTin) {
        int port = Integer.parseInt(pkTin.getMessage().toString());
        // DÃ¹ng Ä‘á»ƒ xá»­ lÃ½ mÃ n hÃ¬nh
        Robot robot;

        try {
            // Táº¡o socket nháº­n remote vá»›i port Ä‘Ã£ gá»Ÿi qua
            final Socket socketRemote =
                    new Socket(ipServer, port);
            try {
                // Láº¥y mÃ n hÃ¬nh máº·c Ä‘á»‹nh cá»§a há»‡ thá»‘ng
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

                // Chuáº©n bá»‹ robot thao tÃ¡c mÃ n hÃ¬nh
                robot = new Robot(gDev);
                // Gá»Ÿi mÃ n hÃ¬nh 
                new GuiManHinh(socketRemote, robot);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
        }
    }
    //</editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStatus4 = new javax.swing.JLabel();
        lblClientName = new javax.swing.JLabel();
        lblStatus1 = new javax.swing.JLabel();
        lblIPAddress = new javax.swing.JLabel();
        lblStatus2 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        btnThoat = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        jButtonConnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lblStatus4.setText("Client:");

        lblClientName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblClientName.setForeground(new java.awt.Color(0, 0, 255));
        lblClientName.setText("MyComputer");

        lblStatus1.setText("IP Address:");

        lblIPAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIPAddress.setForeground(new java.awt.Color(0, 0, 255));
        lblIPAddress.setText("127.0.0.1");

        lblStatus2.setText("Trạng thái:");

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 0, 255));
        lblStatus.setText("Status");

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        jLabel1.setText("IP Server:");

        txtIP.setText("127.0.0.1");
        txtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIPActionPerformed(evt);
            }
        });

        jButtonConnect.setText("Kết nối");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblStatus1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(lblStatus2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblStatus4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(txtIP)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jButtonConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThoat)))
                .addGap(0, 83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus4)
                    .addComponent(lblClientName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus1)
                    .addComponent(lblIPAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus2)
                    .addComponent(lblStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat)
                    .addComponent(jButtonConnect))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        ipServer = txtIP.getText();
        try {
            // Khá»Ÿi táº¡o káº¿t ná»‘i tá»« Client Ä‘áº¿n Server
            lblStatus.setText("Đang chờ kết nối tới server...");
            socketFromServer = new Socket(ipServer, mainPortServer);
            lblStatus.setText("Kết nối Server thành công.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "KhÃ´ng thá»ƒ káº¿t ná»‘i vá»›i server!");
        }
    }//GEN-LAST:event_jButtonConnectActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        continueThread = false;
    }

 
    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void txtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtIPActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnThoat;
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblIPAddress;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatus1;
    private javax.swing.JLabel lblStatus2;
    private javax.swing.JLabel lblStatus4;
    private javax.swing.JTextField txtIP;
    // End of variables declaration//GEN-END:variables
}
