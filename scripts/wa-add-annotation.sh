#!/bin/bash
source scripts/wa-functions.sh

wa-add-annnotation '{
  "@context": "http://www.w3.org/ns/anno.jsonld",
  "type": "Annotation",
  "body": {
    "type": "TextualBody",
    "value": "I like this page!"
  },
  "target": "http://www.example.com/index.html"
}'

wa-add-annnotation '{
  "@context": "http://iiif.io/api/presentation/2/context.json",
  "@type": "oa:Annotation",
  "motivation": [
    "oa:tagging",
    "oa:commenting"
  ],
  "resource": [
    {
      "@type": "oa:Tag",
      "chars": "TestTag"
    },
    {
      "@type": "dctypes:Text",
      "format": "text/html",
      "chars": "<p>TestAnnotatie</p>"
    }
  ],
  "on": {
    "@type": "oa:SpecificResource",
    "full": "https://iiif.lib.harvard.edu/manifests/drs:5981093/canvas/canvas-5981120.json",
    "selector": {
      "@type": "oa:SvgSelector",
      "value": "<svg xmlns='http://www.w3.org/2000/svg'><path xmlns=\"http://www.w3.org/2000/svg\" d=\"M2544.65571,871.97372l539.74212,0l0,0l539.74212,0l0,331.89112l0,331.89112l-539.74212,0l-539.74212,0l0,-331.89112z\" data-paper-data=\"{&quot;defaultStrokeValue&quot;:1,&quot;editStrokeValue&quot;:5,&quot;currentStrokeValue&quot;:1,&quot;rotation&quot;:0,&quot;deleteIcon&quot;:null,&quot;rotationIcon&quot;:null,&quot;group&quot;:null,&quot;editable&quot;:true,&quot;annotation&quot;:null}\" id=\"rectangle_0403bd67-c6af-4b48-b5bf-f845fd5b5944\" fill-opacity=\"0\" fill=\"#00bfff\" fill-rule=\"nonzero\" stroke=\"#00bfff\" stroke-width=\"6.70487\" stroke-linecap=\"butt\" stroke-linejoin=\"miter\" stroke-miterlimit=\"10\" stroke-dasharray=\"\" stroke-dashoffset=\"0\" font-family=\"sans-serif\" font-weight=\"normal\" font-size=\"12\" text-anchor=\"start\" style=\"mix-blend-mode: normal\"/></svg>"
    },
    "within": {
      "@id": "https://iiif.lib.harvard.edu/manifests/drs:5981093",
      "@type": "sc:Manifest"
    }
  }
}'
