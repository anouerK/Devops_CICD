pipeline {
    agent any

    environment {
        registry = "anouerkassaa/devops_indiv"
        registryCredential = 'dockerhub_id'
        dockerComposeFile = 'docker-compose.yml'
    }

    stages {

        stage("MVN Clean") {
            steps {
                sh 'mvn clean'
            }
        }

        stage("MVN Compile") {
            steps {
                sh 'mvn compile'
            }
        }

        stage("JUNIT MOCK") {
            steps {
                sh 'mvn test'
            }
        }



        stage("Packaging") {
            steps {
                sh "mvn package"
            }
        }

        stage('Building image') {
            steps {
                script {
                    docker.build("${registry}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Pushing image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        docker.image("${registry}:${BUILD_NUMBER}").push()
                    }
                }
            }
        }

        stage('Deploying with Docker Compose') {
            steps {
                script {
                    sh "docker-compose -f ${dockerComposeFile} up -d"
                }
            }
        }
        stage("NEXUS") {

             steps{
                   sh 'mvn deploy -DskipTests'
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
        stage('Send Email Notification') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                mail to: 'anoueramin.kassaa@esprit.tn, ksaay2000@gmail.com',
                     subject: "Jenkins Pipeline",
                     body: """<p>Build ${currentBuild.fullDisplayName} has completed successfully!</p>
                             <p>Build URL: ${env.BUILD_URL}</p>
                             <p>Changes: ${CHANGES_SINCE_LAST_SUCCESS}</p>""",
                     from: 'projectlaravel77@gmail.com'
            }
        }
    }
}
