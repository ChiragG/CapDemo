package capdemo

import com.captricity.api.CaptricityClient
import org.joda.time.DateTime
import org.json.JSONArray
import org.json.JSONObject
import org.grails.plugins.csv.CSVMapReader

class Job {
    int id
    DateTime finished
    DateTime started
    DateTime modified
    String name
    String status
    int percent_completed
    int instance_set_count
    List<LinkedHashMap<String,Object>> jobResults
    String resultsText
    List pieData
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

    def populateResults(){
        resultsText = client.getJobResults(id)

        def columnHeaders = [:]
        jobResults = []
        int i =0;
        resultsText.eachCsvLine{tokens->
            if(i==0){
                tokens.eachWithIndex{val ,k->
                    columnHeaders[k] = val
                }
            }else{
                def valueMap = [:]
                tokens.eachWithIndex { columnValue, int columnPos ->
                    String columnName = columnHeaders[columnPos]
                    if (columnName && columnValue) {
                        valueMap[columnName] = columnValue
                    }
                }
                jobResults << valueMap
            }
            i++
        }
    }

    def getPieData(){
        def mapOfVals = [:]
        jobResults.each {map->
            map.each {key,value->
                if(key.contains("Hours")){
                    if(mapOfVals.containsKey(key)){
                        mapOfVals[key] = mapOfVals[key]+ Integer.parseInt(value)
                    }else{
                        mapOfVals[key] =  Integer.parseInt(value)
                    }

                }
            }
        }

        def data =[]
        mapOfVals.each {data << [it.key.replace("_", " "),it.value]}
        pieData = data
    }

    def CSVResults(){
        resultsText = client.getJobResults(id)
    }


}


