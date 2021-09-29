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


