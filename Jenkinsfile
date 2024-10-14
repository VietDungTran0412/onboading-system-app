pipeline {
    agent any
    tools {
        git "Git"
        maven "maven3"
    }
    environment {
        BUILD_VERSION = 'v1.0.0'
        BUILD_SNAPSHOT = 'c6g1-0.0.1-SNAPSHOT.jar '
    }
    stages {
//        stage('Unit Testing') {
//            steps {
//                echo '*** Testing Phase ***'
//                sh "mvn clean test"
//            }
//        }
//        stage('Vulnerability Check') {
//            steps {
//                echo '*** Vulnerability Check Phase ***'
//                sh "mvn org.owasp:dependency-check-maven:check"
//            }
//        }
//        stage('Upload Vulnerability Report') {
//            steps {
//                script {
//                    echo '*** Upload Vulnerability Report ***'
//                    def timestamp = sh(script: 'date +%Y%m%d-%H%M%S', returnStdout: true).trim()
//                    sh 'aws s3 cp target/dependency-check-report.html s3://swin-c6g1-report-bucket/dependency-reports/dependency-check-report-${timestamp}.html'
//                }
//            }
//        }
         stage('Build') {
             steps {
                 echo '*** Build Phase ***'
                 sh "mvn clean install"
                 sh ""
           }
         }
        stage('Upload Build Artefacts') {
            steps {
                echo '*** Upload Build Artefacts ***'
                sh "aws s3 cp target/c6g1-0.0.1-SNAPSHOT.jar  s3://swin-c6g1-report-bucket/build/app-${BUILD_VERSION}.jar"
                sh ""
            }
        }
        stage ('Pull Build File To Test Server') {
            steps{
                sshagent(credentials : ['application-server-ssh-key']) {
                    ansiblePlaybook installation: 'Ansible', inventory: 'playbook/test-hosts.ini', playbook: 'playbook/test-server-deployment.yml', vaultTmpPath: ''
                }
            }
        }

    }
    post {
        always {
            echo '*** Cleaning Jenkins Workspace ***'
            cleanWs() // This will clean the workspace in Jenkins
        }
    }
}
