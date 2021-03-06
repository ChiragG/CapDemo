package capdemo

import com.captricity.api.CaptricityClient

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class JobController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)
        def jobList = []
        def r = client.showJobs()
        for(int i=0; i < r.length(); i++){
            def obj = r.getJSONObject(i)
            if(!obj.getBoolean('is_example')){
                jobList << new Job(obj,client)
            }
        }
        return[jobList: jobList]
    }

    def show() {
        withJob {jobInstance ->
            jobInstance.populateResults()
            respond jobInstance
        }
    }

    def results(){
        withJob {jobInstance ->
            jobInstance.CSVResults()
            response.setHeader "Content-Disposition", "inline; filename=results.csv"
            render contentType: "application/octet-stream", text: jobInstance.resultsText
        }
    }

    private withJob(Closure c) {
        CaptricityClient client = new CaptricityClient(grailsApplication.config.capdemo.api.token)

        def jobInstance =  client.getJob(params.int('id'))
        if (!jobInstance ) {
            return jobNotFound()
        } else {
            c(new Job(jobInstance, client))
        }
    }

    private def jobNotFound(){
        flash.message = message(code: 'job.not.found.message', args: [message(code: 'job.label', default: 'Job'),
                                                                    params.id])
        redirect(action: "list")
    }


}
