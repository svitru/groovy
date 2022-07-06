import groovy.util.XmlSlurper

File file = new File("file.html")
String fileContent = file.text

String html = fileContent.replaceAll(/(?i)<link[^>]*>/, '')
       html = html.replaceAll('&nbsp;', '')

def htmlParser = new XmlSlurper().parseText(html)
res = htmlParser.body.table.tr.findAll { it.td[0].text().contains("projects-config") }.list()
      .collectEntries {
                       [(it.td[0].text()[0..-2]):
                       Date.parse("E MMM dd H:m:s z yyyy", it.td[1].text())]
                      }
      .max { it.value }

println("${res.getKey()} --- ${res.getValue()}")
