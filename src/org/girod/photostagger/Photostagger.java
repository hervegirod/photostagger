/*
Copyright (c) 2021, 2022 Hervé Girod
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/photostagger
 */
package org.girod.photostagger;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @since 0.1
 */
public class Photostagger extends JFrame {
   private File inputDir = null;
   private File outputDir = null;
   private boolean isValid = false;
   private AbstractAction runAction = null;

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Photostagger tagger = new Photostagger();
      tagger.setVisible(true);
   }

   public Photostagger() {
      super("Photos tagger");
      setup();
      this.setSize(300, 300);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void setup() {
      Config.getInstance();
      
      JMenuBar mbar = new JMenuBar();
      this.setJMenuBar(mbar);
      JMenu fileMenu = new JMenu("File");
      mbar.add(fileMenu);

      JPanel cont = new JPanel();
      this.setContentPane(cont);
      cont.setLayout(new GridBagLayout());
      runAction = new AbstractAction("Run") {
         @Override
         public void actionPerformed(ActionEvent e) {
            run();
         }
      };
      runAction.setEnabled(false);
      JButton runButton = new JButton(runAction);
      cont.add(runButton, new GridBagConstraints());

      AbstractAction setInputAction = new AbstractAction("Set Input directory") {
         @Override
         public void actionPerformed(ActionEvent e) {
            setInputDir();
         }
      };
      fileMenu.add(setInputAction);

      AbstractAction setOutputAction = new AbstractAction("Set Output directory") {
         @Override
         public void actionPerformed(ActionEvent e) {
            setOutputDir();
         }
      };
      fileMenu.add(setOutputAction);

      AbstractAction exitAction = new AbstractAction("Exit") {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      };
      fileMenu.add(exitAction);
   }

   private void run() {
      TaggerAction action = new TaggerAction(inputDir, outputDir);
      action.run();
   }

   private void setInputDir() {
      JFileChooser chooser = new JFileChooser("Set Photos Input directory");
      chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int ret = chooser.showOpenDialog(this);
      if (ret == JFileChooser.APPROVE_OPTION) {
         inputDir = chooser.getSelectedFile();
         if (inputDir != null && inputDir.exists()) {
            if (outputDir != null) {
               isValid = true;
               runAction.setEnabled(true);
            }
         }
      }
   }

   private void setOutputDir() {
      JFileChooser chooser = new JFileChooser("Set Photos Output directory");
      chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int ret = chooser.showOpenDialog(this);
      if (ret == JFileChooser.APPROVE_OPTION) {
         outputDir = chooser.getSelectedFile();
         if (outputDir != null && outputDir.exists()) {
            if (inputDir != null) {
               isValid = true;
               runAction.setEnabled(true);
            }
         }
      }
   }
}
