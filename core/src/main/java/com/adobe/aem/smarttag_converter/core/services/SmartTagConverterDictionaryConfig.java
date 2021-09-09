package com.adobe.aem.smarttag_converter.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
// import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(
  name = "Smart Tag Converter Configuration",
  description = "This configuration reads the values to make an smart tag converter dictionary")

public @interface SmartTagConverterDictionaryConfig {

  @AttributeDefinition(
    name = "Enable config",
    description = "This property indicates whether the configuration values will taken account or not",
    type = AttributeType.BOOLEAN
  )
  public boolean enableConfig();

  @AttributeDefinition(
    name = "DictionaryName",
    description = "Enter the dictionary file name (it must be save under the directory of /content/dam/dictionary/"
  )
  public String getDictionaryName();

}


