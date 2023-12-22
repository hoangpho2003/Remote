package zSERVER;

import MODEL.ComputerTableModel;
import MODEL.PacketRemoteDesktop;
import MODEL.PacketTheoDoiClient;
import UTILS.DataUtils;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

/**
 *
 * @author Nhom 1142014_1142066
 */
public class FrmServerGUI extends javax.swing.JFrame implements Runnable {
    
    private final int mainThreadPortNumber = 999;
    private final int remoteDesktopThreadPortNumber = 998;
    private final int theoDoiClientThreadPortNumber = 997;
    private final int fileTransferThreadPortNumber = 996;
    
    Timer timerUpdateListSocket;
    private int timeUpdateTable = 2; // second
    public FrmServerGUI() {
        initComponents();
        setLocation(300, 100);
        tbComputerInfo.setModel(new ComputerTableModel(new ArrayList()));
        tbComputerInfo.getColumnModel().getColumn(0).setMaxWidth(100);
        setVisible(true);
        // Cáº­p nháº­t list socket sau má»—i timeUpdateTable giÃ¢y
        timerUpdateListSocket = new Timer();
        timerUpdateListSocket.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getTbModel().updateAllElement();
            }
        }, timeUpdateTable * 1000, timeUpdateTable * 1000);
        // server láº¯ng nghe remote desktop
        runThreadRemoteDesktop(); 
        // server láº¯ng nghe theo dÃµi client
        runThreadTheoDoiClient();
    }
    
    private ComputerTableModel getTbModel() {
        return (ComputerTableModel) tbComputerInfo.getModel();
    }


    @Override
    public void run() {
       // chat, gá»Ÿi thÃ´ng Ä‘iá»‡p, gá»Ÿi lá»‡nh shell
       try {
            final ServerSocket server = new ServerSocket(mainThreadPortNumber);
            // Phá»¥c vá»¥ nhiá»�u client
            while (true) {
                Socket socket;
                try {
                    // Náº¿u khÃ´ng dÃ¹ng thread
                    // chÆ°Æ¡ng trÃ¬nh sáº½ chá»� 1 client Ä‘áº§u tiÃªn á»Ÿ Ä‘Ã¢y
                    socket = server.accept();
                    getTbModel().addElement(socket);
                    System.out.println("Server: Alo "
                            + getTbModel().getSize());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }
     
   
    //<editor-fold defaultstate="collapsed" desc="Thread remote desktop">

    private void runThreadRemoteDesktop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket server = new ServerSocket(remoteDesktopThreadPortNumber);
                    // Phá»¥c vá»¥ nhiá»�u client
                    while (true) {
                        Socket socket;
                        try {
                            socket = server.accept();
                            new Thread(new FrmRemoteDesktop(socket)).start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Thread theo dÃµi client">
    private void runThreadTheoDoiClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ServerSocket server = new ServerSocket(theoDoiClientThreadPortNumber);
                    // Phá»¥c vá»¥ nhiá»�u client
                    while (true) {
                        Socket socket;
                        try {
                            socket = server.accept();
                            new Thread(new FrmTheoDoiClient(socket)).start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //</editor-fold>
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnChatClient = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButtonRemoteDesktop = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButtonTheoDoiClient = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbComputerInfo = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        btnChatClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/chat-bubbles-with-ellipsis.png"))); // NOI18N
        btnChatClient.setText("Chat Client");
        btnChatClient.setFocusable(false);
        btnChatClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChatClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChatClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatClientActionPerformed(evt);
            }
        });
        jToolBar1.add(btnChatClient);
        jToolBar1.add(jSeparator2);

        jButtonRemoteDesktop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/computer.png"))); // NOI18N
        jButtonRemoteDesktop.setText("Danh sách client");
        jButtonRemoteDesktop.setFocusable(false);
        jButtonRemoteDesktop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRemoteDesktop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRemoteDesktop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoteDesktopActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonRemoteDesktop);
        jToolBar1.add(jSeparator3);

        jButtonTheoDoiClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/monitor.png"))); // NOI18N
        jButtonTheoDoiClient.setText("Theo dõi client");
        jButtonTheoDoiClient.setFocusable(false);
        jButtonTheoDoiClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonTheoDoiClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonTheoDoiClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTheoDoiClientActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonTheoDoiClient);
        jToolBar1.add(jSeparator4);

        tbComputerInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên máy", "IP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbComputerInfo);
        if (tbComputerInfo.getColumnModel().getColumnCount() > 0) {
            tbComputerInfo.getColumnModel().getColumn(0).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbComputerInfo.getColumnModel().getColumn(1).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(1).setPreferredWidth(250);
            tbComputerInfo.getColumnModel().getColumn(2).setResizable(false);
            tbComputerInfo.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Thông tin máy tính trong mạng LAN", jPanel1);

        jMenu1.setText("Quay lại");

        jMenuItem1.setText("Thoát");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChatClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatClientActionPerformed
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ chat!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Má»Ÿ form chat vá»›i client Ä‘Ã£ chá»�n
        new Thread(new FrmChatVoiClient(mayClient)).start();
    }//GEN-LAST:event_btnChatClientActionPerformed
                   
    private void jButtonTheoDoiClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTheoDoiClientActionPerformed
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ Ä‘iá»�u khiá»ƒn!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Gá»Ÿi lá»‡nh yÃªu cáº§u client káº¿t ná»‘i Ä‘áº¿n socket server remote desktop
        PacketTheoDoiClient pk = new PacketTheoDoiClient();
        pk.setCmd(PacketTheoDoiClient.CMD_KHOIDONG);
        pk.setMessage(String.valueOf(theoDoiClientThreadPortNumber));
        DataUtils.goiDuLieu(mayClient, pk.toString());
    }

    private void jButtonRemoteDesktopActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbComputerInfo.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Báº¡n chÆ°a chá»�n mÃ¡y Ä‘á»ƒ Ä‘iá»�u khiá»ƒn!");
            return;
        }
        Socket mayClient = getTbModel().getItem(tbComputerInfo.getSelectedRow());
        // Gá»Ÿi lá»‡nh yÃªu cáº§u client káº¿t ná»‘i Ä‘áº¿n socket server remote desktop
        PacketRemoteDesktop pk = new PacketRemoteDesktop();
        pk.setCmd(PacketRemoteDesktop.CMD_KHOIDONG);
        pk.setMessage(String.valueOf(remoteDesktopThreadPortNumber));
        DataUtils.goiDuLieu(mayClient, pk.toString());
    }//GEN-LAST:event_jButtonRemoteDesktopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChatClient;
    private javax.swing.JButton jButtonRemoteDesktop;
    private javax.swing.JButton jButtonTheoDoiClient;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbComputerInfo;
    // End of variables declaration//GEN-END:variables
}
