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
