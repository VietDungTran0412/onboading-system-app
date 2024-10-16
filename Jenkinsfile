pipeline {
    agent any
    tools {
        git "Git"
        maven "maven3"
    }
    environment {
        BUILD_VERSION = 'v1.0.0'
        BUILD_SNAPSHOT = 'c6g1-0.0.1-SNAPSHOT.jar '
        SCANNER_HOME = tool 'sonar-scanner'
        SONARQUBE_SERVER = 'http://3.107.104.232:9000/' 
        SONAR_TOKEN = credentials('jenkinssonar')
        ANSIBLE_PRIVATE_KEY = credentials('application-server-ssh-key')
    }
    stages {
        stage('Unit Testing') {
            steps {
                echo '*** Testing Phase ***'
                sh "mvn clean test"
            }
        }
        stage('Sonarqube Analysis') {
            steps {
                script {
                    def sonarScanner = "${SCANNER_HOME}/bin/sonar-scanner"
                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        sh """
                            mvn clean verify sonar:sonar \
                              -Dsonar.host.url=${SONARQUBE_SERVER} \
                              -Dsonar.login=${SONAR_TOKEN} \
                              -Dsonar.projectName=onboarding-system-app \
                              -Dsonar.java.binaries=target/classes \
                              -Dsonar.projectKey=onboarding-system-app \
                              -Dsonar.qualitygate.wait=true \
                              -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                              -X
                        """
                    }
                }
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
                    sh "aws s3 cp target/dependency-check-report.html s3://swin-c6g1-report-bucket/dependency-reports/dependency-check-report-${timestamp}.html"
                }
            }
        }
         stage('Build with Maven') {
             steps {
                 echo '*** Build Phase ***'
                 sh "mvn clean install"
                 sh ""
           }
         }
        stage('Upload Build Artefacts to S3') {
            steps {
                echo '*** Upload Build Artefacts ***'
                sh "ansible-galaxy collection install -r playbook/requirements.yml"
                sh "aws s3 cp target/c6g1-0.0.1-SNAPSHOT.jar  s3://swin-c6g1-report-bucket/build/app-${BUILD_VERSION}.jar"
            }
        }
        stage ('Pull Build File and Deploy To Test Server') {
            steps{
                sshagent(credentials : ['application-server-ssh-key']) {
                    echo "*** Pull And Deploy to Test Server ***"
                    sh "/usr/bin/ansible-playbook playbook/test-server-deployment.yml -i playbook/test-hosts.ini"
                }
            }
        }
        stage('Integration Testing using Postman') {
            steps {
                echo '*** Integration Testing using Postman ***'
                script {
                    try {
                        // Run Postman collection and display the results in the console
                        sh """
                        newman run https://swin-c6g1-report-bucket.s3.ap-southeast-2.amazonaws.com/postman-test/test-collection.postman_collection.json \
                        --reporters cli
                        """
                    } catch (Exception e) {
                        echo "Newman run failed: ${e.getMessage()}"
                        error("Newman test execution failed")
                    }
                }
            }
        }
        stage ('Pull Build File and Deploy To Prod Servers') {
            steps{
                sshagent(credentials : ['application-server-ssh-key']) {
                    echo "*** Pull And Deploy to Production Servers ***"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@10.0.4.201"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@10.0.3.83"
                    sh "ansible-galaxy collection install -r playbook/requirements.yml"
                    sh "/usr/bin/ansible-playbook playbook/prod-server-deployment.yml -i playbook/prod-hosts.ini -u ec2-user"
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
