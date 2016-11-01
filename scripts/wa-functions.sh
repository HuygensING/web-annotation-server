function wa-about {
  curl  http://localhost:8080/about | jq "."
}

function wa-add-annnotation {
  curl \
    -H 'Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"' \
    -H 'Content-type: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"' \
     --data-binary "$1" \
    http://localhost:8080/annotations | jq "."
  echo
}

function wa-get-annnotations {
  curl -i \
    -H 'Accept: application/ld+json; profile="http://www.w3.org/ns/anno.jsonld"' \
    http://localhost:8080/annotations
}
