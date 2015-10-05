package capdemo

import com.captricity.api.CaptricityClient
import grails.converters.JSON
import org.apache.commons.io.FilenameUtils
import org.json.JSONObject
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartRequest

import java.io.*

class BatchController {
    static allowedMethods = [save: "POST", update: "PUT"]

    def index() {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def r = client.showBatches()
        def batches = []
        for (int i = 0; i < r.length(); i++) {
            def jObj = r.getJSONObject(i)
            batches << new Batch(jObj, client)
        }
        return [
                results      : batches,
                batchInstance: new Batch()
        ]
    }

    def show() {
        withBatch {batch ->
            batch.setupDeep()
            return [batchInstance: batch]
        }
    }

    def create() {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def r = client.createBatch(params.name, true, false)

        redirect(action: "index")
    }

    def save() {
        def batch_id = params.int('id')
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def file = request.getFile('save_file')
        def homeDir = new java.io.File(System.getProperty("user.home")) //user home e.g /home/username for unix
        def fileDest = new java.io.File(homeDir, "tempfiles")
        def batchDest = new java.io.File(fileDest, params.id)
        def destFile = new java.io.File(batchDest, file.fileItem.name)
        destFile.createNewFile()
        batchDest.mkdirs()
        file.transferTo(destFile)

        def result = client.addFileToBatch(batch_id, destFile.path)

        redirect(action: "index")

    }

    def addBatchFile() {
        def mRequest = request as MultipartRequest
        if (mRequest) {
            def files = mRequest.fileMap
            def batchID
            int status =0
            String statusText
            withBatch { batch ->
                batchID = batch.id
                files.each { k, v ->
                    if (!v.empty) {
                        def fileEx = FilenameUtils.getExtension(v.originalFilename)
//                        if(v.originalFilename.e)
                        if(grailsApplication.config.capdemo.allowedextensions.contains(fileEx)){
                            def maxFileSize = Integer.parseInt(grailsApplication.config.capdemo.maxfilesize )
                            if(v.bytes.size() <=maxFileSize){
                                try{
                                    def destFile = saveFile(v)
                                    def result = batch.addFile(destFile.path)
                                    status = 200
                                    statusText = "Successfully Uploaded."
                                }catch(Exception e){
                                    status = 500
                                    statusText = "${e.message} "
                                }

                            }else{
                                status= 403
                                statusText = "${v.originalFilename} file size too big."
                            }

                        }else{
                            status = 403
                            statusText= "${v.originalFilename} has invalid extension: ${fileEx}."
                        }
                    }
                }
            }
            render(status: status,text: statusText )
//            redirect action: "show", params: [id: batchID]
        }
    }

    def delete(){
        withBatch {batch ->
           def result = batch.delete()
            def result1 = result
        }
        redirect(action: "index")
    }

    def start(){
        withBatch {batch ->
            def results = batch.start()
        }
        redirect(action: "index")
    }

    def addBatchToDocument() {
        withBatch { batchInstance ->
            batchInstance.assignDefaultDocumet()

            redirect action: "show", params: [id: batchInstance.id]
        }
    }

    def deleteFiles() {
        if (params['delete_files']) {
            withBatch { batch ->
                if (params['delete_files'] instanceof String) {
                    def returnVal = batch.deleteFile(params.int('delete_files'))
                } else {
                    params['delete_files'].each { val ->
                        def returnVal = batch.deleteFile(Integer.parseInt(val))
                    }
                }
                redirect action: "show", params: [id: batch.id]
            }
        }


    }

    private withBatch(Closure c) {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def batchInstance = client.readBatch(params.int('id'))
        if (!batchInstance) {
            return batchNotFound()
        } else {
            c(new Batch(batchInstance, client))
        }
    }

    private def batchNotFound() {
        flash.message = message(code: 'batch.not.found.message', args: [message(code: 'batch.label', default: 'Batch'),
                                                                        params.id])
        redirect(action: "list")
    }

    private def java.io.File saveFile(MultipartFile file) {

        def homeDir = new java.io.File(System.getProperty("user.home")) //user home e.g /home/username for unix
        def fileDest = new java.io.File(homeDir, "tempfiles")
        def batchDest = new java.io.File(fileDest, params.id)
        def destFile = new java.io.File(batchDest, file.originalFilename)
        batchDest.mkdirs()
        file.transferTo(destFile)
        return destFile
    }

    protected def renderResponse(String type, int status, String _message) {
        response.status = status
        def jsonResponse = [success: false, msg: _message]
        render jsonResponse as JSON
    }
}
