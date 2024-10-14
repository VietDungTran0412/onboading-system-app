pipeline {
    agent any
    tools {
        git "Git"
        maven "maven3"
    }
    environment {
        BUILD_VERSION = 'v1.0.0'
    }
    stages {
        stage('Unit Testing') {
            steps {
                echo '*** Testing Phase ***'
                sh "mvn clean test"
            }
        }
        stage('Vulnerability Check') {
            steps {
                echo '*** Vulnerability Check Phase ***'
                sh "mvn org.owasp:dependency-check-maven:check"
            }
        }
        stage('Upload Vulnerability Report') {
            steps {
                script {
                    echo '*** Upload Vulnerability Report ***'
                    def timestamp = sh(script: 'date +%Y%m%d-%H%M%S', returnStdout: true).trim()
                    sh 'aws s3 cp target/dependency-check-report.html s3://swin-c6g1-report-bucket/dependency-reports/dependency-check-report-${timestamp}.html --acl public-read'
                }
            }
        }
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
                sh "aws s3 cp target/c6g1-0.0.1-SNAPSHOT.war  s3://swin-c6g1-report-bucket/build/app-${BUILD_VERSION}.war --acl public-read"
                sh ""
            }
        }
    }

}
