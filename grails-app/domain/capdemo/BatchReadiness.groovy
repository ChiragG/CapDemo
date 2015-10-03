package capdemo

import org.json.JSONObject

class BatchReadiness {

    Boolean has_empty_documents
    Boolean valid_page_count
    List<String> warnings
    List<String> errors
    Boolean has_documents

    static constraints = {
    }

    def BatchReadiness(JSONObject obj) {
        has_empty_documents = obj.getBoolean('has_empty_documents')
        valid_page_count =  obj.getBoolean('valid_page_count')
        has_documents = obj.getBoolean('has_documents')
        errors = []
        warnings = []
        def jWarn = obj.getJSONArray('warnings')
        for (int i=0 ; i< jWarn.length(); i++){
            warnings << jWarn.getString(i)
        }
        def jError = obj.getJSONArray('errors')
        for (int i=0 ; i< jError.length(); i++) {
            errors << jError.getString(i)
        }
    }

    def Boolean hasErrors() {
        if(errors != null){
            return  errors.size() > 0
        }

        return true
    }
}
