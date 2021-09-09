package com.adobe.aem.smarttag_converter.core.services.impl;
// CSV Dictionary Read Service 

import com.adobe.aem.smarttag_converter.core.services.CSVDictionaryReadService;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.IOUtils;

@Component(service = CSVDictionaryReadService.class, immediate = true)

public class CSVDictionaryReadServiceImpl implements CSVDictionaryReadService {

  private static final Logger log = LoggerFactory.getLogger(CSVDictionaryReadServiceImpl.class);

  @Reference
  private ResourceResolverFactory resolverFactory;


  @Override
  public String getDictionaryDataString(String dictPath) {
    ResourceResolver resolver = null;
    String contentStr="";
    try {
      resolver = resolverFactory.getAdministrativeResourceResolver(null);

      // modified for AEM CS
      // /content/dam/aem-smarttag-converter/sample-dictionary.csv/jcr:content/renditions/original
      String dictionaryFullPath = dictPath +"/jcr:content/renditions/original/jcr:content";
      Resource resDicData =  resolver.getResource( dictionaryFullPath );
      InputStream content = resDicData.adaptTo(InputStream.class);
      contentStr = IOUtils.toString(content,StandardCharsets.UTF_8);
      log.info("_______■■■■■■■■■■■■■■■■■■■■■■ InputStream contentStr:"+contentStr);
      // end modified for AEM CS

      content.close();
    } catch (Exception e) {
      log.info("getDictionaryDataString: Exception - {}",e);
    } finally {
      resolver.close();
    }
    return contentStr;
  }

}

