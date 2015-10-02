package capdemo

import org.joda.time.*
import org.json.JSONObject

class Batch {

    def grailsApplication
    String name
    int id
    String status
    DateTime last_upload_date
    int file_count
    JSONObject price
    BatchReadiness readiness


    static constraints = {

    }

    def Batch(JSONObject obj){
        name = obj.getString('name')
        id =  obj.getInt('id')
        status = obj.getString('status')
        last_upload_date = DateTime.parse(obj.getString('last_upload_date'))
        file_count = obj.getInt('file_count')
//        cost = new Cost()
//        files= []
    }

}
