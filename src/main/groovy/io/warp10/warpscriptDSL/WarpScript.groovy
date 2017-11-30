package io.warp10.warpscriptDSL

@Singleton
class WarpScript {
    def ws = new StringBuilder()
    def url
    def readToken
    def writeToken

    def with(Map attrs) {
        this.url = attrs.url
        this.readToken = attrs.readToken
        this.writeToken = attrs.writeToken
        return this
    }

    def addDefaultReadToken(String readToken) {
        this.readToken = readToken
    }

    def fetch(String className, Map<String, String> labels, Date start, Date end) {
        return fetch(className, labels, start.toString(), end.toString())
    }

    def fetch(String className, Map<String, String> labels, String start, String end) {
        ws.append("[ '${this.readToken}' '${className}' ${extractLabels(labels)} '${start}' '${end}' ] FETCH").append('\n')
        return this
    }

    def fetch(String className, Map<String, String> labels, int end) {
        ws.append("[ '${this.readToken}' '${className}' ${extractLabels(labels)} NOW ${end} ] FETCH").append('\n')
        return this
    }

    def store(String name) {
        ws.append("'${name}' STORE").append('\n')
        return this
    }

    def load(String name) {
        ws.append("'${name}' LOAD").append('\n')
        return this
    }

    def exec() {
        return sendPostRequest("${this.url}/api/v0/exec", toString())
    }

    String toString() {
        return this.ws.toString()
    }


    private static extractLabels(Map<String, String> labels) {
        def l = '{'
        labels.keySet().each({
            l += " '${it}' '${labels[it]}' "
        })
        l += '}'
        return l
    }

    static def sendPostRequest(String urlString, String paramString) {
        def url = new URL(urlString)
        def conn = url.openConnection()
        conn.setDoOutput(true)
        def writer = new OutputStreamWriter(conn.getOutputStream())

        writer.write(paramString)
        writer.flush()
        def response = [
                meta    : [
                        status: conn.responseCode,
                        'X-Warp10-Error-Line'   : conn.headerFields['X-Warp10-Error-Line'],
                        'X-Warp10-Fetched'      : conn.headerFields['X-Warp10-Fetched'],
                        'X-Warp10-Ops'          : conn.headerFields['X-Warp10-Ops'],
                        'X-Warp10-Elapsed'      : conn.headerFields['X-Warp10-Elapsed'],
                        'X-Warp10-Error-Message': conn.headerFields['X-Warp10-Error-Message'],
                ],
                response : ''
        ]
        println getMeta(response.meta)
        if (conn.responseCode != 200) {
            System.err.println(response.meta.status + ' : ' + response.meta['X-Warp10-Error-Message'])
        } else {
            response.response = conn.inputStream.withReader { Reader reader -> reader.text }
        }
        writer.close()
        return response
    }

    static def getMeta(Map meta) {
       return "Fetched : ${meta.'X-Warp10-Fetched'} | Ops : ${meta.'X-Warp10-Ops'} | Elapsed : ${meta.'X-Warp10-Elapsed'}"
    }
}
