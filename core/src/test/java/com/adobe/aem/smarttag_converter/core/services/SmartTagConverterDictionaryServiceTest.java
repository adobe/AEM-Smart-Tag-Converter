package com.adobe.aem.smarttag_converter.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.adobe.aem.smarttag_converter.core.services.impl.SmartTagConverterDictionaryServiceImpl;

import java.util.HashMap;

@ExtendWith(AemContextExtension.class)
public class SmartTagConverterDictionaryServiceTest {

    @Mock
    private HashMap<String, String> dict;
        
    @InjectMocks
    private SmartTagConverterDictionaryServiceImpl dictServiceImpl;
 
    @BeforeEach
    private void initMocks(){
        MockitoAnnotations.initMocks(this);
        String tag = "japanese";
        String expected ="日本語";
        Mockito.when(dict.get(tag)).thenReturn(expected);
    }    
 
    @Test
    void testService() throws Exception {
        String convertedString = dictServiceImpl.getConvertedString("japanese");
        assertEquals("日本語",convertedString);
    }
}
