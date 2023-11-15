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
                        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent -Djacoco.version=0.8.8 test jacoco:report'
                        sh "mvn sonar:sonar -Dsonar.projectKey=supplier_anouer -Dsonar.projectName='supplier_anouer' -Dsonar.jacoco.reportPaths=target/site/jacoco/jacoco.xml"

                    }
                }
            }
        }
        stage('Notification') {
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                    emailext(
                        subject: "Successful Build #${currentBuild.number} ",
                        body: """
                            The build was successful!
                            Build Details: ${BUILD_URL}
                            Build Number: ${currentBuild.number}
                            Build Status: ${currentBuild.currentResult}
                        """,
                        to: 'ksaay2000@gmail.com'
                    )
                }
            }
        }
    }
}
