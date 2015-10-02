package capdemo

import com.captricity.api.CaptricityClient

import java.io.*

class BatchController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def r = client.showBatches()
        def batches =  []
        for (int i=0; i< r.length() ; i++){
            def jObj = r.getJSONObject(i)
            batches << new Batch(jObj)
        }
        return  [
                results : batches,
                batchInstance : new Batch()
        ]
    }

    def start() {
        def batch = new Batch(params)
        redirect(action: "index")
    }

    def show(){

        def batch_id = params.int('id')
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def batch = new Batch( client.readBatch(batch_id))
        batch.price = client.getBatchCost(batch_id)
        batch.readiness = new BatchReadiness(client.getBatchReadiness(batch_id))
        return [batchInstance: batch ]

    }


    def save() {
        def batch_id =  params.int('id')
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def file = request.getFile('save_file')
        def homeDir = new java.io.File(System.getProperty("user.home")) //user home e.g /home/username for unix
        def fileDest = new java.io.File(homeDir,"tempfiles")
        def batchDest = new java.io.File(fileDest,params.id)
        def destFile = new java.io.File(batchDest,file.fileItem.name)
        batchDest.mkdirs()
        file.transferTo(destFile)

        def result = client.addFileToBatch(batch_id, destFile.path)
//        batch = new Batch( client.readBatch(batch_id))

        redirect(action: "index")

    }
}
