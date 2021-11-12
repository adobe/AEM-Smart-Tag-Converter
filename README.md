# AEM Smart Tag Converter

Sometime AEM user wants to convert smart tags into other tags to improve searchability.

Example:
- cuisine -> cooking
- sky -> 空

<BR/>

## Goals
Convert smart tags attached into assets into other tags.
The conversion rule is defined in CSV dictionary file in Assets.

This can be used for smart tag translation.

<BR/>

## Installation

    mvn clean install -PautoInstallSinglePackage

<BR/>

## Usage
Run "Smart Tag Converter Sample Workflow" for the assets which contains smart tags.
<BR/>

<img src="SmartTagConverterDemo800.gif" width="100%">

<BR/>
<BR/>

The sample image and dictionary under /content/dam/aem-smarttag-converter.

- Sample Image: sample-kyoto.JPG

- Sample dictionary: sample-dictionary.csv.



<BR/>

## Licensing
This project is licensed under the Apache V2 License. See [LICENSE](LICENSE)  for more information.
