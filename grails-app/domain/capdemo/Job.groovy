package capdemo

import com.captricity.api.CaptricityClient
import org.joda.time.DateTime
import org.json.JSONObject

class Job {
    int id
    DateTime finished
    DateTime started
    DateTime modified

    String name
    String status
    int percent_completed
    int instance_set_count

    static private  CaptricityClient client

    static constraints = {
    }

    def Job(JSONObject obj, CaptricityClient capClient){
        id= obj.getInt('id')
        name = obj.getString('name')
        status =  obj.getString('status')
        if(obj.isNull('finished')){
            finished = null
        }else{
            finished = DateTime.parse(obj.getString('finished'))
        }
        if(obj.isNull('started')){
            started = null
        } else{
            started = DateTime.parse(obj.getString('started'))
        }
        if(obj.isNull('modified')){
            modified =  null
        }else{
            modified =DateTime.parse(obj.getString('modified'))
        }
        percent_completed =  obj.getInt('percent_completed')
        instance_set_count =  obj.getInt('instance_set_count')
        client = capClient
    }

    def getResults(){
        def results = client.getJobResults(id)
    }

}
