import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    
    private JButton invLocationButton;
    private JButton loginButton;
    private JButton downloadButton;
    private JButton exitButton;
    private JPasswordField passwordText;
    private JScrollPane jScrollPane1;
    private JTextArea statusText;
    private JTextField filepathText;
    private JTextField payrollText;
    private JTextField surnameText;

    public MainFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        
        payrollText = new JTextField();
        invLocationButton = new javax.swing.JButton();
        surnameText = new JTextField();
        passwordText = new JPasswordField();
        filepathText = new JTextField();
        loginButton = new JButton();
        downloadButton = new JButton();
        exitButton = new JButton();
        jScrollPane1 = new JScrollPane();
        statusText = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Reed Invoice Downloader");

        payrollText.setText("Payroll number");
        payrollText.setToolTipText("Reed Payroll Number");
        payrollText.setName("payrollText");
        payrollText.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                payrollText.setText("");
            }
            public void focusLost(FocusEvent e) {
                enableLogin();
            }
        });

        surnameText.setText("Surname");
        surnameText.setToolTipText("Surname");
        surnameText.setName("surnameText");
        surnameText.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                surnameText.setText("");
            }
            public void focusLost(FocusEvent e) {
                enableLogin();
            }
        });

        passwordText.setText("Password");
        passwordText.setToolTipText("Password");
        passwordText.setName("passwordText");
        passwordText.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e){
                passwordText.setText("");
            }
            public void focusLost(FocusEvent e) {
                enableLogin();
            }
        });
        
        invLocationButton.setText("Invoice Location");
        invLocationButton.setName("invLocationButton"); // NOI18N
        invLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invLocationButtonActionPerformed(evt);
            }
        });

        filepathText.setToolTipText("Output location");

        loginButton.setText("Login");
        loginButton.setName("loginButton"); // NOI18N
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginButton.setEnabled(false);

        downloadButton.setText("Download");
        downloadButton.setName("downloadButton"); // NOI18N
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });
        downloadButton.setEnabled(false);

        exitButton.setText("Exit");
        exitButton.setName("exitButton"); // NOI18N
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        statusText.setEditable(false);
        statusText.setColumns(20);
        statusText.setRows(5);
        statusText.setText("Reed Invoice Downloader\n");
        statusText.setToolTipText("Status");
        statusText.setWrapStyleWord(true);
        statusText.setName("statusText"); // NOI18N
        jScrollPane1.setViewportView(statusText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(payrollText)
                                                        .addComponent(invLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(filepathText)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(surnameText)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(payrollText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(surnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(invLocationButton)
                                        .addComponent(filepathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(downloadButton)
                                        .addComponent(exitButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    public void updateStatus(String update){
        statusText.setText(statusText.getText() + update + "\n");
    }

    public void enableLogin(){
        if (!payrollText.getText().equals("Payroll number") &&
                !surnameText.getText().equals("Surname") &&
                !new String(passwordText.getPassword()).equals("Password")){
            loginButton.setEnabled(true);
        }
    }

    public void enableDownload(){
        downloadButton.setEnabled(true);
    }

    private void invLocationButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        ReedInvoiceDownloaderExperimental.login(payrollText.getText(), surnameText.getText(), passwordText.getPassword());
    }

    private void downloadButtonActionPerformed(ActionEvent evt) {
        ReedInvoiceDownloaderExperimental.download();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        ReedInvoiceDownloaderExperimental.close();
    }

}
