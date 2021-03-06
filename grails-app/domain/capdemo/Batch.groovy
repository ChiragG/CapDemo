package capdemo

import com.captricity.api.CaptricityClient
import org.joda.time.*
import org.json.JSONObject

class Batch {

    def grailsApplication
    String name
    int id
    int jobId
    String status
    DateTime last_upload_date
    int file_count
    JSONObject price
    BatchReadiness readiness
    CaptricityClient client
    List<File> files

    static constraints = {

    }

    def Batch(JSONObject obj, capClient){
        if(obj != null && capClient != null ){
            name = obj.getString('name')
            id =  obj.getInt('id')
            status = obj.getString('status')
            if(obj.isNull('related_job_id')){
                jobId = 0
            }else{
                jobId = obj.getInt('related_job_id')
            }
            if(obj.isNull('last_upload_date')){
                last_upload_date = null
            }else{
                last_upload_date = DateTime.parse(obj.getString('last_upload_date'))
            }
            file_count = obj.getInt('file_count')
            client= capClient
            files = []
            def filesJson = client.getBatchFiles(id)
            for(int i=0; i< filesJson.length() ; i++){
                def fileToBeAdded = new File()
                fileToBeAdded.name = filesJson.getJSONObject(i).getString('file_name')
                fileToBeAdded.id = filesJson.getJSONObject(i).getInt('id')
                files << fileToBeAdded
            }
        }

    }

    def assignDefaultDocumet(){
        def docs = client.showDocuments()
        def defaultDocId = docs.getJSONObject(0).id
        def results = client.assignDocumentToBatch(id,defaultDocId)
    }

    def JSONObject deleteFile(int fileID){
        return client.deleteBatchFile(fileID)
    }

    def JSONObject addFile(String path){
        return client.addFileToBatch(id,path)
    }

    def disableStart(){
        if(readiness){
            return (readiness.hasErrors() || status != "setup")
        }
        return true
    }

    def setupDeep(){
        price = client.getBatchCost(id)
        readiness = new BatchReadiness(client.getBatchReadiness(id))
    }

    def delete(){
        def results = client.deleteBatch(id)
    }
    def JSONObject start() {
        def results = client.submitBatch(id)
    }

}
