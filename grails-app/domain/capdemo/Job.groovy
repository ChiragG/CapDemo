package capdemo

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

    static constraints = {
    }

    def Job(JSONObject obj){
        id= obj.getInt('id')
        name = obj.getString('name')
        status =  obj.getString('status')
        finished = DateTime.parse(obj.getString('finished'))
        started = DateTime.parse(obj.getString('started'))
        modified =DateTime.parse(obj.getString('modified'))
        percent_completed =  obj.getInt('percent_completed')
        instance_set_count =  obj.getInt('instance_set_count')
    }
}
