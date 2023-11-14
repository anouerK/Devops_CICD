pipeline {
    agent any

    environment {
        registry = "anouerkassaa/devops_indiv"
        registryCredential = 'dockerhub_id'
    }

    stages {
        stage("Git") {
            steps {
                sh 'git checkout main'
                sh 'git pull'
            }
        }

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

        stage('Running Docker container') {
            steps {
                script {
                    sh "docker run -p 8085:8085 --name ${registry}:${BUILD_NUMBER} --link mysql:mysql ${registry}:${BUILD_NUMBER}"
                }
            }
        }
    }
}
