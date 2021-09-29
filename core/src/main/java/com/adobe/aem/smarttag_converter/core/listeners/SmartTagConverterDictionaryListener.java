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

/*
 * Reload a converter dictionary csv file when it is modified.
 */
package com.adobe.aem.smarttag_converter.core.listeners;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;

import java.util.List;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
  service = ResourceChangeListener.class,
  property = {
    ResourceChangeListener.PATHS + "=" + SmartTagConverterDictionaryService.DictionaryPath,
    ResourceChangeListener.CHANGES + "=CHANGED"
  }
)

public class SmartTagConverterDictionaryListener implements ResourceChangeListener{
  public static final Logger log = LoggerFactory.getLogger(SmartTagConverterDictionaryListener.class);

  @Reference
  private SmartTagConverterDictionaryService dictionaryService;

  @Override
  public void onChange(List<ResourceChange> list) {
    String dictionaryName = dictionaryService.getDictionaryName();

    list.forEach((change) -> {
      String path = change.getPath();
      log.info("■■■■■■■■■■ Smart Tag Converter ■■■■ Dictionary Name: {} , Path: {}", dictionaryName, path );
      if ( path.contains(dictionaryName) ){
        dictionaryService.importDictionary();
        log.info("■■■■■■■■■■ Smart Tag Converter ■■■■ Dictionary Imported.. {}", "OK");
      }
    });
  }
}
