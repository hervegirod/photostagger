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
import java.io.FileReader;
import java.util.PropertyResourceBundle;

/**
 *
 * @since 0.1
 */
public class Config {
   private static Config config = null;
   private int firstIndex = 1;
   private String pattern = "file";
   
   private Config() {
      File dir = new File(System.getProperty("user.dir"));
      File propertiesFile = new File(dir, "properties.properties");
      try {
         PropertyResourceBundle prb = new PropertyResourceBundle(new FileReader(propertiesFile));
         firstIndex = Integer.parseInt(prb.getString("start"));
         pattern = prb.getString("pattern");
      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }
   
   public int getFirstIndex()  {
      return firstIndex;
   }   
   
   public String getPattern()  {
      return pattern;
   }
   
   public static Config getInstance() {
      if (config == null) {
         config = new Config();
      }
      return config;
   }
}
