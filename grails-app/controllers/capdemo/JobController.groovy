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
            jobList << new Job(obj)
        }
        return[jobList: jobList]
    }

    def show(Job jobInstance) {
        respond jobInstance
    }


}
