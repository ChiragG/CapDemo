package capdemo

import com.captricity.api.CaptricityClient
import org.json.JSONObject

import java.io.*

class BatchController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def r = client.showBatches()
        def batches =  []
        for (int i=0; i< r.length() ; i++){
            def jObj = r.getJSONObject(i)
            batches << new Batch(jObj, client)
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
        def batch = new Batch( client.readBatch(batch_id), client)
        batch.price = client.getBatchCost(batch_id)
        batch.readiness = new BatchReadiness(client.getBatchReadiness(batch_id))
        return [batchInstance: batch ]

    }

    def create(){
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def r = client.createBatch(params.name,true,false)

        redirect(action: "index")
    }

    def save() {
        def batch_id =  params.int('id')
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def file = request.getFile('save_file')
        def homeDir = new java.io.File(System.getProperty("user.home")) //user home e.g /home/username for unix
        def fileDest = new java.io.File(homeDir,"tempfiles")
        def batchDest = new java.io.File(fileDest,params.id)
        def destFile = new java.io.File(batchDest,file.fileItem.name)
        destFile.createNewFile()
        batchDest.mkdirs()
        file.transferTo(destFile)

        def result = client.addFileToBatch(batch_id, destFile.path)

        redirect(action: "index")

    }

    def addBatchFile(){
        if(request.getFile('save_file')){
            withBatch{ batch->
                def destFile = saveFile()

                def result = batch.addFile(destFile.path)
                redirect action: "show", params: [id: batch.id]

            }
        }

    }

    def addBatchToDocument(){
        withBatch {batchInstance ->
            batchInstance.assignDefaultDocumet()

            redirect action: "show", params: [id: batchInstance.id]
        }
    }

    def deleteFiles() {
        if(params['delete_files']){
            withBatch {batch ->
                if(params['delete_files'] instanceof String){
                    def returnVal = batch.deleteFile( params.int('delete_files') )
                }else{
                    params['delete_files'].each {val->
                        def returnVal = batch.deleteFile( Integer.parseInt(val))
                    }
                }
                redirect action: "show", params: [id: batch.id]
            }
        }


    }

    private withBatch(Closure c) {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def batchInstance =  client.readBatch(params.int('id'))
        if (!batchInstance ) {
            return batchNotFound()
        } else {
            c(new Batch(batchInstance, client))
        }
    }

    private def batchNotFound(){
        flash.message = message(code: 'batch.not.found.message', args: [message(code: 'batch.label', default: 'Batch'),
                                                                      params.id])
        redirect(action: "list")
    }

    private def java.io.File saveFile(){

        def file = request.getFile('save_file')
        def homeDir = new java.io.File(System.getProperty("user.home")) //user home e.g /home/username for unix
        def fileDest = new java.io.File(homeDir,"tempfiles")
        def batchDest = new java.io.File(fileDest,params.id)
        def destFile = new java.io.File(batchDest,file.fileItem.name)
        batchDest.mkdirs()
        file.transferTo(destFile)
        return destFile
    }
}
