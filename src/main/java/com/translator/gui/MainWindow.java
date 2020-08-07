/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.translator.gui;

import com.translator.TranslationUnit;
import com.translator.output.ContextHolder;
import com.translator.parser.JavaLexer;
import com.translator.parser.JavaListener;
import com.translator.parser.JavaParser;
import com.translator.parser.JavaParser.CompilationUnitContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

/**
 *
 * @author Carlos
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        btnTranslate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSource = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Translator Java -> C++");

        txtOutput.setColumns(20);
        txtOutput.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtOutput.setRows(5);
        txtOutput.setName(""); // NOI18N
        jScrollPane1.setViewportView(txtOutput);

        btnTranslate.setText("Traducir");
        btnTranslate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTranslateActionPerformed(evt);
            }
        });

        txtSource.setColumns(20);
        txtSource.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtSource.setRows(5);
        txtSource.setName(""); // NOI18N
        jScrollPane2.setViewportView(txtSource);

        btnSave.setText("Guardar archivo");
        btnSave.setToolTipText("");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnOpen.setText("Abrir archivo");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTranslate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnTranslate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTranslateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTranslateActionPerformed
        // TODO add your handling code here:
        String sourceCode = txtSource.getText();
        String interfaceReplaced = sourceCode.replaceAll("interface ", "abstract class ");
        String output = execute(interfaceReplaced);
        txtOutput.setText(output);

    }//GEN-LAST:event_btnTranslateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        JFileChooser saveFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt", "cpp");
        saveFile.setFileFilter(filter);
        int returnVal = saveFile.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = saveFile.getSelectedFile();
                if (!(file.getName().endsWith(".txt") || file.getName().endsWith(".cpp"))) {
                    file = new File(file.getAbsolutePath() + ".cpp");
                }

                BufferedWriter outFile = null;
                try {
                    outFile = new BufferedWriter(new FileWriter(file));
                    txtOutput.write(outFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (outFile != null) {
                        try {
                            outFile.close();
                        } catch (IOException e) {
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Escritura exitosa en archivo",
                        "Registro", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo escribir en el archivo",
                        "Registro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        JFileChooser openFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt", "java");
        openFile.setFileFilter(filter);
        int returnVal = openFile.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = openFile.getSelectedFile();

                BufferedReader in = new BufferedReader(new FileReader(selectedFile));
                StringBuilder sb = new StringBuilder();
                String line = in.readLine();
                while (line != null) {
                    sb.append(line + "\n");
                    line = in.readLine();
                }
                txtSource.setText(sb.toString());
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_btnOpenActionPerformed
    public static String execute(String sourceCode) {
        ContextHolder.methodDeclaration = null;
        ContextHolder.classDeclarations.clear();
        ANTLRInputStream stream = new ANTLRInputStream(sourceCode);
        JavaLexer lexer = new JavaLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);

        JavaParser.CompilationUnitContext tree = parser.compilationUnit();
        try {
            TranslationUnit translationUnit = new TranslationUnit(tree);
            return translationUnit.toString();
        } catch (RecognitionException ex) {
            JOptionPane.showMessageDialog(null, "Syntax error: " + ex,
                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        //return translationUnit.toString();
        return null;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTranslate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtOutput;
    private javax.swing.JTextArea txtSource;
    // End of variables declaration//GEN-END:variables
}
