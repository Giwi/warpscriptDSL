# WarpScript DSL

Light Groovy DSL for generating and executing [WarpScript](http://www.warp10.io/reference/) against an
[warp10](https://www.warp10.io/) instance.

Example : 

Create a token.resource file in folder src/main/groovy
```
READ_TOKEN
```

Then run a test script, a default test script can be found in src/main/groovy/token.resource

```groovy
def url = 'http://localhost:8080'
def readToken = 'ZZ63q..Y0f4IFjYaiF9S'

println WarpScript.instance.with(url, readToken).fetch('data.fuel', ['type': 'sp98'], -1).exec()
```

