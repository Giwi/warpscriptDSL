package io.warp10.warpscriptDSL

@Singleton
class WarpScript {
    def ws = new StringBuilder()
    def url
    def readToken
    def writeToken


    def with(String url, String readToken) {
        this.url = url
        this.readToken = readToken
        return this
    }

    def with(String url, String readToken, String writeToken) {
        this.url = url
        this.readToken = readToken
        this.writeToken = writeToken
        return this
    }

    def fetch(String className, Map<String, String> labels, Date start, Date end) {
        return fetch(className, labels, start.toString(), end.toString())
    }

    def fetch(String className, Map<String, String> labels, String start, String end) {
        ws.append("[ '${this.readToken}' '${className}' ${extractLabels(labels)} '${start}' '${end}' ] FETCH ")
        return this
    }

    def fetch(String className, Map<String, String> labels, int end) {
        ws.append("[ '${this.readToken}' '${className}' ${extractLabels(labels)} NOW ${end} ] FETCH ")
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
        println conn.responseCode
        String response = conn.inputStream.withReader { Reader reader -> reader.text }

        writer.close()
        return response
    }
}
