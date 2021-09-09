package com.adobe.aem.smarttag_converter.core.services;

public interface SmartTagConverterDictionaryService {
    public String getConvertedString(String tag);
    public void importDictionary();
    public String getDictionaryName();
    public static String DictionaryPath = "/content/dam/aem-smarttag-converter/";
}
