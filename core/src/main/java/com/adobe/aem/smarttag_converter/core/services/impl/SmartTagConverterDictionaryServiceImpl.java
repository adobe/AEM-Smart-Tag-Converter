/*
Copyright 2021 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/

package com.adobe.aem.smarttag_converter.core.services.impl;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryConfig;
import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;
import com.adobe.aem.smarttag_converter.core.services.CSVDictionaryReadService;

import java.io.BufferedReader;
import java.util.HashMap;
import java.io.IOException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import java.io.StringReader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = SmartTagConverterDictionaryService.class, immediate = true)
@Designate(ocd = SmartTagConverterDictionaryConfig.class)

public class SmartTagConverterDictionaryServiceImpl implements SmartTagConverterDictionaryService {

  private static final Logger log = LoggerFactory.getLogger(SmartTagConverterDictionaryServiceImpl.class);
  private SmartTagConverterDictionaryConfig configuration;
  private HashMap<String, String> dictionary;

  @Reference
  private CSVDictionaryReadService csvDictionaryReadSerivce;

  @Activate
  protected void activate(SmartTagConverterDictionaryConfig configuration) {
    log.info(" __________ SmartTagConverterDictionaryConfigImpl - activate");
    this.configuration = configuration;

    boolean enable = configuration.enableConfig();
    String dictionaryName = configuration.getDictionaryName();
    log.info(" __________ dictionaryName:" + dictionaryName + " enabled:"+enable);

    if (enable) importDictionary();
  }

  @Override
  public String getConvertedString (String tag){
    String translated = this.dictionary.get(tag);
    return (translated!=null)?translated:tag;
  }

  @Override
  public void importDictionary() {
    String dictionaryPath = SmartTagConverterDictionaryService.DictionaryPath + getDictionaryName();
    String contentStr = csvDictionaryReadSerivce.getDictionaryDataString(dictionaryPath);
    BufferedReader br = new BufferedReader(new StringReader(contentStr));

    this.dictionary = new HashMap<String, String>();
    try {
      String line = br.readLine();		// skip first HEADER　line
      while ((line = br.readLine()) != null) {
        log.info("_____■■■■■■■■■■■ line _____ {} _____■■■■■■■■■■■",line);
        if ( !line.isEmpty() ){
          String[] col = line.split(",");
          this.dictionary.put(col[0], col[1]);
        }
      }  
    }catch(IOException e) {
      e.printStackTrace();
    }
  }


  public String getDictionaryName() {
    return this.configuration.getDictionaryName();
  }

}

