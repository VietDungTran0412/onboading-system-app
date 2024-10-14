pipeline {
    agent any
    tools {
        git "Git"
        maven "maven3"
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
         stage('Build') {
             steps {
                 sh "mvn clean install"
           }
         }

    }
}
