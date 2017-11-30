import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.warp10.warpscriptDSL.WarpScript


def jsonSlurper = new JsonSlurper()
def url = 'http://localhost:8080'
def token = 'ZZ63qGJSJ5gWc3QUIKbYXng_.mDHJjEJH.8XSygfHM7Bzle2UG9ljmzQpURlrcCuvlvAy1HUyLO3CieY6Aa_BwsNzFLZ5MYAniL6EQD.QUgpIycT7y9pz8rSjsV1GoTRZAX7XS1GNuOEINj6lpWY0f4IFjYaiF9S'

def res = jsonSlurper.parseText(
        WarpScript.instance
                .with(url, token)
                .fetch('data.fuel', ['type': 'sp98'], -1).exec()
)

res.each {
    println JsonOutput.prettyPrint(JsonOutput.toJson(it))
}
