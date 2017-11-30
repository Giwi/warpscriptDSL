import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.warp10.warpscriptDSL.WarpScript


def jsonSlurper = new JsonSlurper()
def url = 'http://localhost:8080'
def token = new File('token.resource').getText('UTF-8')

def response = WarpScript.instance
        .with(url: url, readToken: token)
        .fetch('data.fuel', ['type': 'sp98'], -1)
        .store('gts')
        .load('gts')
        .exec()

if (response.response != null) {
    jsonSlurper.parseText(
            response.response
    ).each {
        println JsonOutput.prettyPrint(JsonOutput.toJson(it))
    }
}
