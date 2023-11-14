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

      stage("JUNIT MOCK") {

                 steps{
                 sh 'mvn test'

                }
            }
      stage("SonarQube Analysis") {
                steps {
                    script {
                        withSonarQubeEnv('SonarQube') {
                            sh "mvn sonar:sonar -Dsonar.projectKey=supplier_anouer -Dsonar.projectName='supplier_anouer'"
                        }
                    }
            }
        }
      stage("Packaging") {
            steps {
                    sh "mvn package"
            }
        }
      stage("NEXUS") {

                 steps{
                    sh 'mvn deploy -DskipTests'

                }
            }
        }
}
