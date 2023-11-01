pipeline {
    agent any

      stages {
         stage("Git") {
      
             steps{ 
              sh 'git checkout main'
              sh 'git pull'      
            }
          }

  stage("MVN Clean") {
      
             steps{
             sh 'mvn clean'

            }
        }


  stage("MVN Compile") {
      
             steps{
             sh 'mvn compile'

            }
        }
    }
  stage("JUNIT") {
      
             steps{
             sh 'mvn test'

            }
        }
    }
}
