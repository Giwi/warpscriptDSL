# WarpScript DSL

Light Groovy DSL for generating and executing [WarpScript](http://www.warp10.io/reference/) against an
[warp10](https://www.warp10.io/) instance.

Example : 

```groovy
def url = 'http://localhost:8080'
def readToken = 'ZZ63q..Y0f4IFjYaiF9S'

println WarpScript.instance.with(url, readToken).fetch('data.fuel', ['type': 'sp98'], -1).exec()
```

