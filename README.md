##Roche Case
This case is developed with Maven / Java 8

To execute the Test Manager Module, run Application class.

Examples API call with curl

    #Valid request
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Biochemistry","testProperties":[{"property":"density","value":"331"}],"clientId":4343}'

    #Invalid test data
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Microbiology", "clientId":431}'

            
    #Test validation fails
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Microbiology","testProperties":[{"property":"density","value":"321"}],"clientId":431}'
      
    #Operations not found
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Genetic","testProperties":[{"property":"density","value":"321"}],"clientId":431}'   
      
    #Operation test class not found (Fatal error!!)
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Hematology","testProperties":[{"property":"density","value":"321"}],"clientId":431}'        

    #Validation test class not found (Fatal error!!)
    curl -X POST \
      http://localhost:8080/v1/testmanager/execute-operations \
      -H 'Content-Type: application/json' \
      -d '{"testId":"Immunology","testProperties":[{"property":"density","value":"321"}],"clientId":431}'   


