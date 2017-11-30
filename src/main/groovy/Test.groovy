import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.warp10.warpscriptDSL.WarpScript


def jsonSlurper = new JsonSlurper()
def url = 'http://localhost:8080'
def token = new File('token.resource').getText('UTF-8')

def res = jsonSlurper.parseText(
        WarpScript.instance
                .with(url, token)
                .fetch('data.fuel', ['type': 'sp98'], -1).exec()
)

res.each {
    println JsonOutput.prettyPrint(JsonOutput.toJson(it))
}
