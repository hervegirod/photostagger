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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @since 0.1
 */
public class TaggerAction {
   private final boolean isIdenticalDirs;
   private final File inputDir;
   private final File outputDir;
   private final int firstIndex;
   private int index;
   private int count = 0;
   private final String pattern;

   public TaggerAction(File inputDir, File outputDir) {
      this.inputDir = inputDir;
      this.outputDir = outputDir;
      this.isIdenticalDirs = inputDir.equals(outputDir);

      Config config = Config.getInstance();
      firstIndex = config.getFirstIndex();
      index = firstIndex;
      pattern = config.getPattern();
   }

   private String getFileName(File file) {
      String name = file.getName();
      int theIndex = name.lastIndexOf('.');
      if (theIndex == -1 || theIndex == name.length() - 1) {
         return null;
      }
      String extension = name.substring(theIndex);
      name = pattern + "_" + index + extension;
      return name;
   }

   public void run() {
      ImageFilter filter = new ImageFilter();
      File[] files = inputDir.listFiles(filter);
      if (files != null && files.length != 0) {
         for (int i = 0; i < files.length; i++) {
            File inputFile = files[i];
            String fileName = getFileName(inputFile);
            if (fileName != null) {
               if (isIdenticalDirs) {
                  File outputFile = new File(inputDir, fileName);
                  inputFile.renameTo(outputFile);
                  count++;
               } else {
                  File outputFile = new File(outputDir, fileName);
                  Path inputPath = Paths.get(inputFile.toURI());
                  Path outputPath = Paths.get(outputFile.toURI());
                  try {
                     Files.copy(inputPath, outputPath);
                     count++;
                  } catch (IOException ex) {
                     System.err.println(ex.getMessage());
                  }
               }
               if (i < files.length - 1) {
                  index++;
               }
            }
         }
         System.out.println("Done rename " + count + " files, last file is " + index);
      }
   }

   private static class ImageFilter implements FilenameFilter {
      @Override
      public boolean accept(File dir, String name) {
         int index = name.lastIndexOf('.');
         if (index == -1 || index == name.length() - 1) {
            return false;
         }
         String extension = name.substring(index + 1).toLowerCase();
         switch (extension) {
            case "jpg":
            case "jpeg":
            case "gif":
            case "png":
            case "bmp":
               return true;
            default:
               return false;
         }
      }

   }
}
