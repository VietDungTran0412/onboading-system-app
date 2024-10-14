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
         stage('Build') {
             steps {
                 sh "mvn clean build"
           }
         }

    }
}
